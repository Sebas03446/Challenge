package com.la_haus.domain.entity;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.OverridesAttribute;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
@Data
@ToString
public class Property {
   /* public interface HouseI{

    };*/
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
    @NotNull
    @Getter
    @Setter
    @Valid
    private Location location;
    @NotNull
    @Getter
    @Setter
    @Valid
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
    @Getter
    @Setter
    private String[] photos;
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
