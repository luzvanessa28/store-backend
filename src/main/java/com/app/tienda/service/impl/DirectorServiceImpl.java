package com.app.tienda.service.impl;

import com.app.tienda.entity.DirectorEntity;
import com.app.tienda.exception.InternalServerException;
import com.app.tienda.exception.ResourceNotFoundException;
import com.app.tienda.model.request.DirectorRequest;
import com.app.tienda.model.response.DirectorResponse;
import com.app.tienda.repository.DirectorRepository;
import com.app.tienda.service.IDirectorService;
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
public class DirectorServiceImpl implements IDirectorService {
  private final Logger log = LoggerFactory.getLogger(this.getClass());

  @Autowired
  private DirectorRepository directorRepository;
  @Autowired
  private ModelMapper modelMapper;

  @Override
  public List<DirectorResponse> findAll() {
    log.info("FindAll en serviceImpl");

    List<DirectorEntity> directors = directorRepository.findAll();
    log.info("directors: {}", directors);

    return directors.stream()
      .map(directorEntity -> modelMapper.map(directorEntity, DirectorResponse.class))
      .collect(Collectors.toList());
  }

  @Override
  public DirectorResponse save(DirectorRequest directorRequest) {
    log.info("Save en serviceImpl {}", directorRequest);

    try {
      DirectorEntity directorEntity = modelMapper.map(directorRequest, DirectorEntity.class);
      log.info("directorEntity: {}", directorEntity);

      DirectorEntity saved = directorRepository.save(directorEntity);
      log.info("save: {}", saved);

      return modelMapper.map(saved, DirectorResponse.class);
    } catch (Exception e) {
      log.error("Se produjo un error al guardar al director", e.getMessage());
      throw new InternalServerException("Se produjo un error al guardar el director", e);
    }

  }

  @Override
  public DirectorResponse getById(Long id) {
    log.info("getById en serviceImpl: {}", id);

    Optional<DirectorEntity> directorOptional = directorRepository.findById(id);
    log.info("optional: {}", directorOptional);

    return directorOptional
      .map(directorEntity -> modelMapper.map(directorEntity, DirectorResponse.class))
      .orElseThrow(() -> new ResourceNotFoundException("El director no ha sido encontrado"));
  }

  @Override
  public DirectorResponse updateDirector(Long id, DirectorRequest directorRequest) {
    log.info("updateDirector: {}", directorRequest);


    try {
      Optional<DirectorEntity> directorOptional = directorRepository.findById(id);
      log.info("directorOptional: {}", directorOptional);

      if (directorOptional.isPresent()) {
        DirectorEntity directorEntity = directorOptional.get();
        modelMapper.map(directorRequest, directorEntity);
        log.info("directorEntity: {}", directorEntity);

        DirectorEntity saved = directorRepository.save(directorEntity);
        log.info("save: {}", saved);

        return modelMapper.map(saved, DirectorResponse.class);
      } else {
        throw new ResourceNotFoundException("El director no ha sido encontrado");
      }
    } catch (RuntimeException e)/*(Exception e)*/ {
      log.error("Se produjo un error al guardar la persona", e.getMessage());
      throw new InternalServerException("Se produjo un error al guardar la persona", e);
    }
  }

  @Override
  public void deleteDirector(Long id) {

    try {
      Optional<DirectorEntity> directorOptional = directorRepository.findById(id);

      if (directorOptional.isPresent()) {
        directorRepository.deleteById(id);
      } else {
        throw new ResourceNotFoundException("El director no ha sido encontrado");
      }
    } catch (Exception e) {
      log.error("Se produjo un error al eliminar el director: {}", e.getMessage());
      throw new InternalServerException("El director no ha sido encontrado", e);
    }
  }


}
