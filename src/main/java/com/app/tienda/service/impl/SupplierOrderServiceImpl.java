package com.app.tienda.service.impl;

import com.app.tienda.constant.Message;
import com.app.tienda.entity.ProductEntity;
import com.app.tienda.entity.SupplierEntity;
import com.app.tienda.entity.SupplierOrderEntity;
import com.app.tienda.entity.SupplierOrderProduct;
import com.app.tienda.enums.OrderStatus;
import com.app.tienda.exception.InternalServerException;
import com.app.tienda.exception.ResourceNotFoundException;
import com.app.tienda.model.request.SupplierOrderRequest;
import com.app.tienda.model.response.ISupplierOrderWithDetailsResponse;
import com.app.tienda.model.response.SupplierOrderProductResponse;
import com.app.tienda.model.response.SupplierOrderWithDetailsResponse;
import com.app.tienda.repository.ProductRepository;
import com.app.tienda.repository.SupplierOrderRepository;
import com.app.tienda.repository.SupplierRepository;
import com.app.tienda.service.IInventoryService;
import com.app.tienda.service.ISupplierOrderService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SupplierOrderServiceImpl implements ISupplierOrderService {

  @Autowired
  private SupplierOrderRepository supplierOrderRepository;
  @Autowired
  private ModelMapper modelMapper;
  @Autowired
  private SupplierRepository supplierRepository;
  @Autowired
  private ProductRepository productRepository;
  @Autowired
  private IInventoryService inventoryService;

  @Override
  public String save(SupplierOrderRequest supplierOrderRequest) {
    log.info("SupplierOrderServiceImpl.save: {}", supplierOrderRequest);

    SupplierEntity supplierEntity = supplierRepository.findById(supplierOrderRequest.getSupplierId())
      .orElseThrow(() -> new ResourceNotFoundException("supplier not found"));

    //verificar que los productos existan.
    List<SupplierOrderProduct> ordersProducts = supplierOrderRequest.getProducts().stream().
      map(product -> {
        SupplierOrderProduct orderProduct= new SupplierOrderProduct();
        orderProduct.setProductId(product.getProductId());
        orderProduct.setQuantity(product.getQuantity());
        return orderProduct;
      }).collect(Collectors.toList());

    BigDecimal totalAmount = ordersProducts.stream()
      .map(productDto -> {
        ProductEntity productEntity = productRepository.findById(productDto.getProductId())
         .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        return productEntity.getPrice().multiply(BigDecimal.valueOf(productDto.getQuantity()));
      }).reduce(BigDecimal.ZERO, BigDecimal::add);

    try {
      SupplierOrderEntity orderEntity = new SupplierOrderEntity();
      orderEntity.setId(null);
      orderEntity.setStatus(OrderStatus.PENDING.name());
      orderEntity.setDate(LocalDateTime.now());
      orderEntity.setProducts(ordersProducts);
      orderEntity.setTotalAmount(totalAmount);
      orderEntity.setSupplier(supplierEntity);
      SupplierOrderEntity saved = supplierOrderRepository.save(orderEntity);

      return Message.SAVE;

    } catch (Exception e) {
      log.error("There was an error in creating the supplier order : {}", e.getMessage());
      throw new InternalServerException(Message.SAVE_ERROR);
    }
  }

  @Override
  public List<SupplierOrderWithDetailsResponse> findAllWithDetails() {
    log.info("findAllWithDetails");

    List<ISupplierOrderWithDetailsResponse> supplierOrders = supplierOrderRepository.findAllSupplierOrders();

    Map<Long, SupplierOrderWithDetailsResponse> orderMap = new LinkedHashMap<>();

    // Obtener o crear la orden proveedor si no existe en el mapa
    supplierOrders.forEach(projection -> {
      Long orderId = projection.getOrderId();
      SupplierOrderWithDetailsResponse orderResponse = orderMap.computeIfAbsent(orderId, id -> {
        SupplierOrderWithDetailsResponse response = new SupplierOrderWithDetailsResponse();
        response.setId(id);
        response.setDate(projection.getDate());
        response.setStatus(projection.getStatus());
        response.setTotalAmount(projection.getTotalAmount());
        response.setProducts(new ArrayList<>());
        return response;
      });

      // Crear el producto de la orden proveedor y agregarlo a la lista de productos
      SupplierOrderProductResponse productResponse = new SupplierOrderProductResponse();
      productResponse.setName(projection.getProduct());
      productResponse.setUnitPrice(projection.getPrice());
      productResponse.setQuantity(projection.getQuantity());

      orderResponse.getProducts().add(productResponse);
    });

    return new ArrayList<>(orderMap.values());
  }

  @Override
  public SupplierOrderWithDetailsResponse findById(Long id) {
    log.info("ProviderOrderServiceImpl - find provider order by id {}", id);

    SupplierOrderEntity orderOptional = supplierOrderRepository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

    SupplierOrderWithDetailsResponse orderResponseDTO = new SupplierOrderWithDetailsResponse();
    orderResponseDTO.setId(orderOptional.getId());
    orderResponseDTO.setStatus(orderOptional.getStatus());
    orderResponseDTO.setTotalAmount(orderOptional.getTotalAmount());
    orderResponseDTO.setDate(orderOptional.getDate());

    List<SupplierOrderProductResponse> productsResponse = orderOptional.getProducts().stream()
      .map(product -> {
        ProductEntity productEntity = productRepository.findById(product.getProductId())
          .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        SupplierOrderProductResponse productResponse = new SupplierOrderProductResponse();
        productResponse.setName(productEntity.getName());
        productResponse.setQuantity(product.getQuantity());
        productResponse.setUnitPrice(productEntity.getPrice());

        return productResponse;
      }).collect(Collectors.toList());

    orderResponseDTO.setProducts(productsResponse);

    return orderResponseDTO;
  }

  @Override
  public List<SupplierOrderWithDetailsResponse> getBySupplierId(Long supplierId) {
    log.info("SupplierOrderServiceImpl - getBySupplierId: {}", supplierId);
    List<ISupplierOrderWithDetailsResponse> supplierOrders = supplierOrderRepository.getBySupplierId(supplierId);

    return this.responseOrder(supplierOrders);
  }

  @Override
  public List<SupplierOrderWithDetailsResponse> getByStatus(String status) {
    log.info("SupplierOrderServiceImpl - getByStatus: {}", status);

    List<ISupplierOrderWithDetailsResponse> supplierOrders = supplierOrderRepository.getByStatus(status);

    return  this.responseOrder(supplierOrders);
  }

  @Override
  public SupplierOrderWithDetailsResponse update(Long id, SupplierOrderRequest supplierOrderRequest) {
    log.info("SupplierOrderServiceImpl - update: {} {}", id, supplierOrderRequest);

    // Verificar si la orden existe
    SupplierOrderEntity supplierOrderEntity = supplierOrderRepository.findById(id)
      .orElseThrow(()-> new ResourceNotFoundException("Supplier order not found with ID: " + id));

    // Verificar si la orden existe
    SupplierEntity supplier = supplierRepository.findById(supplierOrderRequest.getSupplierId())
      .orElseThrow(() -> new ResourceNotFoundException("The supplier was not found"));

    //verificar que los productos existan.
    List<SupplierOrderProduct> ordersProducts = supplierOrderRequest.getProducts().stream().
      map(product -> {
        SupplierOrderProduct orderProduct = new SupplierOrderProduct();
        orderProduct.setProductId(product.getProductId());
        orderProduct.setQuantity(product.getQuantity());

        return orderProduct;
      }).collect(Collectors.toList());

    BigDecimal totalAmount = ordersProducts.stream()
      .map(productDto -> {
        ProductEntity productEntity = productRepository.findById(productDto.getProductId())
          .orElseThrow(() -> new ResourceNotFoundException("Product not found")); //TODO: Siempre que se encuentre una excepción se detiene el programa y se dispara la excepción

        return productEntity.getPrice().multiply(BigDecimal.valueOf(productDto.getQuantity()));
      }).reduce(BigDecimal.ZERO, BigDecimal::add);

    try {
      supplierOrderEntity.setProducts(ordersProducts);
      supplierOrderEntity.setTotalAmount(totalAmount);
      supplierOrderEntity.setSupplier(supplier);
      SupplierOrderEntity saved = supplierOrderRepository.save(supplierOrderEntity);
      return modelMapper.map(saved, SupplierOrderWithDetailsResponse.class);
    } catch (DataAccessException e) {
      log.error("Error updating the supplier order: {}", e.getMessage());
      throw new InternalServerException(Message.UPDATE_ERROR + "the supplier order with ID: " + id, e);
    }
  }

  @Transactional
  @Override
  public String updateStatus(Long id, String status) {
    log.info("method updateStatus - implementation {} {}", id, status);

    if ( !(status.equals(OrderStatus.COMPLETED.name()) || status.equals(OrderStatus.CANCELLED.name())) ) {
      throw new IllegalArgumentException("The state is incorrect.");
    }

    SupplierOrderEntity orderOptional = supplierOrderRepository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("The order was not found."));

    if( !(orderOptional.getStatus().equals(OrderStatus.PENDING.name()))) {
      throw new IllegalArgumentException("You are only allowed to update the status of pending orders.");
    }

    try {
      orderOptional.setStatus(status);
      SupplierOrderEntity supplierOrderEntity= supplierOrderRepository.save(orderOptional);

      // TODO: Se va a actualizar el inventario
      if(supplierOrderEntity.getStatus().equals(OrderStatus.COMPLETED.name())) {
        this.inventoryService.update(supplierOrderEntity.getProducts());
      }

      return "Status updated successfully";
    } catch (DataAccessException e) {
      log.info("There was an error updating the order status: {}", e.getMessage());
      throw new InternalServerException(Message.UPDATE_ERROR + "the status of the order " + id, e);
    }
  }

  @Override
  public void delete(Long id) {
    log.info("Deleting {}", id);

    try {
      Optional<SupplierOrderEntity> orderOptional =supplierOrderRepository.findById(id);

      if (orderOptional.isPresent()) {
        supplierOrderRepository.deleteById(id);
      } else {
        throw new ResourceNotFoundException(Message.ID_NOT_FOUND + ": " + id);
      }
    } catch (DataAccessException e) {
      log.info("An error occurred while deleting the order {}", e.getMessage());
      throw new InternalServerException(Message.DELETE_ERROR, e);
    }
  }

  private List<SupplierOrderWithDetailsResponse> responseOrder(List<ISupplierOrderWithDetailsResponse> providerOrders) {
    Map<Long, SupplierOrderWithDetailsResponse> orderMap = new LinkedHashMap<>();

    // Obtener o crear la orden proveedor si no existe en el mapa
    providerOrders.forEach(projection -> {
      Long orderId = projection.getOrderId();

      SupplierOrderWithDetailsResponse orderResponse = orderMap.computeIfAbsent(orderId, id -> {
        SupplierOrderWithDetailsResponse response = new SupplierOrderWithDetailsResponse();
        response.setId(id);
        response.setDate(projection.getDate());
        response.setStatus(projection.getStatus());
        response.setTotalAmount(projection.getTotalAmount());
        response.setProducts(new ArrayList<>());
        return response;
      });

      // Crear el producto de la orden proveedor y agregarlo a la lista de productos
      SupplierOrderProductResponse productResponse = new SupplierOrderProductResponse();
      productResponse.setName(projection.getProduct());
      productResponse.setUnitPrice(projection.getPrice());
      productResponse.setQuantity(projection.getQuantity());

      orderResponse.getProducts().add(productResponse);
    });

    return new ArrayList<>(orderMap.values());
  }
}