package com.app.tienda.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class PersonRequest {
  @NotBlank(message = "El nombre no puede estar en blanco")
  @Size(min = 3, max=20, message = "El nombre debe de contener de 3 a 20 caracteres")
  private String name;

  @NotBlank(message = "El nombre no puede estar en blanco")
  private String firstName;

  private String secondName;

  @NotBlank(message = "El nombre no puede estar en blanco")
  private String age;

  public String getName() {
    return name;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getSecondName() {
    return secondName;
  }

  public String getAge() {
    return age;
  }

  @Override
  public String toString() {
    return "PersonRequest{" +
      "name='" + name + '\'' +
      ", firstName='" + firstName + '\'' +
      ", secondName='" + secondName + '\'' +
      ", age='" + age + '\'' +
      '}';
  }
}

