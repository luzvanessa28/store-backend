package com.app.tienda.repository;

import com.app.tienda.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

  /**
   *  Filtrando los productos por nombre.
   *
   * @param name filtrar los productos por nombre.
   * @return retorna una lista de productos que coincidan con el nombre.
   */
  @Query(value = "SELECT * FROM PRODUCTS WHERE name = :name", nativeQuery = true)
  List<ProductEntity> findByName(String name);

  /**
   *  Filtrando los productos por category.
   *
   * @param category filtrar los productos por category.
   * @return retorna una lista de productos que coincidan con el category.
   */
  @Query(value = "SELECT * FROM PRODUCTS WHERE category = :category", nativeQuery = true)
  List<ProductEntity> findByCategory(String category);
}
