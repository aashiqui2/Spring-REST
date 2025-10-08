package com.springrest.entity;

import com.springrest.enums.PetType;
import com.springrest.enums.Gender;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "pet_table")
@Entity
public abstract class Pet extends Base {

    @Column(name = "name", nullable = false)
    private String name;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "type", nullable = false)
    private PetType type;

    @OneToOne(mappedBy = "pet")
    private Owner owner;

}
