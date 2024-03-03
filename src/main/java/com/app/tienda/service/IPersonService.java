package com.app.tienda.service;

import com.app.tienda.model.request.PersonRequest;
import com.app.tienda.model.response.PersonResponse;

import java.util.List;
import java.util.Optional;

public interface IPersonService {
  public List<PersonResponse> findAllPerson();
  public PersonResponse savePerson(PersonRequest personRequest);
  public PersonResponse getById(Long id);
  public PersonResponse updatePerson(Long id, PersonRequest personRequest);
  public void deletePerson(Long id);
}
