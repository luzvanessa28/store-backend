package com.app.tienda.model.response;

import com.app.tienda.entity.AddressEntity;
import lombok.Data;

@Data
public class SupplierResponse {
  private Long id;
  private String name;
  private AddressEntity address;
  private String phone;
  private String email;
}