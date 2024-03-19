package com.app.tienda.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/perros")
public class PerroController {
  private final Logger log = LoggerFactory.getLogger(this.getClass());
}
