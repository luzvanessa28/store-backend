package com.app.tienda.controller;

import com.app.tienda.model.response.ProductResponse;
import com.app.tienda.service.IProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/product")
public class ProductController {
  @Autowired
  private IProductService productService;
  @GetMapping
  public List<ProductResponse> getAll() {
    log.info("Getting all Product");
    return productService.findAll();
  }
}
