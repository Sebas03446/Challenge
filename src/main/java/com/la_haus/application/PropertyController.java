package com.la_haus.application;

import com.la_haus.domain.entity.House;
import com.la_haus.domain.entity.Location;
import com.la_haus.domain.entity.Property;
import com.la_haus.infrastructure.mysql.mapper.LocationEntity;
import com.la_haus.infrastructure.mysql.mapper.Pricing;
import com.la_haus.infrastructure.mysql.mapper.Properties;
import com.la_haus.infrastructure.mysql.repository.PropertyRepository;
import com.la_haus.infrastructure.mysql.repository.PropertyRepositoryImplements;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    public Iterable<Properties> hello(){
        return propertyRepository.findAll();
    }

    /*@PutMapping("/property/{id}")
    @ResponseBody
    public Properties property(@RequestBody Properties porperty, @PathVariable int id){
        return propertyRepository.findById(id)
                .map(properti -> {
            properti.set

        });
    }*/

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
                Property result = validatePropertyType.saveProperty(newProperty,jsonMap,mapper,validator);
                LocationEntity loc = new LocationEntity();
                loc.setLatitude(result.getLocation().getLatitude());
                loc.setLongitude(result.getLocation().getLongitude());
                Pricing price = new Pricing();
                price.setSalePrice(result.getPricing().getSalePrice());
                price.setAdministrativeFee(result.getPricing().getAdministrativeFee());
                Properties properties = new Properties();
                properties.setTitle(result.getTitle());
                properties.setDescription(result.getDescription());
                properties.setLocation(loc);
                properties.setPricing(price);
                properties.setPropertyType(result.getPropertyType());
                properties.setBedrooms(result.getBedrooms());
                properties.setBathrooms(result.getBathrooms());
                properties.setParkingSpots(result.getParkingSpots());
                properties.setArea(result.getArea());
                Set<String> photos = new HashSet();
                for (String photo : result.getPhotos()) {
                    // Add each element into the set
                    photos.add(photo);
                }
                properties.setPhotos(photos);
                properties.setCreatedAt(result.getCreatedAt());
                properties.setUpdatedAt(result.getUpdatedAt());
                properties.setStatus(result.getStatus());
                propertyRepository.save(properties);
            }catch (Error err){
                return err.getMessage();
            }
            return "Saved!";
        }catch (IOException ex){
            System.out.println(ex.fillInStackTrace());
        }
        return "Build";
    }
}
