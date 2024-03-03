package com.app.tienda.service;

import com.app.tienda.model.request.AlumnoRequest;
import com.app.tienda.model.response.AlumnoResponse;

import java.util.List;

public interface IAlumnoService {
  public List<AlumnoResponse> findAllAlumns();
  public AlumnoResponse saveAlumn(AlumnoRequest alumnoRequest);
  public AlumnoResponse getById(Long id);
  public AlumnoResponse updateAlumn(Long id, AlumnoRequest alumnoRequest);
}
