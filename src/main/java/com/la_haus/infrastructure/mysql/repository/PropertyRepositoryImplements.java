package com.la_haus.infrastructure.mysql.repository;


import com.la_haus.domain.entity.Apartment;
import com.la_haus.domain.entity.House;
import com.la_haus.domain.entity.Property;
import com.la_haus.domain.repository.PropertyRepository;
import com.la_haus.infrastructure.mysql.mapper.Properties;
import net.bytebuddy.implementation.bytecode.Throw;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.UserTransaction;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.IOException;
import java.util.Set;

@Service
public class PropertyRepositoryImplements implements PropertyRepository {
    @Override
    public Property saveProperty(Property newProperty, JsonNode jsonMap, ObjectMapper mapper, Validator validator) throws IOException {
        if(newProperty.getPropertyType().equalsIgnoreCase("HOUSE")){
            House newHouse = mapper.readValue(jsonMap, House.class);
            newHouse.setStatus(newProperty.getStatus());
            Set<ConstraintViolation<House>> violationsHouse = validator.validate(newHouse);
            if (!violationsHouse.isEmpty() ){
                throw new Error("error " + " " + violationsHouse + " House was'nt create, state invalid");
            }
            return newHouse;
        }else if(newProperty.getPropertyType().equalsIgnoreCase("APARTMENT")){
            Apartment newApartment = mapper.readValue(jsonMap, Apartment.class);
            Set<ConstraintViolation<Apartment>> violationsApartment = validator.validate(newApartment);
            newApartment.setStatus(newProperty.getStatus());
            if (!violationsApartment.isEmpty() ){
                throw new Error( "error" + "  " + violationsApartment + "Apartment was'nt create, state invalid");
            }
            return newApartment;

        }
        throw new Error( "The Type of Property is incorrect, Try with HOUSE or APARTMENT");
    }

}
