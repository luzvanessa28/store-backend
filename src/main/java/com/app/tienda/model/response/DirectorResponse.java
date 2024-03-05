package com.app.tienda.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DirectorResponse {
  private Long id;
  private String name;
  private String lastName;
  private String secondLastName;
  private String phone;
}
