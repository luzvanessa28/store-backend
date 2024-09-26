package com.app.tienda.service.impl;


import com.app.tienda.entity.SupplierEntity;
import com.app.tienda.model.response.SupplierResponse;
import com.app.tienda.repository.SupplierRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

}