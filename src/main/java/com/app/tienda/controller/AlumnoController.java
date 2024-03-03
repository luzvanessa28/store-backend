package com.app.tienda.controller;

import com.app.tienda.model.request.AlumnoRequest;
import com.app.tienda.model.request.PersonRequest;
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
    log.info("Metodo getAlumns");
    return alumnoService.findAllAlumns();
  }

  @PostMapping
  public ResponseEntity<?> create(
    @Valid @RequestBody AlumnoRequest alumn,
    BindingResult bindingResult) {
    log.info("Creating alumn: {}", alumn);

    if (bindingResult.hasErrors()) {
      log.info("Se ha producido un error: {}", bindingResult.hasErrors());
      List<String> errors = bindingResult.getFieldErrors().stream()
        .map(error -> error.getField() + ": " + error.getDefaultMessage())
        .collect(Collectors.toList());

      return ResponseEntity.badRequest().body(errors);
    }

    log.info("Updated alumn");
    return ResponseEntity.status(HttpStatus.CREATED).body(alumnoService.saveAlumn(alumn));
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getById(@PathVariable Long id) {
    log.info("Metodo getById: {}", id);

    return new ResponseEntity<>(alumnoService.getById(id),HttpStatus.OK);
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> update(
    @PathVariable Long id,
    @Valid @RequestBody AlumnoRequest alumno,
    BindingResult bindingResult
  ) {
    log.info("Mi metodo update: {}", alumno);

    if (bindingResult.hasErrors()) {
      log.info("Se produjo un error: {}", bindingResult.hasErrors());

      List<String> errores= bindingResult.getFieldErrors().stream()
        .map(error -> error.getField() + ":" + error.getDefaultMessage())
        .collect(Collectors.toList());

      return ResponseEntity.badRequest().body(errores);
    }

    log.info("Alumno modificado correctamente");
    return ResponseEntity.status(HttpStatus.OK).body(alumnoService.updateAlumn(id, alumno));
  }

























}
