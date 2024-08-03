package com.app.tienda.model.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class SupplierOrderProductResponse {
  private String name;
  private BigDecimal unitPrice;
  private Integer quantity;
}
