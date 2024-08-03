package com.app.tienda.model.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.*;
import java.util.List;

@Getter
@Setter
@ToString
public class SupplierOrderRequest {
  private List<ProductOrder> products;

  @NotNull(message = "Requerido")
  private Long supplierId;
}
/*Como lo hago para guardar:
 * Voy a verificar que el provider exista o este registrado
 * Si el provider no existe, corto el flujo del programa, y le notifico al usuario que ese proveedor no existe y que registre primero el proveedor
 * Si el provider existe, guardo el registro*/