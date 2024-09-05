package com.app.tienda.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@ToString
@Entity
@Table(name = "customer_orders")
public class CustomerOrderEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private LocalDateTime date;

  @ManyToOne
  @JoinColumn(name = "customer_id", nullable = false)
  private CustomerEntity customer;

  @ElementCollection
  //@CollectionTable(name = "customer_order_product", joinColumns = @JoinColumn(name = "customer_order_id"))
  @MapKeyColumn(name = "producto_id")
  @Column(name = "cantidad")
  private Map<Long, Integer> requestedProducts;

  private Double total;
  private String status;
  private String methodOfPayment;
}
