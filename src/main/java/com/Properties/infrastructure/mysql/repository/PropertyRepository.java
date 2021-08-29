package com.Properties.infrastructure.mysql.repository;


import com.Properties.domain.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public interface PropertyRepository extends JpaRepository<Property, Integer> {
    List<Property> findAllByStatus(String status);
    @Query("FROM Property p  WHERE p.location.latitude>:minLatitude and p.location.latitude<:maxLatitude and p.location.longitude>:minLongitude and p.location.longitude<:maxLongitude")
    List <Property> findAllPropertiesBBOX(@Param("minLongitude")double minLongitude,@Param("minLatitude")double minLatitude,@Param("maxLongitude")double maxLongitude,@Param("maxLatitude")double maxLatitude );
    @Query("FROM Property p  WHERE p.location.latitude>:minLatitude and p.location.latitude<:maxLatitude and p.location.longitude>:minLongitude and p.location.longitude<:maxLongitude and p.status =:status")
    List <Property> findAllPropertiesByStatusAndBBOX(@Param("minLongitude")double minLongitude,@Param("minLatitude")double minLatitude,@Param("maxLongitude")double maxLongitude,@Param("maxLatitude")double maxLatitude,@Param("status")String status );

}
