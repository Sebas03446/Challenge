package com.Properties.application;

import com.Properties.domain.entity.Location;
import com.Properties.domain.entity.Pricing;
import com.Properties.domain.entity.Property;
import com.Properties.infrastructure.mysql.repository.PropertyRepository;
import com.Properties.infrastructure.mysql.repository.PropertyRepositoryImplements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/")
    @ResponseBody
    String welcome(){
        return "Hola";
    }
    @PostMapping("/property")
    @ResponseBody
    String createProperty(@RequestBody Property newProperty){
        try {
            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
            newProperty.setCreatedAt(currentDateTime.format(formatter));
            newProperty.setUpdatedAt(currentDateTime.format(formatter));
            PropertyRepositoryImplements validatePropertyType = new PropertyRepositoryImplements();
            try {
                //Validate and return the property
                System.out.println("paso");
                Property result = validatePropertyType.saveProperty(newProperty);
                propertyRepository.save(result);
            }catch (Error err){
                return err.getMessage();
            }
            return "Saved!";
        }catch (IOException ex){
            System.out.println(ex.fillInStackTrace());
        }
        return "error";
    }
    @PutMapping("/property/{id}")
    @ResponseBody
     String property(@RequestBody Property property, @PathVariable(value = "id") int id){
        PropertyRepositoryImplements validatePropertyType = new PropertyRepositoryImplements();
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
                    try {
                        //Validate and return the property
                        System.out.println("paso");
                        Property result = validatePropertyType.saveProperty(properti);
                        propertyRepository.save(result);
                    }catch (IOException error){
                        return error.getMessage();
                    }catch (Error err){
                        return err.getMessage();
                    }
                    return "Property was update!";
                }).orElseGet(()->{
                    return "The property does'nt exist";

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
     Map<String, Object> getProperties(@RequestParam(name = "status", defaultValue ="ALL" ,required = false) String status, @RequestParam(name = "bbox", required = false) List<Double> bbox,
                                             @RequestParam(name = "page", defaultValue = "0", required = false) int page,
                                       @RequestParam(name = "pageSize", defaultValue = "10", required = false) @Min(value = 10) @Max(value = 20) int pageSize) {
        if(bbox.size()!=4){
            bbox=null;
        }
        //Coparator with format bbox={minLongitude},{minLatitude},{maxLongitude},{maxLatitude} for example bbox=-99.296741,19.296134,-98.916339,19.661237
        /*Comparator<Property> comparator= (property, property2) -> {
            int compareQuantity=0;
            Location newPropertyLocation=property.getLocation();
            if(bbox.get(1)<= newPropertyLocation.getLatitude() && newPropertyLocation.getLatitude()<=bbox.get(3) && bbox.get(0)<= newPropertyLocation.getLongitude() && newPropertyLocation.getLongitude()<=bbox.get(2)) {
                compareQuantity=1;
            }
            int compareTwo=0;
            Location newPropertyLocation2=property2.getLocation();
            if(bbox.get(1)<= newPropertyLocation2.getLatitude() && newPropertyLocation2.getLatitude()<=bbox.get(3) && bbox.get(0)<= newPropertyLocation2.getLongitude() && newPropertyLocation2.getLongitude()<=bbox.get(2)) {
                compareTwo=1;
            }
            //Decending order, for change the order return compareQuantity-compareTwo
            return compareTwo - compareQuantity ;
            };*/
        Comparator<Property> comparator2= (property, property2) -> {
            //Decending order for upadte
            return property2.getUpdatedAt().compareTo(property.getUpdatedAt()) ;
        };
        Comparator<Property> comparator3= (property, property2) -> {
            //Decending order for Create
            return property2.getCreatedAt().compareTo(property.getCreatedAt()) ;
        };
        List<Property> listProperty= Collections.emptyList();
        if(status.equals("ALL") && bbox!=null ){
            listProperty = propertyRepository.findAllPropertiesBBOX(bbox.get(0),bbox.get(1),bbox.get(2), bbox.get(3));
        }else if(status.equals("ALL")){
            listProperty=propertyRepository.findAll();
        }else if(bbox!=null){
            listProperty=propertyRepository.findAllPropertiesByStatusAndBBOX(bbox.get(0),bbox.get(1),bbox.get(2), bbox.get(3),status);
        }else {
            listProperty = propertyRepository.findAllByStatus(status);
        }

        //Call comparator and sort the list
        Collections.sort(listProperty,comparator3);
        Collections.sort(listProperty,comparator2);
        List<Property> listPropertyResult= Collections.emptyList();



        Pageable pagination = PageRequest.of(page, pageSize);
        //Page<Property> data = propertyRepository.findAllByStatus(status,pagination);
        PageImpl<Property> data=new PageImpl<Property>(listProperty);
        //Page<Property> data = propertyRepository.findAllByPricing(pagination);
        LinkedHashMap<String, Object> response = new LinkedHashMap<>();
        response.put("page", pagination.getPageNumber());
        response.put("pageSize", pagination.getPageSize());
        response.put("total", data.getTotalElements());
        response.put("totalPages", data.getTotalPages());
        response.put("data", data.getContent());

        return  response;
    }


}
