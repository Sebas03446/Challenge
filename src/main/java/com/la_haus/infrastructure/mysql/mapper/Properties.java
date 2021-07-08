package com.la_haus.infrastructure.mysql.mapper;


import com.la_haus.domain.entity.Property;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import com.la_haus.infrastructure.mysql.mapper.LocationEntity;
import com.la_haus.infrastructure.mysql.mapper.Pricing;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Properties {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    @Setter
    private int id;
    @NotNull
    @NotBlank
    @Getter
    @Setter
    private String title;
    @Getter
    @Setter
    private String description;
    @Getter
    @Setter
    private LocationEntity location;
    @Getter
    @Setter
    private Pricing pricing;
    @NotNull
    @NotBlank
    @Getter
    @Setter
    private String propertyType;
    @NotNull
    @PositiveOrZero
    @Getter
    @Setter
    //@Max(value = 10,groups = HouseI.class)
    private int bedrooms;
    @NotNull
    @PositiveOrZero
    @Getter
    @Setter
    private int bathrooms;
    @PositiveOrZero
    @Getter
    @Setter
    private int parkingSpots;
    @NotNull
    @Getter
    @Setter
    private int area;
    @ToString.Exclude
    @ElementCollection
    @Setter
    @Getter
    private Set<String> photos;
    @ToString.Exclude
    @Getter
    @Setter
    private String createdAt;
    @ToString.Exclude
    @Getter
    @Setter
    private String updatedAt;
    @Getter
    @Setter
    private String status;
}
