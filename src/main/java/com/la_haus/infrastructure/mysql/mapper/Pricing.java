package com.la_haus.infrastructure.mysql.mapper;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

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
