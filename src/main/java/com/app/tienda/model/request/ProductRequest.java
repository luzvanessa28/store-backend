package com.app.tienda.model.request;

import com.app.tienda.entity.SupplierEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
  @Size(max = 60, message = "Solo se aceptan numeros")
  private Integer quantityInInventory;

  @NotBlank(message = "Requerido")
  @Size(max = 50, message = "maximo 50 caracteres")
  private String category;

  private SupplierEntity supplier;
}