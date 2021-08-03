package com.la_haus.domain.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
@Embeddable
public class Pricing {
    /*public interface Country{

    };
    @Min(value = 50000000,groups = Country.class)
    @Max(value = 350000000,groups = Country.class)*/

    @Getter
    @Setter
    @Min(1000000)
    @Max(15000000)
    private int  salePrice;
    @Getter
    @Setter
    private int administrativeFee;
}
