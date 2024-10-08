package com.app.tienda.service.impl;


import com.app.tienda.entity.AddressEntity;
import com.app.tienda.entity.CustomerEntity;
import com.app.tienda.exception.InternalServerException;
import com.app.tienda.exception.ResourceNotFoundException;
import com.app.tienda.model.request.AddressRequest;
import com.app.tienda.model.request.CustomerRequest;
import com.app.tienda.model.response.CustomerResponse;
import com.app.tienda.repository.AddressRepository;
import com.app.tienda.repository.CustomerRepository;
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

  @Test
  @DisplayName("Throws the exception InternalServerException when an error occurs while creating the customer")
  void saveError() {
    CustomerRequest customerRequestError = new CustomerRequest();
    customerRequestError.setName("Customer");
    customerRequestError.setPhone("1234567890");
    customerRequestError.setEmail("customer@example.com");
    customerRequestError.setGender("F");

    AddressRequest addressRequest = new AddressRequest();
    addressRequest.setStreet("Street 1");
    addressRequest.setCity("City 1");
    addressRequest.setMz("2");
    addressRequest.setLt("3");
    addressRequest.setDelegation("Delegation");
    customerRequestError.setAddress(addressRequest);

    when(addressRepository.save(any(AddressEntity.class))).thenThrow(new RuntimeException("Error saving address"));

    // Ejecutar la prueba y verificar el comportamiento esperado
    InternalServerException exception = assertThrows(InternalServerException.class, () -> {
      customerService.save(customerRequestError);
    });

    // Verificar que el mensaje de la excepción es el esperado
    assertEquals("Hubo un error al crear el cliente", exception.getMessage());
  }

  @Test
  @DisplayName("Este Test devuelve una excepción si no encuentra un customer")
  void getById() {
    // Verificar que se lance la excepción esperada
    assertThrows(ResourceNotFoundException.class, () -> {
      customerService.getById(1L);
    });
  }

  @Test
  void getByIdSuccess() {
      CustomerEntity customerEntity = new CustomerEntity();
      customerEntity.setId(1L);
      customerEntity.setName("John Doe");

      // Configura otros campos si es necesario
      CustomerResponse customerResponse = new CustomerResponse();
      customerResponse.setId(1L);
      customerResponse.setName("John Doe");

      // Configurar el comportamiento simulado de customerRepository.findById
      when(customerRepository.findById(3L)).thenReturn(Optional.of(customerEntity));
      when(modelMapper.map(customerEntity, CustomerResponse.class)).thenReturn(customerResponse);

      // Llamar al método que estamos probando
      CustomerResponse response = this.customerService.getById(3L);

      // Verificar el resultado
      assertNotNull(response);
    }

  @Test
  void getByName() {
    List<CustomerEntity> customerEntityList = new ArrayList<CustomerEntity>();
    CustomerEntity customerEntity = new CustomerEntity();
    customerEntity.setId(2L);
    customerEntity.setName("Luz");
    customerEntity.setEmail("luz@gmail.com");
    customerEntityList.add(customerEntity);

    when(this.customerRepository.findByName("Vanessa")).thenReturn(customerEntityList);
    List<CustomerResponse> customers = this.customerService.getByName("Vanessa");

    assertNotNull(customers);
    assertEquals(1, customers.size());
  }

  @Test
  @DisplayName("Returns a list of customers from the specified city")
  void getByCity() {
    List<CustomerEntity> customers = new ArrayList<CustomerEntity>();
    CustomerEntity customerEntity = new CustomerEntity();
    customerEntity.setId(3L);
    customerEntity.setName("Jane");
    customers.add(customerEntity);

    when(customerRepository.findByAddressCity("Oaxaca")).thenReturn(customers);

    List<CustomerResponse> customersResponse = customerService.getByCity("Oaxaca");
  }

  @Test
  @DisplayName("Returns a list of customers from the specified email")
  void getByEmail() {
    List<CustomerEntity> customers = new ArrayList<CustomerEntity>();
    CustomerEntity customerEntity = new CustomerEntity();
    customerEntity.setId(4L);
    customerEntity.setName("David");
    customerEntity.setEmail("david@gmail.com");
    customers.add(customerEntity);

    when(customerRepository.findByEmail("david@example.com")).thenReturn(customers);

    List<CustomerResponse> customersResponse = this.customerService.getByEmail("david@example.com");

    assertEquals(1, customersResponse.size());
  }

  @Test
  void update() {
    CustomerEntity customerEntity = new CustomerEntity();
    customerEntity.setId(4L);
    customerEntity.setName("Jane");
    customerEntity.setPhone("9876543210");
    customerEntity.setEmail("jane@gmail.com");

    CustomerRequest customerRequest = new CustomerRequest();
    customerRequest.setName("Jane");
    customerRequest.setPhone("5432101525");
    customerRequest.setEmail("jane@gmail.com");

    when(customerRepository.findById(4L)).thenReturn(Optional.of(customerEntity));

    customerService.update(4L, customerRequest);
  }

  @Test
  @DisplayName("An exception is thrown when trying to update a non-existent client.")
  void updateNonExistentCustomer() {

    CustomerRequest customerRequest = new CustomerRequest();
    customerRequest.setName("Jane");
    customerRequest.setPhone("5432101525");
    customerRequest.setEmail("jane@gmail.com");

    // Ejecutar la prueba y verificar el comportamiento esperado
    InternalServerException exception = assertThrows(InternalServerException.class, () -> {
      customerService.update(2L, customerRequest);
    });

    //el primer argumento es lo que espero obtener y el segundo es la excepcion que obtuve.
    assertThrows(InternalServerException.class, () -> {
      customerService.update(0L, customerRequest);
    });
  }

  @Test
  @DisplayName("Should delete customer by ID when customer exists")
  void delete() {
    CustomerEntity customerEntity = new CustomerEntity();
    customerEntity.setId(5L);
    customerEntity.setName("Ana");
    customerEntity.setEmail("ana@gmail.com");

    when(customerRepository.findById(5L)).thenReturn(Optional.of(customerEntity));

    customerService.delete(5L);

    verify(customerRepository, times(1)).delete(any());
  }

  @Test
  @DisplayName("Logs info and throws ResourceNotFoundException when customer is not found")
  void customerNotFound() {

    //el primer argumento es lo que espero obtener y el segundo es la excepcion que obtuve.
    assertThrows(InternalServerException.class, () -> {
      customerService.delete(4L);
    });
  }
}