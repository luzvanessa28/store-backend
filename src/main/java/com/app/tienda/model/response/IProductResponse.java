package com.app.tienda.model.response;

import java.math.BigDecimal;

public interface IProductResponse {
  Long getId();
  String getName();
  String getDescription();
  BigDecimal getPrice();
  Integer inventory();
  String category();
  Long supplierId();

}
