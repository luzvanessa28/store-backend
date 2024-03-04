package com.app.tienda.service.impl;

import com.app.tienda.entity.PersonEntity;
import com.app.tienda.exception.InternalServerException;
import com.app.tienda.exception.ResourceNotFoundException;
import com.app.tienda.model.request.PersonRequest;
import com.app.tienda.model.response.PersonResponse;
import com.app.tienda.repository.PersonRepository;
import com.app.tienda.service.IPersonService;
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
public class PersonServiceImpl implements IPersonService {
  private final Logger log = LoggerFactory.getLogger(this.getClass());
  @Autowired
  private PersonRepository personRepository;

  @Autowired
  private ModelMapper modelMapper;

  @Override
  public List<PersonResponse> findAllPerson() {
    log.info("PersonServiceImpl - find all users");

    List<PersonEntity> persons = personRepository.findAll();

    //modelmapper -> me sirve para hacer el mapeo(transformacion) de un objeto a otro objeto
    return persons.stream()
      .map(personEntity -> modelMapper.map(personEntity, PersonResponse.class))
      .collect(Collectors.toList());
  }

  @Override
  public PersonResponse savePerson(PersonRequest personRequest) {
    log.info("PersonServiceImpl - savePerson: {}", personRequest);

    try {
      // Mapeo de un objeto personRequest a un objeto PersonEntity
      PersonEntity personEntity = modelMapper.map(personRequest, PersonEntity.class);
      log.info("personEntity: {}", personEntity);

      PersonEntity savedPerson = personRepository.save(personEntity);

      return modelMapper.map(savedPerson, PersonResponse.class);

    } catch (Exception e) {
      log.error("Se produjo un error al guardar la persona", e.getMessage());
      throw new InternalServerException("Se produjo un error al guardar la persona", e);
    }
  }

  @Override
  public PersonResponse getById(Long id) {
    log.info("PersonServiceImpl - getById: {}", id);

    Optional<PersonEntity> personOptional = personRepository.findById(id);
    log.info("personOptional: {}", personOptional);

    return personOptional
      .map(personEntity -> modelMapper.map(personEntity, PersonResponse.class))
      .orElseThrow(() -> new ResourceNotFoundException("La persona no ha sido encontrada"));
  }


  @Override
  public PersonResponse updatePerson(Long id, PersonRequest personRequest) {
    log.info("PersonServiceImpl - updatePerson: {}", personRequest);

    try {
      Optional<PersonEntity> personOptional = personRepository.findById(id);

      if (personOptional.isPresent()) {
        // Mapeo de un objeto personRequest a un objeto PersonEntity
        PersonEntity personEntity = personOptional.get();
        modelMapper.map(personRequest, personEntity);
        log.info("personEntity: {}", personEntity);

        PersonEntity savedPerson = personRepository.save(personEntity);

        return modelMapper.map(savedPerson, PersonResponse.class);
      } else {
        throw new ResourceNotFoundException("La persona no ha sido encontrada");
      }
    } catch (DataAccessException e) {
      log.error("Se produjo un error al guardar la persona", e.getMessage());
      throw new InternalServerException("Se produjo un error al guardar la persona", e);
    }
  }

  @Override
  public void deletePerson(Long id) {
    log.info("PersonServiceImpl - deletePerson: {}", id);

    try {
      Optional<PersonEntity> personOptional = personRepository.findById(id);

      if (personOptional.isPresent()) {
        personRepository.deleteById(id);
      } else {
        throw new ResourceNotFoundException("La persona no ha sido encontrada");
      }
    } catch (DataAccessException e) {
      log.error("Se produjo un error al eliminar la persona", e.getMessage());
      throw new InternalServerException("Se produjo un error al eliminar a la persona", e);
    }
  }


}
