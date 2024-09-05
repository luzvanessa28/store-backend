package com.app.tienda.constant;

import org.hibernate.sql.Select;

public class Querys {

  public static final String QUERY_FILTER_PRODUCTS_BY_NAME = "SELECT * FROM PRODUCTS WHERE name = :name";
  public static final String QUERY_FILTER_PRODUCTS_BY_CATEGORY = "SELECT * FROM PRODUCTS WHERE category = :category";
  public static final String QUERY_FILTER_PRODUCTS_BY_SUPPLIER = "SELECT \n" +
    "    p.id,\n" +
    "    p.name,\n" +
    "    p.description,\n" +
    "    p.price,\n" +
    "    p.quantity_In_Inventory as inventory,\n" +
    "    p.category,\n" +
    "    s.name AS supplier\n" +
    "FROM \n" +
    "    products p\n" +
    "INNER JOIN \n" +
    "    suppliers s ON p.supplier_id = s.id\n" +
    "WHERE \n" +
    "    s.id = :supplierId";

  public static final String QUERY_FIND_ALL_SUPPLIER_ORDERS = "SELECT so.id as orderId, so.date, so.status, so.total_amount as totalAmount, \n" +
    "p.id as productId, p.name as product, p.price, sop.quantity \n" +
    "FROM SUPPLIER_ORDERS so\n" +
    "JOIN SUPPLIER_ORDER_PRODUCT sop on so.id = sop.supplier_order_id\n" +
    "JOIN PRODUCTS p on sop.product_id = p.id";

  public static final String QUERY_FILTER_SUPPLIER_ORDER_BY_SUPPLIER_ID = "SELECT so.id as orderId, so.date, so.status, so.total_amount as totalAmount, \n" +
  "p.id as productId, p.name as product, p.price, sop.quantity\n" +
  "FROM SUPPLIER_ORDERS so\n" +
  "JOIN SUPPLIER_ORDER_PRODUCT sop on so.id = sop.supplier_order_id\n" +
  "JOIN PRODUCTS p on sop.product_id = p.id where so.supplier_id = :supplierId";
  public static final String QUERY_FILTER_ORDER_BY_STATUS = "SELECT so.id as orderId, so.date, so.status, so.total_amount as totalAmount,\n" +
    "  p.id as productId, p.name as product, p.price, sop.quantity\n" +
    "  FROM SUPPLIER_ORDERS so\n" +
    "  JOIN SUPPLIER_ORDER_PRODUCT sop on so.id = sop.supplier_order_id\n" +
    "  JOIN PRODUCTS p on sop.product_id = p.id where so.status = :status";
}
