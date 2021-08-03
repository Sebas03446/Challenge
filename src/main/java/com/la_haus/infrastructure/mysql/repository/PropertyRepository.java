package com.la_haus.infrastructure.mysql.repository;

import com.la_haus.domain.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface PropertyRepository extends JpaRepository<Property, Integer> {
}
