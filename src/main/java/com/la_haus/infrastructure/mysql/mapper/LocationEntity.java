package com.la_haus.infrastructure.mysql.mapper;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
@Embeddable
public class LocationEntity {
    @NotNull
    @Setter
    @Getter
    private float latitude;
    @NotNull
    @Setter
    @Getter
    private float longitude;
}
