package com.app.tienda.controller;

import com.app.tienda.model.request.ProductRequest;
import com.app.tienda.model.response.IProductResponse;
import com.app.tienda.model.response.ProductResponse;
import com.app.tienda.service.IProductService;
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
@RequestMapping("/api/v1/product")
public class ProductController {
  @Autowired
  private IProductService productService;
  @GetMapping
  public List<ProductResponse> getAll() {
    log.info("Getting all Product");
    return productService.findAll();
  }

  @PostMapping
  public ResponseEntity<Object> save(
    @Valid @RequestBody ProductRequest prroductRequest,
    BindingResult bindingResult
  ) {
    log.info("Creating product: {}", prroductRequest);

    if (bindingResult.hasErrors()) {

      List<String> errors = bindingResult.getFieldErrors().stream()
        .map(error -> error.getField() + ": " + error.getDefaultMessage())
        .collect(Collectors.toList());

      return ResponseEntity.badRequest().body(errors);
    }

    ProductResponse productSaved = productService.save(prroductRequest);

    return ResponseEntity.status(HttpStatus.CREATED).body(productSaved);
  }

  @GetMapping("/{id}")
  private ResponseEntity<ProductResponse> findById(@PathVariable Long id) {
    log.info("Fetching product by id: {}", id);

    return new ResponseEntity<>(productService.getById(id), HttpStatus.OK);
  }

  @GetMapping("/name/{name}")
  private ResponseEntity<List<ProductResponse>> findByName(@PathVariable String name) {
    log.info("Fetching product by name: {}", name);

    return new ResponseEntity<>(productService.getByName(name), HttpStatus.OK);
  }

  @GetMapping("/category/{category}")
  private ResponseEntity<List<ProductResponse>> findByCategory(@PathVariable String category) {
    log.info("Fetching product by category: {}", category);

    return new ResponseEntity<>(productService.getByCategory(category), HttpStatus.OK);
  }

  @GetMapping("/supplier/{supplierId}")
  private ResponseEntity<List<IProductResponse>> findProductBySupplier(@PathVariable Long supplierId) {
    log.info("Fetching product by supplierId: {}", supplierId);

    return new ResponseEntity<>(
      productService.getSuppilerById(supplierId), HttpStatus.OK
    );
  }

  @PutMapping("/{id}")
  private ResponseEntity<?> update(
    @PathVariable Long id,
    @Valid @RequestBody ProductRequest productRequest,
    BindingResult bindingResult
  ){
    log.info("Update product by id: {}", id);

    if (bindingResult.hasErrors()) {
      log.info("Error updating product");

      List<String> errors = bindingResult.getFieldErrors().stream()
       .map(error -> error.getField() + ": " + error.getDefaultMessage())
       .collect(Collectors.toList());
      log.info("errors: " + errors);

      return ResponseEntity.badRequest().body(errors);
    }
    ProductResponse productUpdated = productService.update(id, productRequest);
    log.info("productUpdated: " + productUpdated);

    return ResponseEntity.status(HttpStatus.CREATED).body(productUpdated);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable Long id) {
    log.info("product controller {}", id);
    productService.delete(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
