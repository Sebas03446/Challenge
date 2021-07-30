package com.la_haus.domain.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;


@Embeddable
public class Location {
    @NotNull
    @Setter
    @Getter
    private float latitude;
    @NotNull
    @Setter
    @Getter
    private float longitude;
}
