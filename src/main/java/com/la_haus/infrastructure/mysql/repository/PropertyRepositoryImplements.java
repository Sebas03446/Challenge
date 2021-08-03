package com.la_haus.infrastructure.mysql.repository;


import com.la_haus.domain.entity.*;
import com.la_haus.domain.repository.PropertyRepository;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

@Service
public class PropertyRepositoryImplements implements PropertyRepository {
    @Override
    public Property saveProperty(Property newProperty, Validator validator) throws IOException {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        newProperty.setCreatedAt(currentDateTime.format(formatter));
        newProperty.setUpdatedAt(currentDateTime.format(formatter));
        Location newPropertyLocation = newProperty.getLocation();
        if(19.296134<= newPropertyLocation.getLatitude() && newPropertyLocation.getLatitude()<=19.661237 && -99.296741<= newPropertyLocation.getLongitude() && newPropertyLocation.getLongitude()<=-98.916339) {
            newProperty.setStatus("ACTIVE");
        }else{
            newProperty.setStatus("INACTIVE");
        }
        //validate
        Set<ConstraintViolation<Property>> violations = validator.validate(newProperty);
        if (!violations.isEmpty()){
            throw new Error( "error " + violations + "The property was'nt create, state invalid.");
        }
        if(newProperty.getPropertyType().equalsIgnoreCase("HOUSE")){
            Set<ConstraintViolation<Property>> violationsHouse = validator.validate(newProperty, Property.House.class);
            if (!violationsHouse.isEmpty() ){
                throw new Error("error " + " " + violationsHouse + " House was'nt create, state invalid");
            }
            return newProperty;
        }else if(newProperty.getPropertyType().equalsIgnoreCase("APARTMENT")){
            Set<ConstraintViolation<Property>> violationsApartment = validator.validate(newProperty, Property.Apartment.class);
            if (!violationsApartment.isEmpty() ){
                throw new Error( "error" + "  " + violationsApartment + "Apartment was'nt create, state invalid");
            }
            return newProperty;

        }
        throw new Error( "The Type of Property is incorrect, Try with HOUSE or APARTMENT");
    }


}
