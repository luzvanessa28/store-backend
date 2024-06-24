package com.app.tienda.repository;

import com.app.tienda.constant.Querys;
import com.app.tienda.entity.ProductEntity;
import com.app.tienda.model.response.IProductResponse;
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
  @Query(value = Querys.QUERY_FILTER_PRODUCTS_BY_NAME, nativeQuery = true)
  List<ProductEntity> findByName(String name);

  /**
   *  Filtrando los productos por category.
   *
   * @param category filtrar los productos por category.
   * @return retorna una lista de productos que coincidan con el category.
   */
  @Query(value = Querys.QUERY_FILTER_PRODUCTS_BY_CATEGORY, nativeQuery = true)
  List<ProductEntity> findByCategory(String category);

  /**
   *  Filtrando de productos por proveedor.
   *
   * @param supplierId filtrar los productos por proveedor.
   * @return retorna una lista de productos que coincidan con el proveedor.
   */
  @Query(value = Querys.QUERY_FILTER_PRODUCTS_BY_SUPPLIER, nativeQuery = true)
  List<IProductResponse> findProductsBySuppiler(long supplierId);
}
