package com.app.tienda.controller;

import com.app.tienda.model.request.Profesor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/profesor")
public class ProfesorController {
  private final Logger log = LoggerFactory.getLogger(this.getClass());
  private ArrayList<Profesor> listaProfesores = new ArrayList<>();

  @GetMapping
  public ArrayList<Profesor> getAll() {
    log.info("Entrando a la funcion lista Profesor");

    return listaProfesores;
  }

  @PostMapping
  private ResponseEntity<String> save(@RequestBody Profesor profesor) {
    log.info("Entrando a la funcion save");
    log.info("Mi parametro es {}", profesor);

    listaProfesores.add(profesor);

    return new ResponseEntity<>("Se agrego con exito el profesor", HttpStatus.CREATED);
  }

  @GetMapping("/{id}")
  private ResponseEntity<Profesor> getById(@PathVariable Long id) {
    log.info("Entrando a mi funcion getById");
    log.info("parametro: {}", id);

    Profesor profesorEncontrado = listaProfesores.stream()
      .filter(profesor -> profesor.getId() == id)
      .findFirst()
      .orElse(null);

    if (profesorEncontrado != null) {
      return new ResponseEntity<>(profesorEncontrado, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @PutMapping("/{id}")
  private  ResponseEntity<String> update(@PathVariable Long id,
                                         @RequestBody Profesor profesor
  ) {
    log.info("Update");
    log.info("id: {}", id);
    log.info("profesor modificado: {}", profesor);

    Optional<Profesor> profesorOptional = listaProfesores.stream()
      .filter(profe -> profe.getId() == id)
      .findFirst();

    if (profesorOptional.isPresent()) {
      Profesor profesorEncontrado = profesorOptional.get();

      profesorEncontrado.setName(profesor.getName());
      profesorEncontrado.setLastName(profesor.getLastName());
      profesorEncontrado.setSecondLastName(profesor.getSecondLastName());
      profesorEncontrado.setAge(profesor.getAge());
      profesorEncontrado.setEmail(profesor.getEmail());
      profesorEncontrado.setPhone(profesor.getPhone());
      profesorEncontrado.setAddress(profesor.getAddress());

      return  ResponseEntity.ok("Se ha modificado el profesor");
    } else {
      return  ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(("No se encontró el profesor con ID:" + id) + "por lo que no se pudo actualizar");
    }
  }

  @DeleteMapping("/{id}")
  private ResponseEntity<String> delete(@PathVariable Long id) {
    log.info("Metodo delete");
    log.info("id: {}", id);

    boolean eliminarProfesor = listaProfesores.removeIf(profesor -> profesor.getId() == id);
    log.info("resultado: {}", eliminarProfesor);

    if (eliminarProfesor) {
      log.info("verdadero");
      return ResponseEntity.noContent().build();
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro ningun profesor con ID: " + id);
    }
  }
}



















