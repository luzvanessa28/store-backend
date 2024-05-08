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

}
