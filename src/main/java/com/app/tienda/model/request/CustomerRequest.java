package com.app.tienda.model.request;

import com.app.tienda.entity.AddressEntity;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class CustomerRequest {
  @NotBlank(message = "Es requerido")
  @Size(min = 3, max=20, message = "El nombre debe de contener de 3 a 20 caracteres")
  private String name;

  @NotBlank(message = "Es requerido")
  private AddressEntity address;

  @NotBlank(message = "Es requerido")
  @Size(min = 10)
  private String phone;

  @NotBlank(message = "Es requerido")
  @Email
  private String email;
}
