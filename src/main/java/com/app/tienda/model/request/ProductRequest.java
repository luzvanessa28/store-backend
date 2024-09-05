package com.app.tienda.model.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.*;
import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class ProductRequest {

  @NotBlank(message = "Requerido")
  @Size(max = 50, message = "maximo 50 caracteres")
  private String name;

  @NotBlank(message = "Requerido")
  @Size(max = 60, message = "maximo 60 caracteres")
  private String description;

  @NotNull(message = "Requerido")
  @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor que 0")
  private BigDecimal price;

  @NotBlank(message = "Requerido")
  @Size(max = 50, message = "maximo 50 caracteres")
  private String category;

  @NotNull(message = "Requerido")
  private Long supplierId;
}