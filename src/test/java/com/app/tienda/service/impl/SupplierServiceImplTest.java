package com.app.tienda.service.impl;


import com.app.tienda.constant.Message;
import com.app.tienda.entity.AddressEntity;
import com.app.tienda.entity.SupplierEntity;
import com.app.tienda.exception.InternalServerException;
import com.app.tienda.exception.ResourceNotFoundException;
import com.app.tienda.model.request.AddressRequest;
import com.app.tienda.model.request.SupplierRequest;
import com.app.tienda.model.response.SupplierResponse;
import com.app.tienda.repository.AddressRepository;
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

  @Mock
  private AddressRepository addressRepository;

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
  @DisplayName("Save a provider and return a ProviderResponse with the correct data")
  void save() {
    // Configura la solicitud del proveedor
    SupplierRequest supplierRequest = new SupplierRequest();
    supplierRequest.setName("Daniel");
    supplierRequest.setPhone("56274067");
    supplierRequest.setEmail("daniel@gmail.com");

    // Configura la dirección del proveedor
    AddressRequest addressRequest = new AddressRequest();
    addressRequest.setStreet("Morelos");
    addressRequest.setCity("Tlaxiaco");
    addressRequest.setDelegation("San Diego");
    supplierRequest.setAddress(addressRequest);

    SupplierResponse supplierResponse = new SupplierResponse();
    supplierResponse.setName("Daniel");
    supplierResponse.setPhone("56274067");
    supplierResponse.setEmail("daniel@gmail.com");


    // Crea las entidades necesarias para simular las operaciones
    SupplierEntity supplierEntity = new SupplierEntity();
    AddressEntity addressEntity = new AddressEntity();
    supplierEntity.setAddress(addressEntity);// Asigna la dirección a la entidad del proveedor

    // Simula el mock del modelMapper para convertir el objeto SupplierRequest a un objeto SupplierEntity
    when(modelMapper.map(any(SupplierRequest.class), eq(SupplierEntity.class))).thenReturn(supplierEntity);
    // Simula el mock de guardar en el repositorio de dirección
    when(addressRepository.save(any(AddressEntity.class))).thenReturn(addressEntity);

    // Simula el mock de guardar en el repositorio de proveedores
    when(supplierRepository.save(any(SupplierEntity.class))).thenReturn(supplierEntity);
    // Simula el mock del modelMapper para convertir el objeto SupplierEntity a un objeto SupplierResponse
    when(modelMapper.map(any(SupplierEntity.class), eq(SupplierResponse.class))).thenReturn(supplierResponse);

    // Llama al método save del servicio para guardar la entidad de proveedor
    SupplierResponse response = this.supplierService.save(supplierRequest);

    // Verifica que la respuesta no sea nula
    log.info("Supplier: {}", response);
    assertNotNull(response);

    verify(addressRepository, times(1)).save(any(AddressEntity.class));
    verify(supplierRepository, times(1)).save(any(SupplierEntity.class));
  }

  @Test
  @DisplayName("Throws the exception InternalServerException when an error occurs while creating the provider")
  void saveError() {
    // Simulación de los datos de un nuevo proveedor
    SupplierRequest supplierRequest = new SupplierRequest();
    supplierRequest.setName("Daniel");
    supplierRequest.setPhone("56274067");
    supplierRequest.setEmail("daniel@gmail.com");

    // Configura la dirección del proveedor
    AddressRequest addressRequest = new AddressRequest();
    addressRequest.setStreet("Morelos");
    addressRequest.setCity("Tlaxiaco");
    addressRequest.setDelegation("San Diego");
    supplierRequest.setAddress(addressRequest);

    // Verifica que se lance una InternalServerException al fallar el guardado de la dirección
    assertThrows(InternalServerException.class, () -> {
      supplierService.save(supplierRequest);
    });
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
  void update() {
    SupplierEntity supplierMock = new SupplierEntity();
    supplierMock.setId(12L);
    supplierMock.setName("Luz");
    supplierMock.setPhone("65436910");
    supplierMock.setEmail("luz@gmail.com");

    SupplierRequest supplierUpdate = new SupplierRequest();
    supplierUpdate.setName("Luz");
    supplierUpdate.setPhone("65436910");
    supplierUpdate.setEmail("luz@gmail.com");

    SupplierResponse supplierResponseMock = new SupplierResponse();
    supplierResponseMock.setId(12L);
    supplierResponseMock.setName("Luz");
    supplierResponseMock.setPhone("65436910");
    supplierResponseMock.setEmail("luz@gmail.com");

    when(supplierRepository.findById(12L)).thenReturn(Optional.of(supplierMock));
    when(modelMapper.map(any(), eq(SupplierResponse.class))).thenReturn(supplierResponseMock);

    SupplierResponse supplierResponse = this.supplierService.update(12L, supplierUpdate);
    log.info("SupplierResponse {}", supplierResponse);

    assertNotNull(supplierResponse);
  }

  @Test
  @DisplayName("An exception is thrown when trying to update a non-existent client.")
  void updateNonExistentSupplier() {
    SupplierRequest supplierRequest = new SupplierRequest();
    supplierRequest.setName("Jane Updated");
    supplierRequest.setPhone("5432101525");
    supplierRequest.setEmail("jane_updated@gmail.com");

    // Simulamos que no se encuentra el cliente
    when(supplierRepository.findById(2L)).thenReturn(Optional.empty());

    ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
      supplierService.update(2L, supplierRequest);
    });

    assertEquals(Message.ID_NOT_FOUND, exception.getMessage());
  }

  @Test
  @DisplayName("Throws InternalServerException when a DataAccessException occurs during supplier update")
  void updateError() {
    SupplierRequest supplierRequest = new SupplierRequest();
    supplierRequest.setName("Stasy");
    supplierRequest.setPhone("5432101525");
    supplierRequest.setEmail("stasy@gmail.com");

    // Simula que el proveedor existe en la base de datos
    SupplierEntity supplierEntity = new SupplierEntity();
    when(supplierRepository.findById(8L)).thenReturn(Optional.of(supplierEntity));

    // Simula que ocurre un DataAccessException al intentar guardar el proveedor actualizado
    when(supplierRepository.save(any(SupplierEntity.class))).thenThrow(new DataAccessException("Error accessing database") {});

    // Verifica que se lanza la InternalServerException cuando ocurre un DataAccessException
    InternalServerException exception = assertThrows(InternalServerException.class, () -> {
      this.supplierService.update(8L, supplierRequest);
    });

    log.info("exception: {}", exception.getMessage());

    // Verifica que el mensaje de excepción es correcto
    assertTrue(exception.getMessage().contains(Message.UPDATE_ERROR));
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

    assertEquals("Error deleting the record", exception.getMessage());
  }
}