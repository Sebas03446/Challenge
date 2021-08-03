package com.la_haus.domain.repository;

import com.la_haus.domain.entity.Property;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import javax.validation.Validator;
import java.io.IOException;

public interface PropertyRepository {
     Property saveProperty(Property newProperty, Validator validator) throws IOException;

}
