package com.app.tienda.service.impl;

import com.app.tienda.entity.InventoryEntity;
import com.app.tienda.entity.ProductEntity;
import com.app.tienda.entity.SupplierOrderProduct;
import com.app.tienda.exception.ResourceNotFoundException;
import com.app.tienda.model.response.ProductResponse;
import com.app.tienda.repository.InventoryRepository;
import com.app.tienda.service.IInventoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class InventoryServiceImpl implements IInventoryService {

  @Autowired
  private InventoryRepository inventoryRepository;

  @Override
  public ProductResponse update(List<SupplierOrderProduct> products) {
    return null;
  }

  @Override
  public ProductResponse findProductById(Long id) {
    return null;
  }

  @Override
  public List<ProductResponse> findAll() {
    return null;
  }

  @Override
  public List<ProductResponse> findProductsByCategory(String category) {
    return null;
  }

  @Override
  public void addProduct(ProductEntity productEntity) {
    log.info("InventoryServiceImpl - add product {}", productEntity);

    InventoryEntity inventoryEntity = new InventoryEntity();
    inventoryEntity.setProduct(productEntity);
    inventoryEntity.setStock(0);
    inventoryEntity.setDate(LocalDate.now());

    this.inventoryRepository.save(inventoryEntity);
  }
}