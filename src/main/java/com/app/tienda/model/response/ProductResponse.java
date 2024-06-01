package com.app.tienda.model.response;

import com.app.tienda.entity.SupplierEntity;
import lombok.ToString;

import java.math.BigDecimal;
@ToString
public class ProductResponse {
  private Long id;
  private String name;
  private String description;
  private BigDecimal price;
  private Integer quantityInInventory;
  private String category;
  private SupplierEntity supplier;
}
