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
        ProductEntity productEntity = productOptional.get();
        modelMapper.map(productRequest, productEntity);

        ProductEntity productUpdate = productRepository.save(productEntity);
        return modelMapper.map(productRepository.save(productEntity), ProductResponse.class);

      } else {
        throw new ResourceNotFoundException(Message.ID_NOT_FOUND);
      }
    } catch (DataAccessException e) {
      log.info("Hubo un error al actualizar el producto: {}", e.getMessage());
      throw new InternalServerException(Message.UPDATE_ERROR + "el producto con ID " + id, e);
    }
  }

}
