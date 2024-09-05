package com.app.tienda.service;

import com.app.tienda.entity.ProductEntity;
import com.app.tienda.entity.SupplierOrderProduct;
import com.app.tienda.model.response.ProductResponse;

import java.util.List;

public interface IInventoryService{
  public ProductResponse update(List<SupplierOrderProduct> products);
  public ProductResponse findProductById(Long id);
  public List<ProductResponse> findAll();
  public List<ProductResponse> findProductsByCategory(String name);
  public void addProduct(ProductEntity productEntity);
}
