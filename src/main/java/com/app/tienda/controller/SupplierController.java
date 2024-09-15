package com.app.tienda.controller;

import com.app.tienda.model.request.SupplierRequest;
import com.app.tienda.model.response.SupplierResponse;
import com.app.tienda.service.ISupplierService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/v1/suppliers")
public class SupplierController {
  @Autowired
  private ISupplierService supplierService;

  @GetMapping
  public List<SupplierResponse> getAll() {
    log.info("Getting all suppliers");
    return supplierService.findAll();
  }

  @PostMapping
  public ResponseEntity<?> create(
    @Valid @RequestBody SupplierRequest supplier,
    BindingResult bindingResult
  ) {
    log.info("creating supplier: {}", supplier);

    if (bindingResult.hasErrors()) {

      List<String> errors = bindingResult.getFieldErrors().stream()
        .map(error -> error.getField() + ": " + error.getDefaultMessage())
        .collect(Collectors.toList());

      return ResponseEntity.badRequest().body(errors);
    }
    log.info("supplier created successfully");
    return ResponseEntity.status(HttpStatus.CREATED).body(supplierService.save(supplier));
  }

  @GetMapping("/{id}")
  private ResponseEntity<SupplierResponse> findById(@PathVariable Long id) {
    log.info("Fetching supplier by id: {}", id);

    return new ResponseEntity<>(supplierService.getById(id), HttpStatus.OK);
  }

  @GetMapping("/name/{name}")
  public ResponseEntity<List<SupplierResponse>> getByName(@PathVariable String name) {
    log.info("Fetching customer by name: {}", name);

    return new ResponseEntity<>(supplierService.getByName(name), HttpStatus.OK);
  }

  @GetMapping("/city/{city}")
  private ResponseEntity<List<SupplierResponse>> findByCity(@PathVariable String city) {
    log.info("Fetching supplier by city: {}", city);

    return new ResponseEntity<>(supplierService.getByCity(city), HttpStatus.OK);
  }

  @GetMapping("/email/{email}")
  public ResponseEntity<?> findByEmail(@PathVariable String email) {
    log.info("Fetching supplier by email: {}", email);

    return new ResponseEntity<>(supplierService.getByEmail(email), HttpStatus.OK);
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> update(
    @PathVariable Long id,
    @Valid @RequestBody SupplierRequest supplierRequest,
    BindingResult bindingResult
  ) {
    log.info("Updating supplier by id: {}", id);

    if (bindingResult.hasErrors()) {

      List<String> errors = bindingResult.getFieldErrors().stream()
        .map(error -> error.getField() + ": " + error.getDefaultMessage())
        .collect(Collectors.toList());

      return ResponseEntity.badRequest().body(errors);
    }

    SupplierResponse supplierUpdated = supplierService.update(id, supplierRequest);

    return ResponseEntity.status(HttpStatus.CREATED).body(supplierUpdated);
  }


  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable Long id) {
    log. info("Deleting supplier by id: {}", id);

    supplierService.delete(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
