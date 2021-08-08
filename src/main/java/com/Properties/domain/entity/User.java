package com.Properties.domain.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    @NotNull
    @NotBlank
    @Email
    @Size(max = 255)
    @Column(unique = true)
    private String email;
    private String password;
    @ElementCollection
    private Set<Integer> favorites;
}
