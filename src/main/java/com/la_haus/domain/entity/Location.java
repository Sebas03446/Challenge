package com.la_haus.domain.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
public class Location {
    @Setter
    @Getter
    private float latitude;
    @Setter
    @Getter
    private float longitude;
    /*public Location(float latitud, float longitud) {
        this.latitud = latitud;
        this.longitud = longitud;
    }*/
}
