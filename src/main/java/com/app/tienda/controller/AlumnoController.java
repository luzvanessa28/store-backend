package com.app.tienda.controller;

import com.app.tienda.model.request.AlumnoRequest;
import com.app.tienda.model.response.AlumnoResponse;
import com.app.tienda.service.IAlumnoService;
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
@RequestMapping("/api/v1/alumns")
public class AlumnoController {
  private final Logger log = LoggerFactory.getLogger(this.getClass());
  @Autowired
  private IAlumnoService alumnoService;

  @GetMapping
  public List<AlumnoResponse> getAllAlumns() {
    log.info("Fetching all alumns");
    return alumnoService.findAllAlumns();
  }

  @PostMapping
  public ResponseEntity<?> create(
    @Valid @RequestBody AlumnoRequest alumn,
    BindingResult bindingResult
  ) {
      log.info("Creating alumn: {}", alumn);

    if (bindingResult.hasErrors()) {
      List<String> errors = bindingResult.getFieldErrors().stream()
        .map(error -> error.getField() + ": " + error.getDefaultMessage())
        .collect(Collectors.toList());

      return ResponseEntity.badRequest().body(errors);
    }

    log.info("Created alumn");
    return ResponseEntity.status(HttpStatus.CREATED).body(alumnoService.saveAlumn(alumn));
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getById(@PathVariable Long id) {
    log.info("Fetching alumn by id: {}", id);

    return new ResponseEntity<>(alumnoService.getById(id),HttpStatus.OK);
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> update(
    @PathVariable Long id,
    @Valid @RequestBody AlumnoRequest alumno,
    BindingResult bindingResult
  ) {
    log.info("Updating alumn {}", alumno);

    if (bindingResult.hasErrors()) {

      List<String> errors= bindingResult.getFieldErrors().stream()
        .map(error -> error.getField() + ":" + error.getDefaultMessage())
        .collect(Collectors.toList());

      return ResponseEntity.badRequest().body(errors);
    }
    log.info("Updated alumn");
    return ResponseEntity.status(HttpStatus.OK).body(alumnoService.updateAlumn(id, alumno));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable Long id) {
    log.info("Deleting alumn by id: {}", id);

    alumnoService.deleteAlumn(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

























}
