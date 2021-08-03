package com.la_haus.application;

import com.la_haus.domain.entity.Location;
import com.la_haus.domain.entity.Pricing;
import com.la_haus.domain.entity.Property;
import com.la_haus.infrastructure.mysql.repository.PropertyRepository;
import com.la_haus.infrastructure.mysql.repository.PropertyRepositoryImplements;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
public class PropertyController {
    @Autowired
    private PropertyRepository propertyRepository;
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
            PropertyRepositoryImplements validatePropertyType = new PropertyRepositoryImplements();
            try {
                //Validate and return the property
                System.out.println("paso");
                Property result = validatePropertyType.saveProperty(newProperty,validator);
                propertyRepository.save(result);
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
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
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
                    Set<ConstraintViolation<Property>> violations = validator.validate(properti);
                    if (!violations.isEmpty()){
                        return "error " + violations;
                    }
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
    @GetMapping("/property")
    @ResponseBody
     Map<String, Object> getProperties(@RequestParam(name = "status", defaultValue = "ALL", required = false) String status, @RequestParam(name = "bbox", required = false) List<Double> bbox,
                                             @RequestParam(name = "page", defaultValue = "0", required = false) int page,
                                             @RequestParam(name = "pageSize", defaultValue = "10", required = false) @Min(value = 10) @Max(value = 20) int pageSize) {


        Pageable pagination = PageRequest.of(page, pageSize, Sort.by("updatedAt").descending().and(Sort.by("createdAt").descending()));

        Page<Property> data = propertyRepository.findAll(pagination);;

        Map<String, Object> response = new HashMap<>();
        response.put("page", pagination.getPageNumber());
        response.put("pageSize", pagination.getPageSize());
        response.put("total", data.getTotalElements());
        response.put("totalPages", data.getTotalPages());
        response.put("data", data.getContent());

        return  response;
    }


}
