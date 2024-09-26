package com.app.tienda.service.impl;


import com.app.tienda.constant.Message;
import com.app.tienda.entity.SupplierEntity;
import com.app.tienda.exception.InternalServerException;
import com.app.tienda.exception.ResourceNotFoundException;
import com.app.tienda.model.request.SupplierRequest;
import com.app.tienda.model.response.SupplierResponse;
import com.app.tienda.repository.SupplierRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataAccessException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;


/**
 * Tes supplierService
 * @author Vanessa
 */
@Slf4j
@ExtendWith(MockitoExtension.class)
class SupplierServiceImplTest {
  @Mock
  private SupplierRepository supplierRepository;
  @Mock
  private ModelMapper modelMapper;

  @InjectMocks
  private SupplierServiceImpl supplierService;

  @Test
  void findAll() {
    List<SupplierEntity> suppliersList = new ArrayList<>();
    SupplierEntity supplierEntity = new SupplierEntity();
    supplierEntity.setId(2L);
    supplierEntity.setName("Luz");
    supplierEntity.setEmail("luz@gmail.com");
    suppliersList.add(supplierEntity);

    when(this.supplierRepository.findAll()).thenReturn(suppliersList);
    when(modelMapper.map(any(SupplierEntity.class), eq(SupplierResponse.class))).thenReturn(new SupplierResponse());

    List<SupplierResponse> suppliers = this.supplierService.findAll();

    assertEquals(1, suppliers.size());
  }

  @Test
  void getByIdSuccess() {
    SupplierEntity supplierEntity = new SupplierEntity();
    supplierEntity.setId(4L);
    supplierEntity.setName("Carlos");
    supplierEntity.setEmail("carlos@gmail.com");

    SupplierResponse supplierResponse = new SupplierResponse();
    supplierResponse.setId(4L);
    supplierResponse.setName("Carlos");
    supplierResponse.setEmail("carlos@gmail.com");

    when(this.supplierRepository.findById(4L)).thenReturn(Optional.of(supplierEntity));
    when(modelMapper.map(supplierEntity, SupplierResponse.class)).thenReturn(supplierResponse);

    SupplierResponse response = this.supplierService.getById(4L);

    assertEquals(supplierResponse, response);
  }

  @Test
  @DisplayName("Should throw ResourceNotFoundException if supplier is not found")
  void getByIdNotFound() {
    assertThrows(ResourceNotFoundException.class, () -> {
      supplierService.getById(1L);
    });
  }

  @Test
  void getByName() {
    List<SupplierEntity> supplierList = new ArrayList<SupplierEntity>();
    SupplierEntity supplierEntity = new SupplierEntity();
    supplierEntity.setId(2L);
    supplierEntity.setName("Vanessa");
    supplierEntity.setEmail("luz@gmail.com");
    supplierList.add(supplierEntity);

    when(this.supplierRepository.findByName("Vanessa")).thenReturn(supplierList);

    List<SupplierResponse> suppliers = this.supplierService.getByName("Vanessa");

    assertNotNull(suppliers);
    assertEquals(1, suppliers.size());
  }

  @Test
  @DisplayName("Returns a list of providers for the specified city.")
  void getByCity() {
    List<SupplierEntity> suppliers = new ArrayList<>();
    SupplierEntity supplierEntity = new SupplierEntity();
    supplierEntity.setId(5L);
    supplierEntity.setName("Melisa");
    suppliers.add(supplierEntity);

    // Configura el mock para que devuelva la lista de proveedores cuando se busque por ciudad
    when(supplierRepository.findByAddressCity("Monterrey")).thenReturn(suppliers);

    // Llama al método getByCity del servicio para obtener los proveedores de la ciudad "Monterrey"
    List<SupplierResponse> suppliersResponse = supplierService.getByCity("Monterrey");

    // Verifica que la lista de respuestas de proveedores no sea nula
    assertNotNull(suppliersResponse);

    // Verificar que el tamaño de la lista es el esperado
    assertEquals(1, suppliersResponse.size());
  }


  @Test
  @DisplayName("Returns a ProviderResponse when a provider is found by email")
  void getByEmail() {
    // Configura la entidad del supplier
    List <SupplierEntity> suppliers = new ArrayList <SupplierEntity>();
    SupplierEntity supplierEntity = new SupplierEntity();
    supplierEntity.setId(4L);
    supplierEntity.setName("David");
    supplierEntity.setEmail("david@gmail.com");
    suppliers.add(supplierEntity);

    // Simula la respuesta del repositorio
    when(supplierRepository.findByEmail("david@gmail.com")).thenReturn(suppliers);

    // Simula el comportamiento del ModelMapper
    SupplierResponse supplierResponse = new SupplierResponse();
    supplierResponse.setId(4L);
    supplierResponse.setName("David");
    supplierResponse.setEmail("david@gmail.com");

    when(modelMapper.map(supplierEntity, SupplierResponse.class)).thenReturn(supplierResponse);

    // Llama al método de prueba para buscar un proveedor por email
    List <SupplierResponse> response = supplierService.getByEmail("david@gmail.com");

    log.info("Response: {}", response);
    log.info("suppliers {}", suppliers);
    // Verifica que el provider es correcto y es el esperado
    assertEquals(1, response.size());
  }

  @Test
  void delete() {
    SupplierEntity supplierEntity = new SupplierEntity();
    supplierEntity.setId(8L);
    supplierEntity.setName("Stasy");

    when(supplierRepository.findById(8L)).thenReturn(Optional.of(supplierEntity));

    this.supplierService.delete(8L);
  }

  /**
   * The provider that does not exist is removed.
   * @throws ResourceNotFoundException supplier not found
   */
  @Test
  @DisplayName("The provider that does not exist is removed.")
  void supplierNotFound() {
    ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
      supplierService.delete(2L);
    });

    log.info("exception: {}", exception.getMessage());

    assertEquals("The id does not exist: 2", exception.getMessage());
  }

  @Test
  void supplierDeleteError() {
    SupplierEntity supplierEntity = new SupplierEntity();
    supplierEntity.setId(8L);
    supplierEntity.setName("Stasy");

    when(supplierRepository.findById(8L)).thenReturn(Optional.of(supplierEntity));

    // Simula que ocurre un DataAccessException al intentar eliminar
    // doThrow solo se utiliza en metodos
    doThrow(new DataAccessException("Error accessing database") {}).when(supplierRepository).deleteById(any());

    InternalServerException exception = assertThrows(InternalServerException.class, () -> {
      this.supplierService.delete(8L);
    });

    log.info("exception: {}", exception.getMessage());

    assertEquals("An error occurred while deleting the supplier", exception.getMessage());
  }

}