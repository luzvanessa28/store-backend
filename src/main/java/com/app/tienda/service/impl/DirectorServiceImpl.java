package com.app.tienda.service.impl;

import com.app.tienda.model.request.Director;
import com.app.tienda.service.IDirectorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class DirectorServiceImpl implements IDirectorService {
  private final Logger log = LoggerFactory.getLogger(this.getClass());
  private ArrayList<Director> listaDirectores = new ArrayList<>();

  @Override
  public ArrayList<Director> getAll() {
    log.info("Metodo getAll");
    return listaDirectores;
  }

  @Override
  public Director save(Director director) {
    log.info("Metodo save");
    log.info("parametro: {}", director);
    listaDirectores.add(director);

    return director;
  }

  @Override
  public Director getById(Long id) {
    log.info("Metodo getById");
    log.info("parametro: {}", id);

    Director directorEncontrado = listaDirectores.stream()
     .filter(director -> director.getId() == id)
     .findFirst()
     .orElse(null);

    return directorEncontrado;
  }

  @Override
  public Director update(Long id, Director director) {
    log.info("Metodo update");
    log.info("parametro id: {}", id);
    log.info("parametro director: {}", director);

    Optional<Director> directorOptional = listaDirectores.stream()
      .filter(directorEncontrado -> directorEncontrado.getId() == id)
      .findFirst();

    log.info("Valor de directorOptional: {}", directorOptional);

    if (directorOptional != null) {
      Director directorActualizado = directorOptional.get();

      directorActualizado.setName(director.getName());
      directorActualizado.setLastName(director.getLastName());
      directorActualizado.setSecondLastName(director.getSecondLastName());
      directorActualizado.setAge(director.getAge());
      directorActualizado.setEmail(director.getEmail());
      directorActualizado.setPhone(director.getPhone());
      directorActualizado.setAddress(director.getAddress());
      return directorActualizado;
    }
    return null;
  }

  @Override
  public Boolean delete(Long id) {
    log.info("funcion delete");
    log.info("parametro: {}", id);

    Boolean directorEliminado = listaDirectores.removeIf(directorRemove -> directorRemove.getId() == id);
    log.info("resultado de directorEliminado {}", directorEliminado);

    return directorEliminado;
  }


}