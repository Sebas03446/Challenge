package com.la_haus.application;

import com.la_haus.domain.entity.House;
import com.la_haus.domain.entity.Location;
import com.la_haus.domain.entity.Property;
import com.la_haus.domain.repository.PropertyRepository;
import com.la_haus.infrastructure.mysql.repository.PropertyRepositoryImplements;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.validation.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

@Controller
public class PropertyController {

    @GetMapping("/property")
    @ResponseBody
    public String hello(){
        return "Hello -3";
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
            String result = validatePropertyType.saveProperty(newProperty,jsonMap,mapper,validator);
            return result + " ";
        }catch (IOException ex){
            System.out.println(ex.fillInStackTrace());
        }
        return "Build";
    }
}
