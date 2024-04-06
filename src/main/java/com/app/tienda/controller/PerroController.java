package com.app.tienda.controller;

import com.app.tienda.entity.PerroEntity;
import com.app.tienda.model.request.PerroRequest;
import com.app.tienda.model.response.PerroResponse;
import com.app.tienda.service.IPerroService;
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
@RequestMapping("/api/v1/perros")
public class PerroController {
  private final Logger log = LoggerFactory.getLogger(this.getClass());
  @Autowired
  private IPerroService perroService;
  @GetMapping
  public List<PerroResponse> getAll() {
    log.info("Fetching all Perro");

    return perroService.findAll();
  }
  @PostMapping
  public ResponseEntity<?> create(
    @Valid @RequestBody PerroRequest perro,
    BindingResult bindingResult
  ) {
    log.info("Creating perro: {}", perro);

    if (bindingResult.hasErrors()) {
      List<String> errors = bindingResult.getFieldErrors().stream()
        .map(error -> error.getField() + ": " + error.getDefaultMessage())
        .collect(Collectors.toList());

      return ResponseEntity.badRequest().body(errors);
    }

    return ResponseEntity.status(HttpStatus.CREATED).body(perroService.save(perro));
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getById(@PathVariable Long id) {
    log.info("Fetching perro by id: {}", id);

    return new ResponseEntity<>(perroService.getById(id), HttpStatus.OK);
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> update(
    @PathVariable Long id,
    @Valid @RequestBody PerroRequest perro,
    BindingResult bindingResult
  ) {
    log.info("Updating Perro: {}",perro);

    if (bindingResult.hasErrors()) {

      List <String> errors = bindingResult.getFieldErrors().stream()
        .map(error -> error.getField() + ":" + error.getDefaultMessage())
        .collect(Collectors.toList());

      return ResponseEntity.badRequest().body(errors);
    }

    log.info("Updated perro");
    return ResponseEntity.status(HttpStatus.OK).body(perroService.update(id, perro));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable Long id) {
    log.info("Deleting Perro by id: {}", id);

    perroService.delete(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
