package com.app.tienda.controller;

import com.app.tienda.model.request.Alumno;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/alumnos")
public class AlumnoController {

  private final Logger log = LoggerFactory.getLogger(this.getClass());
  private ArrayList<Alumno> listaAlumnos = new ArrayList<>();

  @GetMapping //consultar todos los registros
  public ArrayList<Alumno> getAll() {
    log.info("Entrando a la función lista");
    return listaAlumnos;
  }

  @GetMapping("/{id}") //consultar un solo registro por su id
  private ResponseEntity<Alumno> getById(@PathVariable Integer id) {
    log.info("Entrando a la función getById");
    log.info("Parametro recibido {}", id);

    Alumno alumnoEncontrado = listaAlumnos.stream()
      .filter(alumno -> alumno.getId() == id) // [..].findFirst()
      .findFirst()
      .orElse(null);

    if (alumnoEncontrado != null) { // Se ha encontrado un alumno
      return new ResponseEntity<>(alumnoEncontrado, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping //agregrar un nuevo registro
  private  ResponseEntity<String> save(@RequestBody Alumno alumno) { // @RequestBody representa el valor del cuerpo de la solicitud
    log.info("Method save() ");
    log.info("Mi parametro es: {}", alumno);

    listaAlumnos.add(alumno);

    return new ResponseEntity<>("Se ha agregado con exito el alumno", HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  private ResponseEntity<String> update(@PathVariable Integer id, @RequestBody Alumno alumno) {
    log.info("Metodo update");
    log.info("Mi parametro es: {}", id);
    log.info("Mi parametro es: {}", alumno);

    Optional<Alumno> alumnoOptional = listaAlumnos.stream()
            .filter(alumn -> alumn.getId() == id)
            .findFirst(); // findFirst - entrega el primer elemento encontrado del filter

    if (alumnoOptional.isPresent()) {
      Alumno alumnoEncontrado = alumnoOptional.get();

      alumnoEncontrado.setName(alumno.getName());
      alumnoEncontrado.setLastName(alumno.getLastName());
      alumnoEncontrado.setSecondLastName(alumno.getSecondLastName());
      alumnoEncontrado.setAge(alumnoEncontrado.getAge());
      alumnoEncontrado.setEmail(alumnoEncontrado.getEmail());
      alumnoEncontrado.setPhone(alumnoEncontrado.getPhone());
      // Actualizar otras propiedades según sea necesario
      return ResponseEntity.ok("Se ha modificado con exito el alumno");
    } else {
      // Si no se encuentra el alumno, devolver un mensaje indicando que no se pudo actualizar
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
              .body("No se encontró el alumno con ID: " + id + ". No se realizó ninguna actualización.");
    }
  }

  @DeleteMapping("/{id}")
  private ResponseEntity<String> delete(@PathVariable Integer id) {
    log.info("Method delete() ");
    log.info("Mi parametro es: {}", id);

    boolean alumnoEliminado = listaAlumnos.removeIf(alumno -> alumno.getId() == id);

    log.info("resultado: {}", alumnoEliminado);

    if (alumnoEliminado) {
      log.info("verdadero");
      return ResponseEntity.noContent().build();
    } else {
      log.info("falso");
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el alumno con ID: " + id);
    }
  }

}
