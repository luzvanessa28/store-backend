package com.app.tienda.repository;

import com.app.tienda.entity.PerroEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PerroRepository extends JpaRepository<PerroEntity, Long> {
}
