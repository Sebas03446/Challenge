package com.la_haus.domain.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Favorites {
    @Id
    @Column(unique = false)
    private int favorite;
}
