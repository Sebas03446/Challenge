package com.la_haus.application;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.la_haus.domain.entity.Location;
import com.la_haus.domain.entity.Pricing;
import com.la_haus.domain.entity.Property;
import com.la_haus.infrastructure.mysql.repository.PropertyRepository;
import com.la_haus.infrastructure.mysql.repository.PropertyRepositoryImplements;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

@Controller
public class PropertyController {
    @Autowired
    private PropertyRepository propertyRepository;

    @GetMapping("/property")
    @ResponseBody
    public Iterable<Property> hello(){
        return propertyRepository.findAll();
    }
    @PostMapping("/property")
    @ResponseBody
    String createProperty(HttpServletRequest request){
        ObjectMapper mapper = new ObjectMapper();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        try {
            // Recieve request.InputStream, mapping to Json, create Property
            JsonNode jsonMap = mapper.readTree(request.getInputStream());

            Property newProperty = mapper.readValue(jsonMap, Property.class);
            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
            newProperty.setCreatedAt(currentDateTime.format(formatter));
            newProperty.setUpdatedAt(currentDateTime.format(formatter));
            Location newPropertyLocation = newProperty.getLocation();
            System.out.println(newProperty.getParkingSpots());
            if(19.296134<= newPropertyLocation.getLatitude() && newPropertyLocation.getLatitude()<=19.661237 && -99.296741<= newPropertyLocation.getLongitude() && newPropertyLocation.getLongitude()<=-98.916339) {
                newProperty.setStatus("ACTIVE");
            }else{
                newProperty.setStatus("INACTIVE");
            }
            //validate
            Set<ConstraintViolation<Property>> violations = validator.validate(newProperty);
            if (!violations.isEmpty()){
                return "error " + violations;
            }

            //get Property type for create and evaluate the select property
            PropertyRepositoryImplements validatePropertyType = new PropertyRepositoryImplements();
            try {
                System.out.println("paso");
                Property result = validatePropertyType.saveProperty(newProperty,jsonMap,mapper,validator);
                Property property=validatePropertyType.returnProperty(result);
                propertyRepository.save(property);
            }catch (Error err){
                return err.getMessage();
            }
            return "Saved!";
        }catch (IOException ex){
            System.out.println(ex.fillInStackTrace());
        }
        return "Build";
    }
    @PutMapping("/property/{id}")
    @ResponseBody
     String property(@RequestBody Property property, @PathVariable(value = "id") int id){
        System.out.println(id);
        return propertyRepository.findById(id)
                .map(properti -> {
                    Location loc = new Location();
                    loc.setLatitude(property.getLocation().getLatitude());
                    loc.setLongitude(property.getLocation().getLongitude());
                    Pricing price = new Pricing();
                    price.setSalePrice(property.getPricing().getSalePrice());
                    price.setAdministrativeFee(property.getPricing().getAdministrativeFee());
                    properti.setTitle(property.getTitle());
                    properti.setDescription(property.getDescription());
                    properti.setLocation(loc);
                    properti.setPricing(price);
                    properti.setPropertyType(property.getPropertyType());
                    properti.setBedrooms(property.getBedrooms());
                    properti.setBathrooms(property.getBathrooms());
                    properti.setParkingSpots(property.getParkingSpots());
                    properti.setArea(property.getArea());
                    Set<String> photos = new HashSet();
                    for (String photo : property.getPhotos()) {
                        // Add each element into the set
                        photos.add(photo);
                    }
                    LocalDateTime currentDateTime = LocalDateTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
                    properti.setPhotos(photos);
                    properti.setUpdatedAt(currentDateTime.format(formatter));
                    properti.setStatus(property.getStatus());
                    propertyRepository.save(properti);
                    return "Property was update!";
                }).orElseGet(()->{
                    property.setId(id);
                    propertyRepository.save(property);
                    return "The new property has been created";

                });
    }
    @DeleteMapping("/property/{id}")
    @ResponseBody
    String deleteProperty(@PathVariable int id) {
        try{
            propertyRepository.deleteById(id);
            return "The Property was delete!";
        }catch (Error error){
            return "The Property is'nt create! ";

        }

    }


}
