package com.app.tienda.repository;

import com.app.tienda.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
  //Estas son 2 formas diferentes para filtrar


  //1
  /**
   *  Filtrando los clientes por nombre.
   *
   * @param name filtrar los clientes por nombre.
   * @return una lista de clientes que coincidan con el nombre.
   */
  List<CustomerEntity> findByName(String name);

  //2
  /**
   *  Filtrando los clientes por ciudad.
   *
   * @param city filtrar los clientes por ciudad.
   * @return una lista de clientes que coincidan con la ciudad.
   */
  @Query(value = "SELECT * FROM customers c INNER JOIN address a ON c.address_id = a.id WHERE a.city = :city", nativeQuery = true)
  List<CustomerEntity> findByAddressCity(String city);

  /**
   *  Filtrando los clientes por email.
   *
   * @param email filtrar los clientes por email.
   * @return una lista de clientes que coincidan con el email.
   */
  @Query(value = "SELECT * FROM CUSTOMERS WHERE email = :email", nativeQuery = true)
  List<CustomerEntity> findByEmail(String email);
}
