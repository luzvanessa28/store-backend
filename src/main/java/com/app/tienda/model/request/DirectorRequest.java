package com.app.tienda.model.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class DirectorRequest {

  @NotBlank(message = "El nombre no puede estar en blanco")
  @Size(min = 3, max=20, message = "El nombre debe de contener de 3 a 20 caracteres")
  private String name;

  @NotBlank(message = "El apellido no puede estar en blanco")
  private String lastName;

  @NotBlank(message = "El apellido no puede estar en blanco")
  private String secondLastName;

  @NotBlank(message = "La edad no puede estar en blanco")
  @Size(min = 2, max = 3, message = "Solo se aceptan numeros")
  private String age;
  @Email
  private String email;

  @NotBlank(message = "Es requerido")
  @Size(min = 10)
  private String phone;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getSecondLastName() {
    return secondLastName;
  }

  public void setSecondLastName(String secondLastName) {
    this.secondLastName = secondLastName;
  }

  public String getAge() {
    return age;
  }

  public void setAge(String age) {
    this.age = age;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }


  @Override
  public String toString() {
    return "Director{" +
      ", name='" + name + '\'' +
      ", lastName='" + lastName + '\'' +
      ", secondLastName='" + secondLastName + '\'' +
      ", age=" + age +
      ", email='" + email + '\'' +
      ", phone='" + phone + '\'' +
      '}';
  }
}
