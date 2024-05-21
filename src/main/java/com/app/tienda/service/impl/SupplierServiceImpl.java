package com.app.tienda.service.impl;

import com.app.tienda.constant.Message;
import com.app.tienda.entity.AddressEntity;
import com.app.tienda.entity.SupplierEntity;
import com.app.tienda.exception.InternalServerException;
import com.app.tienda.model.request.SupplierRequest;
import com.app.tienda.model.response.SupplierResponse;
import com.app.tienda.repository.AddressRepository;
import com.app.tienda.repository.SupplierRepository;
import com.app.tienda.service.ISupplierService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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
}
