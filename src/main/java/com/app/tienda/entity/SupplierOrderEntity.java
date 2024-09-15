package com.app.tienda.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
@Table(name = "supplier_orders")
public class SupplierOrderEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ElementCollection
  @CollectionTable(name = "supplier_order_product", joinColumns = @JoinColumn(name = "supplier_order_id"))
  @MapKeyColumn(name = "producto_id")
  @Column(name = "cantidad")
  private List<SupplierOrderProduct> products;

  private LocalDateTime date;
  private String status;
  private BigDecimal totalAmount;

  @ManyToOne
  @JoinColumn(name = "supplier_id", nullable = false)
  private SupplierEntity supplier;
}
