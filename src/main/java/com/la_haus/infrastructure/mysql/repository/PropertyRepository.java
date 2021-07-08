package com.la_haus.infrastructure.mysql.repository;

import com.la_haus.infrastructure.mysql.mapper.Properties;
import org.springframework.data.repository.CrudRepository;

public interface PropertyRepository extends CrudRepository<Properties, Integer> {
}
