package com.app.tienda.service;

import com.app.tienda.model.request.SupplierRequest;
import com.app.tienda.model.response.SupplierResponse;

import java.util.List;

public interface ISupplierService {
  public List<SupplierResponse> findAll();
  public SupplierResponse save(SupplierRequest supplierRequest);
  public SupplierResponse getById(Long id);
  public List<SupplierResponse> getByName(String name);
  public List<SupplierResponse> getByCity(String city);
  public List<SupplierResponse> getByEmail(String email);
}
