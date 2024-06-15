package com.app.tienda.service.impl;

import com.app.tienda.constant.Message;
import com.app.tienda.entity.ProductEntity;
import com.app.tienda.entity.SupplierEntity;
import com.app.tienda.exception.InternalServerException;
import com.app.tienda.exception.ResourceNotFoundException;
import com.app.tienda.model.request.ProductRequest;
import com.app.tienda.model.response.ProductResponse;
import com.app.tienda.repository.ProductRepository;
import com.app.tienda.repository.SupplierRepository;
import com.app.tienda.service.IProductService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProductServiceImpl implements IProductService {
  @Autowired
  private ProductRepository productRepository;
  @Autowired
  private ModelMapper modelMapper;
  @Autowired
  private SupplierRepository supplierRepository;
  @Override
  public List<ProductResponse> findAll() {
    log.info("ProductServiceImpl.findAll");

    List<ProductEntity> products = productRepository.findAll();
    log.info("ProductResponse.findAll");

    return products.stream()
      .map(productEntity -> modelMapper.map(productEntity, ProductResponse.class))
      .collect(Collectors.toList());
  }

  @Override
  public ProductResponse save(ProductRequest productRequest) {
    log.info("ProductServiceImpl - save: {}", productRequest);

    SupplierEntity supplier = supplierRepository.findById(productRequest.getSupplierId())
      .orElseThrow(() -> new ResourceNotFoundException("Proveedor no encontrado"));

    try {
      ProductEntity productEntity = modelMapper.map(productRequest, ProductEntity.class);
      productEntity.setId(null);
      productEntity.setSupplier(supplier);
      ProductEntity saved = productRepository.save(productEntity);

      return modelMapper.map(saved, ProductResponse.class);
    } catch (Exception e) {
      log.error("Hubo un error al crear el proveedor : {}", e.getMessage());
      throw new InternalServerException(Message.SAVE_ERROR + "el proveedor");
    }
  }
  @Override
  public ProductResponse getById(Long id) {
    log.info("ProductServiceImpl - getById: {}", id);

    Optional<ProductEntity> productOptional = productRepository.findById(id);
    log.info("productOptional {}", productOptional);

    return productOptional
      .map(productEntity -> modelMapper.map(productEntity, ProductResponse.class))
      .orElseThrow(() -> new ResourceNotFoundException(Message.ID_NOT_FOUND + ": " + id));
  }

  @Override
  public ProductResponse update(Long id, ProductRequest productRequest) {
    log.info("Product service imp - update: {} {}", id, productRequest);

    try {
      Optional<ProductEntity> productOptional = productRepository.findById(id);

      if (productOptional.isPresent()) {
        //ProductEntity productEntity = productOptional.get();
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(id);
        productEntity.setName(productRequest.getName());
        productEntity.setDescription(productRequest.getDescription());
        productEntity.setPrice(productRequest.getPrice());
        productEntity.setQuantityInInventory(productRequest.getQuantityInInventory());
        productEntity.setCategory(productRequest.getCategory());
        productEntity.setSupplier(productOptional.get().getSupplier());

        ProductEntity productUpdate = productRepository.save(productEntity);
        return modelMapper.map(productUpdate, ProductResponse.class);

      } else {
        throw new ResourceNotFoundException(Message.ID_NOT_FOUND);
      }
    } catch (DataAccessException e) {
      log.info("Hubo un error al actualizar el producto: {}", e.getMessage());
      throw new InternalServerException(Message.UPDATE_ERROR + "el producto con ID " + id, e);
    }
  }

  @Override
  public void delete(Long id) {
    log.info("Deleting producto by id: {}", id);

    try {
      Optional<ProductEntity> productOptional = productRepository.findById(id);
      log.info("Product optional {}", productOptional);

      if (productOptional.isPresent()) {
        log.info("Product {}", productOptional);
        productRepository.deleteById(id);

      } else {
        log.info("The product was not found");
        throw new ResourceNotFoundException(Message.ID_NOT_FOUND +  ": " + id);
      }
    } catch (DataAccessException e) {
      log.info("Se produjo un error al eliminar el producto {}", e.getMessage());
      throw new InternalServerException(Message.DELETE_ERROR + " el producto", e);
    }
  }
}