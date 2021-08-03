package com.la_haus.domain.entity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Set;


@Entity
public class Property {
    public interface House{

    };
    public interface Apartment{

    };
   //@Max(value = 10,groups = HouseI.class)
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
  private Location location;
  @Getter
  @Setter
  private Pricing pricing;
  @NotNull
  @NotBlank
  @Getter
  @Setter
  private String propertyType;
  @NotNull
  @Positive
  @Getter
  @Setter
  @Max(value = 14,groups = House.class)
  @Min(value=1,groups=House.class)
  @Max(value = 6,groups = Apartment.class)
  @Min(value=1,groups=Apartment.class)
  private int bedrooms;
  @NotNull
  @Positive
  @Getter
  @Setter
  @Max(value = 12,groups = House.class)
  @Min(value=1,groups = House.class)
  @Max(value = 4,groups = Apartment.class)
  @Min(value=1,groups=Apartment.class)
  private int bathrooms;
  @PositiveOrZero
  @Getter
  @Setter
  @Min(value = 0, message = "Incorrect value of parking spots")
  private int parkingSpots;
  @NotNull
  @Positive
  @Getter
  @Setter
  @Max(value= 3000, groups = House.class, message = " Verify the size of area of House")
  @Min(value = 50 , groups = House.class, message = " Verify the size of area of House")
  @Max(value = 400,groups = Apartment.class)
  @Min(value=40,groups=Apartment.class)
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
