package com.la_haus.infrastructure.mysql.mapper;

import com.la_haus.domain.entity.Property;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;

public class Properties {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter
    @Getter
    private long id;
    @Column(columnDefinition="json")
    @Setter
    @Getter
    private Property property;
}
