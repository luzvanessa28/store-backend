package com.app.tienda.service;

import com.app.tienda.model.request.CustomerRequest;
import com.app.tienda.model.response.CustomerResponse;

import java.util.List;

public interface ICustumerService {
  public List<CustomerResponse> findAll();
  public CustomerResponse save(CustomerRequest customerRequest);
  public CustomerResponse getById(Long id);
  public List<CustomerResponse> getByCity(String city);
}
