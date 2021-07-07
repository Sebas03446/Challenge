package com.la_haus.domain.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
@Data
public class Pricing {
    /*Property p = new Property();

    public Object getP() {
        return p.getId();
    }*/
    @Getter
    @Setter
    @Min(1000000)
    @Max(15000000)
    private int  salePrice;
    @Getter
    @Setter
    private int administrativeFee;

    /*public Pricing(int salePrice, int administrativeFee) {
        this.salePrice = salePrice;
        this.administrativeFee = administrativeFee;
    }*/
}
