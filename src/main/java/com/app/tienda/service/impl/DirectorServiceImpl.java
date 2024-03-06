package com.app.tienda.service.impl;

import com.app.tienda.entity.DirectorEntity;
import com.app.tienda.entity.PersonEntity;
import com.app.tienda.exception.InternalServerException;
import com.app.tienda.model.request.DirectorRequest;
import com.app.tienda.model.response.DirectorResponse;
import com.app.tienda.model.response.PersonResponse;
import com.app.tienda.repository.DirectorRepository;
import com.app.tienda.service.IDirectorService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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











}
