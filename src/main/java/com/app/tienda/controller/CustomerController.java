package com.app.tienda.controller;

import com.app.tienda.model.request.CustomerRequest;
import com.app.tienda.model.response.CustomerResponse;
import com.app.tienda.service.ICustumerService;
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
@RequestMapping("/api/v1/customers")
public class CustomerController {

  @Autowired
  private ICustumerService customerService;

  @GetMapping
  public List<CustomerResponse> getAll() {
    log.info("Getting all customers");
    return customerService.findAll();
  }

  @PostMapping
  public ResponseEntity<?> create(
    @Valid @RequestBody CustomerRequest customer,
    BindingResult bindingResult
  ) {
    log.info("Creating alumn: {}", customer);

    if (bindingResult.hasErrors()) {
      log.info("errors");

      List<String> errors = bindingResult.getFieldErrors().stream()
         .map(error -> error.getField() + ": " + error.getDefaultMessage())
         .collect(Collectors.toList());

      return ResponseEntity.badRequest().body(errors);
    }

    log.info("created customer");
    return ResponseEntity.status(HttpStatus.CREATED).body(customerService.save(customer));
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getById(@PathVariable Long id) {
    log.info("Fetching customer by id: {}", id);

    return new ResponseEntity<>(customerService.getById(id), HttpStatus.OK);
  }

  @GetMapping("/name/{name}")
  public ResponseEntity<List<CustomerResponse>> getByName(@PathVariable String name) {
    log.info("Fetching customer by name: {}", name);

    return new ResponseEntity<>(customerService.getByName(name), HttpStatus.OK);
  }
  @GetMapping("/city/{city}")
  private ResponseEntity<List<CustomerResponse>> findByCity(@PathVariable String city) {
    log.info("Fetching customer by city: {}", city);

    return new ResponseEntity<>(customerService.getByCity(city), HttpStatus.OK);
  }

  @GetMapping("/email/{email}")
  public ResponseEntity<?> findByEmail(@PathVariable String email) {
    log.info("Fetching customer by email: {}", email);

    return new ResponseEntity<>(customerService.getByEmail(email), HttpStatus.OK);
  }
  @PutMapping("/{id}")
  public ResponseEntity<?> update(
    @PathVariable Long id,
    @Valid @RequestBody CustomerRequest customer,
    BindingResult bindingResult
  ) {
    log.info("Updating customer {}", customer);

    if (bindingResult.hasErrors()) {

      List<String> errors = bindingResult.getFieldErrors().stream()
         .map(error -> error.getField() + ": " + error.getDefaultMessage())
         .collect(Collectors.toList());

      return ResponseEntity.badRequest().body(errors);
    }

    return ResponseEntity.status(HttpStatus.OK).body(customerService.update(id, customer));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable Long id) {
    log.info("Deleting customer by id: {}", id);

    customerService.delete(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
