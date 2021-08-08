package com.Properties.domain.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Embeddable
public class Location {
    @NotNull
    @NotBlank
    @Setter
    @Getter
    private float latitude;
    @NotNull
    @NotBlank
    @Setter
    @Getter
    private float longitude;
}
