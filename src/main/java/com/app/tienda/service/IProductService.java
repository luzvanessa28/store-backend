package com.app.tienda.service;

import com.app.tienda.model.response.ProductResponse;

import java.util.List;

public interface IProductService {
  public List<ProductResponse> findAll();
}
