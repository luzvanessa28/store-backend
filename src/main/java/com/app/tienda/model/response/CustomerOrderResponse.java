package com.app.tienda.model.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class CustomerOrderResponse {
  private Long id;
  private LocalDateTime date;
  private String status;
  //private List<ProductResponse> order;
  /*@OneToMany
  @JoinColumn(name = "supplier_id", referencedColumnName = "id")
  private SupplierEntity supplier;*/
  private Double total;

}
