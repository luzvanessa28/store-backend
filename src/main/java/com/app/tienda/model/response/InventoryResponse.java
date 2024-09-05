package com.app.tienda.model.response;

import com.app.tienda.entity.ProductEntity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class InventoryResponse {
  private Long id;
  private ProductResponse product;
  private Integer stock;
  private LocalDate date;
}
