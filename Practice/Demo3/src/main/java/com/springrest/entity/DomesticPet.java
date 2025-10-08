package com.springrest.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@Table(name = "domestic_pet_table")
@Entity
public class DomesticPet extends Pet {

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate birthDate;

}

