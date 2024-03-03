package com.app.tienda.model.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.*;

@Getter
@Setter
@ToString
public class AlumnoRequest {

  @NotBlank(message = "Es requerido")
  @Size(min = 3, max=20, message = "El nombre debe de contener de 3 a 20 caracteres")
  private String name;

  @NotBlank(message = "Es requerido")
  @Size(min = 3, max = 50, message = "Solo se aceptan letras")
  private String lastName;

  @NotBlank(message = "Es requerido")
  @Size(min = 3, max = 50, message = "Solo se aceptan letras")
  private String secondLastName;

  @NotNull
  @Min(2)
  private Integer age;

  @Email
  private String email;

  @NotBlank(message = "Es requerido")
  @Size(min = 10)
  private String phone;
}
