package com.app.tienda.service.impl;


import com.app.tienda.entity.SupplierEntity;
import com.app.tienda.exception.ResourceNotFoundException;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;


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

}