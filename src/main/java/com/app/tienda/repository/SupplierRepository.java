package com.app.tienda.repository;

import com.app.tienda.entity.SupplierEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierRepository extends JpaRepository<SupplierEntity, Long> {
  /**
   *  Filtrando los proveedores por nombre.
   *
   * @param name filtrar los proveedores por nombre.
   * @return retorna una lista de proveedores que coincidan con el nombre.
   */

  @Query(value = "SELECT * FROM PROVIDERS WHERE name = :name", nativeQuery = true)
  List<SupplierEntity> findByName(String name);

  /**
   * Obtiene una lista de proveedores que viven en la ciudad especificada.
   *
   * @param city la ciudad para filtrar los proveedores.
   * @return una lista de proveedores en la ciudad dada.
   */
  @Query(value = "SELECT * FROM SUPPLIER s INNER JOIN ADDRESS a ON p.address_id = a.id WHERE a.city = :city", nativeQuery = true)
  List<SupplierEntity> findByAddressCity(String city);

  /**
   *  Filtrando los proveedores por email.
   *
   * @param email filtrar los proveedores por email.
   * @return una lista de proveedores que coincidan con el email.
   */
  @Query(value = "SELECT * FROM SUPPLIER WHERE email = :email", nativeQuery = true)
  List<SupplierEntity> findByEmail(String email);


}
