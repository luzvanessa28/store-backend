package com.app.tienda.service.impl;

import com.app.tienda.constant.Message;
import com.app.tienda.entity.AddressEntity;
import com.app.tienda.entity.CustomerEntity;
import com.app.tienda.exception.InternalServerException;
import com.app.tienda.exception.ResourceNotFoundException;
import com.app.tienda.model.request.CustomerRequest;
import com.app.tienda.model.response.AlumnoResponse;
import com.app.tienda.model.response.CustomerResponse;
import com.app.tienda.repository.AddressRepository;
import com.app.tienda.repository.CustomerRepository;
import com.app.tienda.service.ICustumerService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CustomerServiceImpl implements ICustumerService {
  @Autowired
  private CustomerRepository customerRepository;
  @Autowired
  private AddressRepository addressRepository;
  @Autowired
  private ModelMapper modelMapper;

  @Override
  public List<CustomerResponse> findAll() {
    log.info("CustomerServiceImpl - findAll");

    List<CustomerEntity> customers = customerRepository.findAll();

    return customers.stream()
      .map(customerEntity -> modelMapper.map(customerEntity, CustomerResponse.class))
      .collect(Collectors.toList());
  }

  @Transactional
  @Override
  public CustomerResponse save(CustomerRequest customerRequest) {
    log.info("CustomerServiceImpl - save {}", customerRequest);

    try {
      CustomerEntity customerEntity = modelMapper.map(customerRequest, CustomerEntity.class);

      // Obtener la dirección del customerEntity
      AddressEntity addressEntity = customerEntity.getAddress();

      // Guardar la dirección primero para obtener su ID generado
      addressRepository.save(addressEntity);

      // Asignar la dirección al cliente y guardarlo
      customerEntity.setAddress(addressEntity);
      CustomerEntity saved = customerRepository.save(customerEntity);

      return modelMapper.map(saved, CustomerResponse.class);

    } catch (Exception e) {
      log.error("Se produjo un error al guardar al cliente", e.getMessage());
      throw new InternalServerException(Message.SAVE_ERROR + " al cliente", e);
    }
  }
  @Override
  public CustomerResponse getById(Long id) {
    log.info("getById impl {}", id);

    Optional<CustomerEntity> customerOptional = customerRepository.findById(id);
    log.info("customerOptional {}", customerOptional);

    return customerOptional
      .map(customerEntity -> modelMapper.map(customerEntity, CustomerResponse.class))
      .orElseThrow(() -> new ResourceNotFoundException(Message.ID_NOT_FOUND));
  }

  @Override
  public List<CustomerResponse> getByName(String name) {
    log.info("getByName impl {}", name);

    List<CustomerEntity> customerName = customerRepository.findByName(name);
    log.info("getByName impl customerName {}", customerName);

    return customerName.stream().
      map(customerEntity -> modelMapper.map(customerEntity, CustomerResponse.class))
      .collect(Collectors.toList());
  }

  @Override
  public List<CustomerResponse> getByCity(String city) {
    log.info("CustomerServiceImpl - find customer by city {}", city);

    List<CustomerEntity> customerList = this.customerRepository.findByAddressCity(city);

    return customerList.stream()
      .map(customerEntity -> modelMapper.map(customerEntity, CustomerResponse.class))
      .collect(Collectors.toList());
  }


}
