package com.la_haus.domain.repository;

import com.la_haus.domain.entity.Property;
import com.la_haus.infrastructure.mysql.mapper.Properties;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import javax.validation.Validator;
import java.io.IOException;

public interface PropertyRepository {
     Property saveProperty(Property newProperty, JsonNode jsonMap, ObjectMapper mapper, Validator validator) throws IOException;
}
