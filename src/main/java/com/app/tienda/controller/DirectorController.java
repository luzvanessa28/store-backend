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
    return directorService.findAll();
  }

  @PostMapping
  public ResponseEntity<?> create(
    @Valid @RequestBody DirectorRequest director,
    BindingResult bindingResult
  ) {

    if (bindingResult.hasErrors()) {

      List<String> errors = bindingResult.getFieldErrors().stream()
        .map(error -> error.getField() + ": " + error.getDefaultMessage())
        .collect(Collectors.toList());

      return ResponseEntity.badRequest().body(errors);
    }
    return ResponseEntity.status(HttpStatus.CREATED).body(directorService.save(director));
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getById(@PathVariable Long id) {
    return new ResponseEntity<>(directorService.getById(id),HttpStatus.OK);
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> update(
    @PathVariable Long id,
    @Valid @RequestBody DirectorRequest director,
    BindingResult bindingResult
  ) {

    if (bindingResult.hasErrors()) {

      List<String> errors = bindingResult.getFieldErrors().stream()
        .map(error -> error.getField() + ": " + error.getDefaultMessage())
        .collect(Collectors.toList());

      return ResponseEntity.badRequest().body(errors);
    }
    return ResponseEntity.status(HttpStatus.OK).body(directorService.updateDirector(id, director));
  }
}
