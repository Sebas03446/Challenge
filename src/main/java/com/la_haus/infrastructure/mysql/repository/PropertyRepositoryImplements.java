package com.la_haus.infrastructure.mysql.repository;


import com.la_haus.domain.entity.*;
import com.la_haus.domain.repository.PropertyRepository;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.IOException;
import java.util.HashSet;
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
    @Override
    public Property returnProperty(Property result){
        Location loc = new Location();
        loc.setLatitude(result.getLocation().getLatitude());
        loc.setLongitude(result.getLocation().getLongitude());
        Pricing price = new Pricing();
        price.setSalePrice(result.getPricing().getSalePrice());
        price.setAdministrativeFee(result.getPricing().getAdministrativeFee());
        Property properti = new Property();
        properti.setTitle(result.getTitle());
        properti.setDescription(result.getDescription());
        properti.setLocation(loc);
        properti.setPricing(price);
        properti.setPropertyType(result.getPropertyType());
        properti.setBedrooms(result.getBedrooms());
        properti.setBathrooms(result.getBathrooms());
        properti.setParkingSpots(result.getParkingSpots());
        properti.setArea(result.getArea());
        Set<String> photos = new HashSet();
        for (String photo : result.getPhotos()) {
            // Add each element into the set
            photos.add(photo);
        }
        properti.setPhotos(photos);
        properti.setCreatedAt(result.getCreatedAt());
        properti.setUpdatedAt(result.getUpdatedAt());
        properti.setStatus(result.getStatus());
        return properti;
    }

}
