package com.app.tienda.service;

import com.app.tienda.model.request.PerroRequest;
import com.app.tienda.model.response.PerroResponse;

import java.util.List;

public interface IPerroService {
  public List<PerroResponse> findAll();
  public PerroResponse save(PerroRequest perroRequest);
  public PerroResponse getById(Long id);
  public PerroResponse updatePerro(Long id, PerroRequest perroRequest);
  public void delete(Long id);
}
