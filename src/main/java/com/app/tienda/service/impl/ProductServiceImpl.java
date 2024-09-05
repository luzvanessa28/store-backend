package com.app.tienda.service.impl;

import com.app.tienda.constant.Message;
import com.app.tienda.entity.ProductEntity;
import com.app.tienda.entity.SupplierEntity;
import com.app.tienda.exception.InternalServerException;
import com.app.tienda.exception.ResourceNotFoundException;
import com.app.tienda.model.request.ProductRequest;
import com.app.tienda.model.response.IProductResponse;
import com.app.tienda.model.response.ProductResponse;
import com.app.tienda.repository.ProductRepository;
import com.app.tienda.repository.SupplierRepository;
import com.app.tienda.service.IInventoryService;
import com.app.tienda.service.IProductService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
  @Autowired
  private IInventoryService inventoryService;

  @Override
  public List<ProductResponse> findAll() {
    log.info("ProductServiceImpl.findAll");

    List<ProductEntity> products = productRepository.findAll();
    log.info("ProductResponse.findAll");

    return products.stream()
      .map(productEntity -> modelMapper.map(productEntity, ProductResponse.class))
      .collect(Collectors.toList());
  }

  @Transactional
  @Override
  public ProductResponse save(ProductRequest productRequest) {
    log.info("ProductServiceImpl - save: {}", productRequest);

    SupplierEntity supplier = supplierRepository.findById(productRequest.getSupplierId())
      .orElseThrow(() -> new ResourceNotFoundException("Supplier not found"));

    try {
      ProductEntity productEntity = modelMapper.map(productRequest, ProductEntity.class);
      productEntity.setId(null);
      productEntity.setSupplier(supplier);

      ProductEntity productSaved = productRepository.save(productEntity);

      //Registrar un producto en el inventario
      this.inventoryService.addProduct(productEntity);

      return modelMapper.map(productSaved, ProductResponse.class);
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
  public List<ProductResponse> getByName(String name) {
    log.info("ProductServiceImpl - find product by name {}", name);

    List<ProductEntity> productName = productRepository.findByName(name);

    return productName.stream().
      map(productEntity -> modelMapper.map(productEntity, ProductResponse.class))
      .collect(Collectors.toList());
  }

  @Override
  public List<ProductResponse> getByCategory(String category) {
    log.info("ProductServiceImpl - find product by category {}", category);

    List<ProductEntity> productList = this.productRepository.findByCategory(category);
    log.info("productList {}", productList);

    return productList.stream()
      .map(productEntity -> modelMapper.map(productEntity, ProductResponse.class))
      .collect(Collectors.toList());
  }

  @Override
  public List<IProductResponse> getSuppilerById(Long supplierId) {
    log.info("ProductServiceImpl - find products by supplierId {}", supplierId);

    return productRepository.findProductsBySuppiler(supplierId);
  }

  @Override
  public ProductResponse update(Long id, ProductRequest productRequest) {
    log.info("Product service imp - update: {} {}", id, productRequest);

    SupplierEntity supplierEntity = this.supplierRepository.findById(productRequest.getSupplierId())
      .orElseThrow( () -> new ResourceNotFoundException("Supplier not found"));

    try {
      Optional<ProductEntity> productOptional = productRepository.findById(id);

      if (productOptional.isPresent()) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(id);
        productEntity.setName(productRequest.getName());
        productEntity.setDescription(productRequest.getDescription());
        productEntity.setPrice(productRequest.getPrice());
        productEntity.setCategory(productRequest.getCategory());
        productEntity.setSupplier(supplierEntity);

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