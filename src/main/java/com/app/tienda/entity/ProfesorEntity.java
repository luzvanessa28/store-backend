package com.app.tienda.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "profesores")
public class ProfesorEntity implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private String lastName;
  private String secondLastName;
  private Integer age;
  private String email;
  private String phone;
}
