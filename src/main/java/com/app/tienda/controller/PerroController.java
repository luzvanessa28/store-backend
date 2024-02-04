package com.app.tienda.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/perros")
public class PerroController {

  private final Logger log = LoggerFactory.getLogger(this.getClass());
  @GetMapping
  public String saludo() {
    log.info("Entrando a la función saludo");
    return "Hola, buenas tardes.";
  }
  @GetMapping("/bye")
  public String despedida() {
    log.info("Entrando a la función despedida");
    return "Adios";
  }

  @GetMapping("/{name}") // aquí estoy recibiendo un parametro name por ruta. Ej. .../{name}
  //así se hace uso de @PathVariable http://localhost:8080/api/perros/Stasy
  public String saludoPerro(@PathVariable String name) {
    log.info("Entrando a la función saludoPerro");
    log.info(name);
    return "Hola, " + name;
  }
  @GetMapping("/suma") //así se hace uso de @RequestParam http://localhost:8080/api/perros/suma?num1=4&num2=5
  public Integer suma(@RequestParam("num1") Integer num1, @RequestParam("num2") Integer num2) {
    log.info("Entrando a la función suma");
    log.info("param num1 {}", num1);
    log.info("param num2 {}", num2);
    return num1 + num2;
  }

}

