package com.app.tienda.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "customers")
public class CustomerEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;

  @OneToOne //@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true) esto es para el eliminado de proveedor
  @JoinColumn(name = "address_id", referencedColumnName = "id")
  private AddressEntity address;

  private String phone;
  private String email;
  private String gender;
}
