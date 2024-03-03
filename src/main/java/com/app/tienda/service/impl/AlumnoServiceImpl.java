package com.app.tienda.service.impl;

import com.app.tienda.entity.AlumnoEntity;
import com.app.tienda.exception.InternalServerException;
import com.app.tienda.exception.ResourceNotFoundException;
import com.app.tienda.model.request.AlumnoRequest;
import com.app.tienda.model.response.AlumnoResponse;
import com.app.tienda.repository.AlumnoRepository;
import com.app.tienda.service.IAlumnoService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AlumnoServiceImpl implements IAlumnoService {
  private final Logger log = LoggerFactory.getLogger(this.getClass());
  @Autowired
  private AlumnoRepository alumnoRepository;
  @Autowired
  private ModelMapper modelMapper;
  @Override
  public List<AlumnoResponse> findAllAlumns() {
    log.info("Metodo findAllAlumns");

    List<AlumnoEntity> alumnos = alumnoRepository.findAll();
    log.info("variable alumnos {}", alumnos);

    return alumnos.stream()
      .map(alumnoEntity -> modelMapper.map(alumnoEntity, AlumnoResponse.class))
      .collect(Collectors.toList());
  }

  @Override
  public AlumnoResponse saveAlumn(AlumnoRequest alumnoRequest) {
    log.info("AlumnoServiceImpl - saveAlumn: {}", alumnoRequest);

    try {
      log.info("Estoy dentro de try");
      AlumnoEntity alumnoEntity= modelMapper.map(alumnoRequest, AlumnoEntity.class);
      log.info("AlumnoEntity: {}", alumnoEntity);

      AlumnoEntity saveAlumn = alumnoRepository.save(alumnoEntity);
      log.info("saveAlumno: {}", saveAlumn);

      return modelMapper.map(saveAlumn, AlumnoResponse.class);

    } catch (Exception e) {
      log.info("Estoy dentro de catch");
      log.error("Se produjo un error al guardar la persona", e.getMessage());
      throw new InternalServerException("Se produjo un error al guardar la persona", e);
    }
  }

  @Override
  public AlumnoResponse getById(Long id) {
    log.info("Metodo getById en AlumnoServiceImpl {}", id);

    Optional<AlumnoEntity> alumnoOptional = alumnoRepository.findById(id);
    log.info("alumnoOptional: {}", alumnoOptional);

    return alumnoOptional
      .map(alumnoEntity -> modelMapper.map(alumnoEntity, AlumnoResponse.class))
      .orElseThrow(() -> new ResourceNotFoundException("La persona no ha sido encontrada"));
  }

  @Override
  public AlumnoResponse updateAlumn(Long id, AlumnoRequest alumnoRequest) {
    log.info("id: {}", id);
    log.info("Mi metodo updateAlumn: {}", alumnoRequest);

    try {
      Optional<AlumnoEntity> alumnOptional = alumnoRepository.findById(id);
      log.info("Alumno: {}", alumnOptional);

      if (alumnOptional.isPresent()) {
        AlumnoEntity alumnoEntity = alumnOptional.get();
        modelMapper.map(alumnoRequest, alumnoEntity);
        log.info("Alumno entity{}", alumnoEntity);

        //return null;
      } else {
        log.info("else");
      }
    } catch (DataAccessException e) {
      log.info("catch");
    }
    return null;
  }












}
