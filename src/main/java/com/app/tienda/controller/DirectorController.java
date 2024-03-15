package com.app.tienda.controller;

import com.app.tienda.model.request.DirectorRequest;
import com.app.tienda.model.response.DirectorResponse;
import com.app.tienda.service.IDirectorService;
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
@RequestMapping("/api/v1/director")
public class DirectorController {
  private final Logger log = LoggerFactory.getLogger(this.getClass());

  @Autowired
  private IDirectorService directorService;

  @GetMapping
  public List<DirectorResponse> getAll() {
    log.info("Entrando a la funcion getAll");

    return directorService.findAll();
  }

  @PostMapping
  public ResponseEntity<?> create(
    @Valid @RequestBody DirectorRequest director,
    BindingResult bindingResult
  ) {
    log.info("parametro director: {}", director);
    log.info("bindingResult: {}", bindingResult.hasErrors());

    if (bindingResult.hasErrors()) {
      log.info("se produjo un error {}", bindingResult.hasErrors());

      List<String> errors = bindingResult.getFieldErrors().stream()
        .map(error -> error.getField() + ": " + error.getDefaultMessage())
        .collect(Collectors.toList());

      return ResponseEntity.badRequest().body(errors);
    }
    log.info("director creado correctamente");
    return ResponseEntity.status(HttpStatus.CREATED).body(directorService.save(director));
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getById(@PathVariable Long id) {
    log.info("director por id: {}", id);

    return new ResponseEntity<>(directorService.getById(id),HttpStatus.OK);
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> update(
    @PathVariable Long id,
    @Valid @RequestBody DirectorRequest director,
    BindingResult bindingResult
  ) {
    log.info("director por id: {}", id);
    log.info("bindingResult: {}", bindingResult.hasErrors());



    if (bindingResult.hasErrors()) {
      log.info("se produjo un error {}", bindingResult.hasErrors());

      List<String> errors = bindingResult.getFieldErrors().stream()
        .map(error -> error.getField() + ": " + error.getDefaultMessage())
        .collect(Collectors.toList());
      log.info("errors: {}" , errors);

      return ResponseEntity.badRequest().body(errors);
    }
    log.info("director actualizado correctamente");
    return ResponseEntity.status(HttpStatus.OK).body(directorService.updateDirector(id, director));
  }
}
