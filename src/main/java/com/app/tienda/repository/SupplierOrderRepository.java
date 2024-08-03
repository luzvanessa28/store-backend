package com.app.tienda.repository;

import com.app.tienda.constant.Querys;
import com.app.tienda.entity.SupplierOrderEntity;
import com.app.tienda.model.response.IProductResponse;
import com.app.tienda.model.response.ISupplierOrderWithDetailsResponse;
import com.app.tienda.model.response.SupplierOrderWithDetailsResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SupplierOrderRepository extends JpaRepository<SupplierOrderEntity, Long> {

  /**
   * Consulta de las órdenes con sus detalles.
   *
   * Ejecuta una consulta nativa para obtener los detalles de las órdenes, incluyendo el ID de la orden,
   * la fecha, el estado, el monto total, el ID del producto, el nombre, el precio y la cantidad.
   *
   * @return una lista de objetos {@link ISupplierOrderWithDetailsResponse} con los detalles de las órdenes de proveedor.
   */
  @Query(value = Querys.QUERY_FIND_ALL_SUPPLIER_ORDERS, nativeQuery = true)
  List<ISupplierOrderWithDetailsResponse> findAllSupplierOrders();

  /**
   *  Filtrando de pedidos por proveedor.
   *
   * @param supplierId filtrar los pedidos por proveedor.
   * @return retorna una lista de pedidos que coincidan con el proveedor.
   */
  @Query(value = Querys.QUERY_FILTER_SUPPLIER_ORDER_BY_SUPPLIER_ID, nativeQuery = true)
  List<ISupplierOrderWithDetailsResponse> getBySupplierId(long supplierId);

  /**
   *  Filtrando de pedidos por estado.
   *
   * @param status filtrar los pedidos por estado.
   * @return retorna una lista de pedidos que coincidan con el estado.
   */
  @Query(value = Querys.QUERY_FILTER_ORDER_BY_STATUS, nativeQuery = true)
  List<ISupplierOrderWithDetailsResponse> getByStatus(String status);
}
