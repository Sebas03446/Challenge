package com.Properties.domain.repository;

import com.Properties.domain.entity.Property;

import java.io.IOException;

public interface PropertyRepository {
     Property saveProperty(Property newProperty) throws IOException;

}
