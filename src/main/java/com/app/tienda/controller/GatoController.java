package com.app.tienda.controller;

import com.app.tienda.model.request.Gato;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/gatos")
public class GatoController {
  private final Logger log = LoggerFactory.getLogger(this.getClass());
  private ArrayList<Gato> listaGatos = new ArrayList<>();

  @GetMapping
  public ArrayList<Gato> getAll() {
    log.info("Entrando a la función lista de gatos");
    return listaGatos;
  }

  @PostMapping
  private ResponseEntity<String> save(@RequestBody Gato gato) {
    log.info("Metodo save");
    log.info("Mi parametro es: {}", gato);

    listaGatos.add(gato);

    return new ResponseEntity<String>("Se ha agregado con exito", HttpStatus.CREATED);
  }

  @GetMapping("/{id}")
  private ResponseEntity<Gato> getById(@PathVariable Long id) {
    log.info("Entrando a la función getById");
    log.info("Parametro recibido {}", id);

    Gato gatoEncontrado = listaGatos.stream()
      .filter(gato -> gato.getId() == id)
      .findFirst()
      .orElse(null);

    if (gatoEncontrado != null) {
      return new ResponseEntity<>(gatoEncontrado, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @PutMapping("/{id}")
  private ResponseEntity<String> update(@PathVariable Long id, @RequestBody Gato gato) {
    log.info("Entrando a update");
    log.info("Mi parametro es: {}", id);
    log.info("Mi parametro es: {}", gato);

    Optional<Gato> gatoOptional = listaGatos.stream()
      .filter(cat -> gato.getId() == id)
      .findFirst();

    if (gatoOptional.isPresent()) {
      Gato gatoEncontrado = gatoOptional.get();

      gatoEncontrado.setId(gatoEncontrado.getId());
      gatoEncontrado.setName(gato.getName());
      gatoEncontrado.setAge(gatoEncontrado.getAge());
      gatoEncontrado.setBreed(gatoEncontrado.getBreed());
      gatoEncontrado.setHobbies(gatoEncontrado.getHobbies());
      // Actualizar otras propiedades según sea necesario
      return ResponseEntity.ok("Se ha modificado con exito el gato");
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
              .body(("No se encontró el alumno cin ID:" + id) + "por lo que no se pudo actualizar");
    }
  }

  @DeleteMapping("/{id}")
  private ResponseEntity<String> delete(@PathVariable Long id) {
    log.info("Metodo delete");
    log.info("Mi parametro es: {}", id);

    boolean eliminarGato = listaGatos.removeIf(gato -> gato.getId() == id);
    log.info("{}", eliminarGato);

    if (eliminarGato) {
      log.info("Se elimino el gato");
      return ResponseEntity.noContent().build();
      //return ResponseEntity.status(HttpStatus.OK).body("Se elimino con exito"); esta es otra forma de retornar con texto
    } else {
      log.info("No se encontro el id");
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro el id del gato");
    }
  }




}












