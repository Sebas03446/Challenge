
package com.la_haus.domain.entity;



import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class House extends Property {
    @Max(14)
    @Min(1)
    private int bedrooms;
    @Max(value=12, message = "Incorrect nums of bathrooms House")
    @Min(value=1, message = "Incorrect nums of bathrooms House")
    private int bathrooms;
    @Max(value= 3000, message = " Verify the size of area of House")
    @Min(value = 50 , message = " Verify the size of area of House")
    private int area;
    @Min(value = 0, message = "Incorrect value of parking spots")
    private int parkingSpots;
    @Override
    public int getBedrooms() {
        return this.bedrooms;
    }
    @Override
    public void setBedrooms(int bedrooms) {
        this.bedrooms = bedrooms;
    }

    @Override
    public int getBathrooms() {
        return this.bathrooms;
    }
    @Override
    public void setBathrooms(int bathrooms) {
        this.bathrooms = bathrooms;
    }

    @Override
    public int getArea() {
        return this.area;
    }
    @Override
    public void setArea(int area) {
        this.area = area;
    }
    @Override
    public int getParkingSpots() {
        return this.parkingSpots;
    }

    @Override
    public void setParkingSpots(int parkingSpots) {
        this.parkingSpots = parkingSpots;
    }
}
