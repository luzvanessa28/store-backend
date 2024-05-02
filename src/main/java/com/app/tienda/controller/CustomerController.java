package com.app.tienda.controller;

import com.app.tienda.model.response.CustomerResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {
  @GetMapping
  public List<CustomerResponse> getCustomers() {
    log.info("Getting all customers");
    return null;
  }

}
