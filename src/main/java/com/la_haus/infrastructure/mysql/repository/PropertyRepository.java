package com.la_haus.infrastructure.mysql.repository;


import com.la_haus.domain.entity.Property;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PropertyRepository extends JpaRepository<Property, Integer> {
    List<Property> findAllByStatus(String status);

}
