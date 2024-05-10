package com.app.tienda.model.response;

import lombok.Data;

@Data
public class AddressResponse {
  private String street;
  private String mz;
  private String lt;
  private String delegation;
  private String city;
}
