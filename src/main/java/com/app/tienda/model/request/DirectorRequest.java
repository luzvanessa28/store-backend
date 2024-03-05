package com.app.tienda.model.request;

public class DirectorRequest {
  private String name;
  private String lastName;
  private String secondLastName;
  private String age;
  private String email;
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
