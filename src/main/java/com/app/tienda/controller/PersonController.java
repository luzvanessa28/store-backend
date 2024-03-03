package com.app.tienda.controller;

import com.app.tienda.entity.PersonEntity;
import com.app.tienda.model.request.PersonRequest;
import com.app.tienda.model.response.PersonResponse;
import com.app.tienda.service.IPersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/person")
public class PersonController {
  private final Logger log = LoggerFactory.getLogger(this.getClass());

  @Autowired
  private IPersonService personService;

  @GetMapping
  public List<PersonResponse> getAllPersons() {
    log.info("Fetching all persons");
    return personService.findAllPerson();
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getById(@PathVariable Long id) {
    log.info("Fetching person by id: {}", id);

    return new ResponseEntity<>(personService.getById(id), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<?> create(
    @Valid @RequestBody PersonRequest user,
    BindingResult bindingResult
  ) {
    log.info("Creating user: {}", user);

    if (bindingResult.hasErrors()) {
      log.info("Se ha producido un error: {}", bindingResult.hasErrors());
      // Lista de errores de cada campo
      List<String> errors = bindingResult.getFieldErrors().stream()
        .map(error -> error.getField() + ": " + error.getDefaultMessage())
        .collect(Collectors.toList());

      return ResponseEntity.badRequest().body(errors);
    }

    log.info("Created person");
    return ResponseEntity.status(HttpStatus.CREATED).body(personService.savePerson(user));
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> update(
    @PathVariable Long id,
    @Valid @RequestBody PersonRequest user,
    BindingResult bindingResult
  ) {
    log.info("Updating user: {}", user);

    if (bindingResult.hasErrors()) {
      log.info("Se ha producido un error: {}", bindingResult.hasErrors());
      // Lista de errores de cada campo
      List<String> errors = bindingResult.getFieldErrors().stream()
      .map(error -> error.getField() + ": " + error.getDefaultMessage())
      .collect(Collectors.toList());

      return ResponseEntity.badRequest().body(errors);
    }

    log.info("Updated person");
    return ResponseEntity.status(HttpStatus.OK).body(personService.updatePerson(id, user));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable Long id) {
    log.info("Deleting person by id: {}", id);

    personService.deletePerson(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
