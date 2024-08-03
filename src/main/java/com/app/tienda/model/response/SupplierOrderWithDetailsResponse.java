package com.app.tienda.model.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
public class SupplierOrderWithDetailsResponse {
  private Long id;

  private LocalDateTime date;

  private String status;

  private BigDecimal totalAmount;

  private List<SupplierOrderProductResponse> products;
}
