package com.app.tienda.service.impl;

import com.app.tienda.constant.Message;
import com.app.tienda.entity.AddressEntity;
import com.app.tienda.entity.SupplierEntity;
import com.app.tienda.exception.InternalServerException;
import com.app.tienda.exception.ResourceNotFoundException;
import com.app.tienda.model.request.SupplierRequest;
import com.app.tienda.model.response.SupplierResponse;
import com.app.tienda.repository.AddressRepository;
import com.app.tienda.repository.SupplierRepository;
import com.app.tienda.service.ISupplierService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SupplierServiceImpl implements ISupplierService {

  @Autowired
  private SupplierRepository supplierRepository;
  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private AddressRepository addressRepository;

  @Override
  public List<SupplierResponse> findAll() {
    log.info("SupplierServiceImpl - findAll");

    List<SupplierEntity> suppliers = supplierRepository.findAll();

    return suppliers.stream()
      .map(supplierEntity -> modelMapper.map(supplierEntity, SupplierResponse.class))
      .collect(Collectors.toList());
  }

  @Override
  public SupplierResponse save(SupplierRequest supplierRequest) {
    log.info("CustomerServiceImpl - save {}", supplierRequest);

    try {
      SupplierEntity supplierEntity = modelMapper.map(supplierRequest, SupplierEntity.class);

      AddressEntity addressEntity = supplierEntity.getAddress();

      addressRepository.save(addressEntity);

      supplierEntity.setAddress(addressEntity);
      SupplierEntity saved = supplierRepository.save(supplierEntity);

      log.info("Se guardo correctamente");
      return modelMapper.map(saved, SupplierResponse.class);
    } catch (Exception e) {
      log.error("Se produjo un error al guardar al proveedor", e.getMessage());
      throw new InternalServerException(Message.SAVE_ERROR + "al proveedor", e);
    }
  }

  @Override
  public SupplierResponse getById(Long id) {
    log.info("SupplierServiceImpl - find supplier by id {}", id);

    Optional<SupplierEntity> supplierOptional = supplierRepository.findById(id);

    return supplierOptional
      .map(supplierEntity -> modelMapper.map(supplierEntity, SupplierResponse.class))
      .orElseThrow(() -> new ResourceNotFoundException(Message.ID_NOT_FOUND + ": " + id));
  }

  @Override
  public List<SupplierResponse> getByName(String name) {
    log.info("getByName impl {}", name);

    List<SupplierEntity> suppliersList = supplierRepository.findByName(name);
    log.info("getByName impl supplierName {}", suppliersList);

    return suppliersList.stream().
      map(supplierEntity -> modelMapper.map(supplierEntity, SupplierResponse.class))
      .collect(Collectors.toList());
  }

  @Override
  public List<SupplierResponse> getByCity(String city) {
      log.info("SupplierServiceImpl - find supplier by city {}", city);

      List<SupplierEntity> supplierList = this.supplierRepository.findByAddressCity(city);

      return supplierList.stream()
        .map(supplierEntity -> modelMapper.map(supplierEntity, SupplierResponse.class))
        .collect(Collectors.toList());
  }

  @Override
  public List<SupplierResponse> getByEmail(String email) {
    log.info("SupplierServiceImpl - find supplier by email {}", email);

    List<SupplierEntity> supplierEmail = this.supplierRepository.findByEmail(email);
    log.info("supplierEmail {}", supplierEmail);

    return supplierEmail.stream()
      .map(SupplierEntity -> modelMapper.map(SupplierEntity, SupplierResponse.class))
      .collect(Collectors.toList());
  }

  @Override
  public SupplierResponse update(Long id, SupplierRequest supplierRequest) {
    log.info("supplierServiceImpl - update: {} {}", id, supplierRequest);

    try {
      Optional<SupplierEntity> supplierOptional = supplierRepository.findById(id);

      if (supplierOptional.isPresent()) {
        SupplierEntity supplierEntity = supplierOptional.get();
        modelMapper.map(supplierRequest, supplierEntity);

        SupplierEntity supplierUpdate = supplierRepository.save(supplierEntity);
        return modelMapper.map(supplierUpdate, SupplierResponse.class);
      } else {
        throw new ResourceNotFoundException(Message.ID_NOT_FOUND);
      }
    } catch (DataAccessException e) {
      log.error("Hubo un error al actualizar el proveedor: {}", e.getMessage());
      throw new InternalServerException(Message.UPDATE_ERROR + "el proveedor con ID: " + id, e);
    }
  }

  @Override
  public void delete(Long id) {
    log.info("Deleting supplier by id: {}", id);

    try {
      Optional<SupplierEntity> supplierOptional = supplierRepository.findById(id);

      if (supplierOptional.isPresent()) {
        supplierRepository.deleteById(id);

      } else {
        log.info("The supplier was not found");

        throw new ResourceNotFoundException(Message.ID_NOT_FOUND + ": " + id);
      }
    } catch (DataAccessException e) {
      log.info("Se produjo un error al eliminar al proveedor", e.getMessage());
      throw new InternalServerException(Message.DELETE_ERROR + " al proveedor", e);
    }
  }
}
