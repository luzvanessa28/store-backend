package com.app.tienda.model.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class SupplierRequest {
  @NotBlank(message = "Es requerido")
  @Size(min = 3, max=20, message = "Debe de contener de 3 a 20 caracteres")
  private String name;

  @NotBlank(message = "Es requerido")
  @Size(min = 3, max=20, message = "Debe de contener de 3 a 20 caracteres")
  private String lastName;

  @NotBlank(message = "Es requerido")
  @Size(min = 3, max=20, message = "Debe de contener de 3 a 20 caracteres")
  private String secondLastName;

  private AddressRequest address;

  @NotBlank(message = "Es requerido")
  @Size(min = 10)
  private String phone;

  @NotBlank(message = "Es requerido")
  @Email
  private String email;

  @NotBlank(message = "F (femenino) o M (masculino)")
  @Size(max = 1)
  private String gender;
}
