package com.la_haus.domain.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;

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
