package com.app.tienda.service.impl;


import com.app.tienda.entity.AddressEntity;
import com.app.tienda.entity.CustomerEntity;
import com.app.tienda.model.request.AddressRequest;
import com.app.tienda.model.request.CustomerRequest;
import com.app.tienda.model.response.CustomerResponse;
import com.app.tienda.repository.AddressRepository;
import com.app.tienda.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tes customerService
 * @author Vanessa
 */
@Slf4j
@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

  @Mock
  private CustomerRepository customerRepository;
  @Mock
  private ModelMapper modelMapper;
  @Mock
  private AddressRepository addressRepository;


  @InjectMocks
  private CustomerServiceImpl customerService;

  @Test
  void findAll() {
    List<CustomerEntity> customerEntityList = new ArrayList<CustomerEntity>();
    CustomerEntity customerEntity = new CustomerEntity();
    customerEntity.setId(1L);
    customerEntity.setName("Vanessa");
    customerEntityList.add(customerEntity);

    when(this.customerRepository.findAll()).thenReturn(customerEntityList);

    List<CustomerResponse> customers = this.customerService.findAll();

    //assertNotNull(customers);
    assertEquals(1, customers.size());
  }

  @Test
  void save() {
    CustomerRequest customerRequest = new CustomerRequest();
    customerRequest.setName("Customer");
    customerRequest.setPhone("1234567890");
    customerRequest.setEmail("customer@example.com");
    customerRequest.setGender("F");
    AddressRequest addressRequest = new AddressRequest();
    addressRequest.setStreet("Street 1");
    addressRequest.setCity("City 1");
    customerRequest.setAddress(addressRequest);

    CustomerEntity customerEntity = new CustomerEntity();
    AddressEntity addressEntity = new AddressEntity();

    when(addressRepository.save(any(AddressEntity.class))).thenReturn(addressEntity);
    when(customerRepository.save(any(CustomerEntity.class))).thenReturn(customerEntity);

    when(modelMapper.map(any(CustomerEntity.class), eq(CustomerResponse.class))).thenReturn(new CustomerResponse());

    CustomerResponse response = this.customerService.save(customerRequest);

    log.info("Customer: {}", response);
    assertNotNull(response);
  }
}