package com.app.tienda.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class PerroRequest {
  @NotBlank(message = "El nombre no puede estar en blanco")
  @Size(min = 3, max=20, message = "El nombre debe de contener de 3 a 20 caracteres")
  private String name;
  @NotBlank(message = "La edad no puede estar en blanco")
  private Integer age;
  @NotBlank(message = "La raza no puede estar en blanco")
  private String breed;

  public String getName() {
    return name;
  }

  public Integer getAge() {
    return age;
  }

  public String getBreed() {
    return breed;
  }

  @Override
  public String toString() {
    return "PerroRequest{" +
      "name='" + name + '\'' +
      ", age=" + age +
      ", breed='" + breed + '\'' +
      '}';
  }
}
