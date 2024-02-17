package com.app.tienda.controller;

import com.app.tienda.model.request.Director;
import com.app.tienda.service.IDirectorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/director")
public class DirectorController {
  private final Logger log = LoggerFactory.getLogger(this.getClass());

  @Autowired
  private IDirectorService directorService;

  @GetMapping
  public ArrayList<Director> getAll(){
    log.info("Estoy dentro del metodo getAll");

    //ArrayList<Director> listDirectors = directorService.getAll();
    return directorService.getAll();
  }

  @PostMapping
  private ResponseEntity<String> save(@RequestBody Director director) {
    log.info("Estoy dentro del metodo save");
    log.info("mi parametro es: {}", director);

    Director directorAgregado = directorService.save(director);
    return new ResponseEntity<>("Se agrego con exito el director", HttpStatus.CREATED);
  }

  @GetMapping("/{id}")
  private ResponseEntity<Director> getById(@PathVariable Long id) {
    log.info("Entrando a mi funcion getById");
    log.info("parametro: {}", id);

    Director directorEncontrado = directorService.getById(id);

    return new ResponseEntity<>(directorEncontrado, HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  private ResponseEntity<String> update(@PathVariable Long id, @RequestBody Director director) {
    log.info("Entrando a mi metodo update");
    log.info("parametro: {}", id);
    log.info("parametro: {}", director);

    Director directorActualizado = directorService.update(id, director);
    log.info("valor de mi variable {}", directorActualizado);

    if (directorActualizado != null) {
      return ResponseEntity.ok("Se ha actualizo con exito el director");
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
      .body(("No se encontro el id" + id));
    }
  }

  @DeleteMapping("/{id}")
  private ResponseEntity<String> delete(@PathVariable Long id) {
    log.info("Entrando a mi funcion delete");
    log.info("parametro: {}", id);

    Boolean directorEliminado = directorService.delete(id);
    log.info("mi variable es: {}", directorEliminado);

    if (directorEliminado) {
      log.info("Se elimino correctamente el director");
      return ResponseEntity.noContent().build();
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro ningun director con ID: " + id);
    }
  }
}





















