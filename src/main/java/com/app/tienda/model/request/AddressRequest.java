package com.app.tienda.model.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AddressRequest {
  @NotBlank(message = "Es requerido")
  private String street;

  @NotBlank(message = "Es requerido")
  private String mz;

  @NotBlank(message = "Es requerido")
  private String lt;

  @NotBlank(message = "Es requerido")
  private String delegation;

  @NotBlank(message = "Es requerido")
  private String city;
}
