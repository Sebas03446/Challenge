package com.la_haus.domain.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
@Embeddable
public class Pricing {
    @NonNull
    @Getter
    @Setter
    @Min(1000000)
    @Max(15000000)
    private int  salePrice;
    @Getter
    @Setter
    private int administrativeFee;
}
