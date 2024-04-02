package com.app.tienda.service.impl;

import com.app.tienda.constant.Message;
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

    List<AlumnoEntity> alumnos = alumnoRepository.findAll();

    return alumnos.stream()
      .map(alumnoEntity -> modelMapper.map(alumnoEntity, AlumnoResponse.class))
      .collect(Collectors.toList());
  }

  @Override
  public AlumnoResponse saveAlumn(AlumnoRequest alumnoRequest) {

    try {
      AlumnoEntity alumnoEntity= modelMapper.map(alumnoRequest, AlumnoEntity.class);

      AlumnoEntity saveAlumn = alumnoRepository.save(alumnoEntity);

      return modelMapper.map(saveAlumn, AlumnoResponse.class);

    } catch (Exception e) {

      log.error("Se produjo un error al guardar al alumno", e.getMessage());
      throw new InternalServerException(Message.SAVE_ERROR + " al alumno", e);
    }
  }

  @Override
  public AlumnoResponse getById(Long id) {

    Optional<AlumnoEntity> alumnoOptional = alumnoRepository.findById(id);

    return alumnoOptional
      .map(alumnoEntity -> modelMapper.map(alumnoEntity, AlumnoResponse.class))
      .orElseThrow(() -> new ResourceNotFoundException(Message.ID_NOT_FOUND));
  }

  @Override
  public AlumnoResponse updateAlumn(Long id, AlumnoRequest alumnoRequest) {

    try {
      Optional<AlumnoEntity> alumnOptional = alumnoRepository.findById(id);

      if (alumnOptional.isPresent()) {
        AlumnoEntity alumnoEntity = alumnOptional.get();
        modelMapper.map(alumnoRequest, alumnoEntity);

        AlumnoEntity savedAlumno = alumnoRepository.save(alumnoEntity);
        return modelMapper.map(savedAlumno, AlumnoResponse.class);
      } else {
        throw new ResourceNotFoundException(Message.ID_NOT_FOUND);
      }
    } catch (DataAccessException e) {
      log.error("Se produjo un error al actualizar al alumno", e.getMessage());
      throw new InternalServerException(Message.UPDATE_ERROR + " al alumno", e);
    }
  }

  @Override
  public void deleteAlumn(Long id) {

    try {
      Optional<AlumnoEntity> alumnoOptional = alumnoRepository.findById(id);

      if (alumnoOptional.isPresent()) {
        alumnoRepository.deleteById(id);
      } else {
        throw new ResourceNotFoundException(Message.ID_NOT_FOUND);
      }

    } catch (DataAccessException e) {
      log.error("Se produjo un error al eliminar el alumno", e.getMessage());
      throw new InternalServerException(Message.DELETE_ERROR + " al alumno", e);
    }
  }
}
