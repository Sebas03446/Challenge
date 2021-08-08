package com.Properties.domain.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
@Embeddable
public class Pricing{
    public interface Colombia {

    };
    public interface Mexico {

    };

    @Getter
    @Setter
    @Min(value = 1000000, message ="Check the price of property in mexico ",groups=Mexico.class)
    @Max(value=15000000,message ="Check the price of property in mexico ",groups=Mexico.class)
    @Min(value = 50000000,message ="Check the price of property in Colombia ",groups = Colombia.class)
    @Max(value = 350000000,message ="Check the price of property in Colombia ",groups = Colombia.class)
    private int  salePrice;
    @Getter
    @Setter
    private int administrativeFee;
}
