package com.Properties.infrastructure.mysql.repository;


import com.Properties.domain.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PropertyRepository extends JpaRepository<Property, Integer> {
    List<Property> findAllByStatus(String status);

}
