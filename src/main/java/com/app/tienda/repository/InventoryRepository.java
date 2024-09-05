package com.app.tienda.repository;

import com.app.tienda.constant.Querys;
import com.app.tienda.entity.InventoryEntity;
import com.app.tienda.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<InventoryEntity, Long> {

  Optional<InventoryEntity> findByProductId(Long id);
}
