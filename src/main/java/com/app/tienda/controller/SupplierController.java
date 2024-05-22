package com.app.tienda.controller;

import com.app.tienda.model.request.SupplierRequest;
import com.app.tienda.model.response.SupplierResponse;
import com.app.tienda.service.ISupplierService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@RestController
@RequestMapping("/api/v1/supplier")
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
    log.info("Fetching provider by id: {}", id);

    return new ResponseEntity<>(supplierService.getById(id), HttpStatus.OK);
  }
}
