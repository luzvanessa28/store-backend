package com.app.tienda.constant;

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
}
