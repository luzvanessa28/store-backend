package com.app.tienda.controller;

import com.app.tienda.model.request.SupplierOrderRequest;
import com.app.tienda.model.response.SupplierOrderWithDetailsResponse;
import com.app.tienda.service.ISupplierOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("api/v1/supplierOrders")
public class SupplierOrderController {
  @Autowired
  private ISupplierOrderService supplierOrderService;

  @PostMapping
  public ResponseEntity<?> create(
    @Valid @RequestBody SupplierOrderRequest supplierOrderRequest,
    BindingResult bindingResult
  ) {
    log.info("Creating new supplier order: {}", supplierOrderRequest);

    if(bindingResult.hasErrors()) {
      log.info("Binding result order");
      List<String> errors = bindingResult.getFieldErrors().stream()
        .map(error -> error.getField() + ": " + error.getDefaultMessage())
        .collect(Collectors.toList());

      return ResponseEntity.badRequest().body(errors);
    }
    String orderSaved = supplierOrderService.save(supplierOrderRequest);

    return ResponseEntity.status(HttpStatus.CREATED).body(orderSaved);
  }

  @GetMapping
  public List<SupplierOrderWithDetailsResponse> getAllWithDetails() {
    log.info("Fetching all orders to provider");

    return supplierOrderService.findAllWithDetails();
  }

  @GetMapping("{id}")
  private ResponseEntity<SupplierOrderWithDetailsResponse> findById(@PathVariable Long id) {
    log.info("Fetching provider order by id: {}", id);

    return new ResponseEntity<>(supplierOrderService.findById(id), HttpStatus.OK);
  }

  @GetMapping("/supplier/{supplierId}")
  private ResponseEntity<List<SupplierOrderWithDetailsResponse>> getBySupplierId(@PathVariable Long supplierId) {
    log.info("Fetching supplier orders by supplier id: {}", supplierId);
    return new ResponseEntity<>(supplierOrderService.getBySupplierId(supplierId), HttpStatus.OK);
  }

  @GetMapping("/status/{status}")
  private ResponseEntity<List<SupplierOrderWithDetailsResponse>> getByStatus(@PathVariable String status) {
    log.info("Fetching supplier orders by status: {}", status);
    return new ResponseEntity<>(supplierOrderService.getByStatus(status), HttpStatus.OK);
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> update(
    @PathVariable Long id,
    @Valid @RequestBody SupplierOrderRequest supplierOrderRequest,
    BindingResult bindingResult
  ){
    log.info("Updating supplier order by id: {}", supplierOrderRequest);

    if(bindingResult.hasErrors()) {
      log.info("Binding result order {}", bindingResult);
      List<String> errors = bindingResult.getFieldErrors().stream()
       .map(error -> error.getField() + ": " + error.getDefaultMessage())
       .collect(Collectors.toList());
      return ResponseEntity.badRequest().body(errors);
    }
    SupplierOrderWithDetailsResponse orderUpdated = supplierOrderService.update(id, supplierOrderRequest);
    return ResponseEntity.status(HttpStatus.CREATED).body(orderUpdated);
  }

  @PutMapping("/updateStatus/{id}")
  public String updateStatus(@PathVariable Long id, @RequestBody String status) {
    log.info("Updating supplier order status by id: {} to {}", id, status);

    return supplierOrderService.updateStatus(id, status);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable Long id) {
    log.info("Deleting supplier order by id: {}", id);

    supplierOrderService.delete(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}