package com.app.tienda.entity;

import javax.persistence.*;

@Entity
@Table(name = "address")
public class AddressEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String street;
  private String mz;
  private String lt;
  private String delegation;
  private String city;
}
