package com.app.tienda.service;

import com.app.tienda.model.request.CustomerRequest;
import com.app.tienda.model.response.CustomerResponse;

import java.util.List;

public interface ICustumerService {
  public List<CustomerResponse> findAll();
  public CustomerResponse save(CustomerRequest customerRequest);
  public CustomerResponse getById(Long id);
  public List<CustomerResponse> getByName(String name);
  public List<CustomerResponse> getByCity(String city);
  public List<CustomerResponse> getByEmail(String email);
  public CustomerResponse update(Long id, CustomerRequest customerRequest);
}
