package com.app.tienda.model.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Getter
@Setter
@ToString
public class CustomerOrderRequest {
  @NotNull(message = "Requerido")
  private Long customerId;
  @NotEmpty(message = "Products list cannot be empty")
  @Valid
  private List<ProductOrder> products;
  @NotBlank(message = "Method of payment cannot be blank")
  @Pattern(regexp = "Credit Card|PayPal|Bank Transfer", message = "Method of payment must be one of: Credit Card, PayPal, Bank Transfer")
  private String methodOfPayment;
}
