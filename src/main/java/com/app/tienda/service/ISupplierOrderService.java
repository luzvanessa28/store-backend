package com.app.tienda.service;

import com.app.tienda.model.request.SupplierOrderRequest;
import com.app.tienda.model.response.SupplierOrderWithDetailsResponse;

import java.util.List;

public interface ISupplierOrderService {
  public String save(SupplierOrderRequest supplierOrderRequest);
  public List<SupplierOrderWithDetailsResponse> findAllWithDetails();
  public SupplierOrderWithDetailsResponse findById(Long id);
  public List<SupplierOrderWithDetailsResponse> getBySupplierId(Long supplierId);
  public List<SupplierOrderWithDetailsResponse> getByStatus(String status);
  public SupplierOrderWithDetailsResponse update(Long id, SupplierOrderRequest supplierOrderRequest);
  public String updateStatus(Long id, String status);
  public void delete(Long id);
}
