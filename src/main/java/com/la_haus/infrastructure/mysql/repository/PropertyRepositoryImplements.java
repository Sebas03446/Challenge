package com.la_haus.infrastructure.mysql.repository;


import com.la_haus.domain.entity.Apartment;
import com.la_haus.domain.entity.House;
import com.la_haus.domain.entity.Property;
import com.la_haus.domain.repository.PropertyRepository;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.IOException;
import java.util.Set;

@Service
public class PropertyRepositoryImplements implements PropertyRepository {
    @Override
    public String saveProperty(Property newProperty, JsonNode jsonMap, ObjectMapper mapper, Validator validator) throws IOException {
        if(newProperty.getPropertyType().equalsIgnoreCase("HOUSE")){
            House newHouse = mapper.readValue(jsonMap, House.class);
            Set<ConstraintViolation<House>> violationsHouse = validator.validate(newHouse);
            if (!violationsHouse.isEmpty() ){
                return "error " + " " + violationsHouse + " House was'nt create, state invalid" ;
            }
            String propertyAsString = mapper.writeValueAsString(newHouse);
            JsonNode jsonNode = mapper.readTree(propertyAsString);
            return "House is created!";
        }else if(newProperty.getPropertyType().equalsIgnoreCase("APARTMENT")){
            Apartment newApartment = mapper.readValue(jsonMap, Apartment.class);
            Set<ConstraintViolation<Apartment>> violationsApartment = validator.validate(newApartment);
            if (!violationsApartment.isEmpty() ){
                return "error" + "  " + violationsApartment + "Apartment was'nt create, state invalid";
            }
            String propertyAsString = mapper.writeValueAsString(newApartment);
            JsonNode jsonNode = mapper.readTree(propertyAsString);
            return "Apartment is created!";

        }
        return "The Type of Property is incorrect, Try with HOUSE or APARTMENT";
    }
}
