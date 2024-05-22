package com.app.tienda.repository;

import com.app.tienda.entity.SupplierEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierRepository extends JpaRepository<SupplierEntity, Long> {
  /**
   *  Filtrando los proveedores por nombre.
   *
   * @param name filtrar los proveedores por nombre.
   * @return una lista de proveedores que coincidan con el nombre.
   */
  List<SupplierEntity> findByName(String name);
}
