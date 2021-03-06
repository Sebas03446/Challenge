package com.Properties.application;

import com.Properties.domain.entity.AuthRequest;
import com.Properties.domain.entity.Property;
import com.Properties.domain.entity.User;
import com.Properties.infrastructure.mysql.repository.PropertyRepository;
import com.Properties.infrastructure.mysql.repository.UserRepository;
import com.Properties.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PropertyRepository propertyRepository;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/v1/users/login")
    @ResponseBody
    public String generateToken(@RequestBody AuthRequest authRequest) throws Exception {
        try{
            System.out.println(authRequest.getEmail());
           authenticationManager.authenticate(
                   new UsernamePasswordAuthenticationToken(authRequest.getEmail(),authRequest.getPassword())
           );
        }catch (Exception ex){
                throw  new Exception("invalid username" + ex);
        }
        return jwtUtil.generateToken(authRequest.getEmail());

    }
    @PostMapping("v1/users/")
    @ResponseBody
    User createUser(@RequestBody User newUser){
        newUser.setPassword(newUser.getEmail());
        if(userRepository.findByEmail(newUser.getEmail()).isEmpty()){
            return userRepository.save(newUser);
        }else{
            throw (new Error("The email already exist!"));
        }

    }
    @PostMapping("v1/users/{me}/favorites")
    @ResponseBody
    String addFavorite(@RequestBody Map<String,Integer> propertyId, @PathVariable String me){
        Boolean existProperty = propertyRepository.findById(propertyId.get("propertyId")).isEmpty();
        if(!existProperty){
             return userRepository.findByEmail(me).map(user -> {
                    Set<Integer> fav = user.getFavorites();
                    fav.add(propertyId.get("propertyId"));
                    user.setFavorites(fav);
                    userRepository.save(user);
                    return "Succesfull";
                }).orElseGet(()->{
                    User userRespond = new User();
                    userRespond.setEmail("Verify the user or password");
                    userRespond.setPassword("error");
                    return "error with the user";
                    });
        }
        return "Property does'nt exist!";
    }
    @GetMapping("v1/users/{me}/favorites")
    @ResponseBody
    Map<String,Object> getFavorites(@PathVariable String me){
        User user = userRepository.findByEmail(me).get();
        Pageable pagination = PageRequest.of(0, 1);
        Set<Integer> propertyFavorites = user.getFavorites();
        List<Property> listProperty=propertyRepository.findAllById(propertyFavorites);
        PageImpl<Property> data=new PageImpl<Property>(listProperty);
        LinkedHashMap<String, Object> response = new LinkedHashMap<>();
        response.put("page", pagination.getPageNumber());
        response.put("pageSize", pagination.getPageSize());
        response.put("total", data.getTotalElements());
        response.put("totalPages", data.getTotalPages());
        response.put("data", data.getContent());

        return  response;
    }
}
