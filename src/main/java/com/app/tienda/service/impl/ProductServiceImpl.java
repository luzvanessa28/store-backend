package com.app.tienda.service.impl;

import com.app.tienda.entity.ProductEntity;
import com.app.tienda.model.response.ProductResponse;
import com.app.tienda.repository.ProductRepository;
import com.app.tienda.service.IProductService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProductServiceImpl implements IProductService {
  @Autowired
  private ProductRepository productRepository;
  @Autowired
  private ModelMapper modelMapper;
  @Override
  public List<ProductResponse> findAll() {
    log.info("ProductServiceImpl.findAll");

    List<ProductEntity> products = productRepository.findAll();
    log.info("ProductResponse.findAll");

    return products.stream()
      .map(productEntity -> modelMapper.map(productEntity, ProductResponse.class))
      .collect(Collectors.toList());
  }
}
