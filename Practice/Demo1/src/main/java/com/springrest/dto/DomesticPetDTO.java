package com.springrest.dto;

import com.springrest.enums.Gender;
import com.springrest.enums.PetType;
import lombok.*;

import java.time.LocalDate;


@NoArgsConstructor
@ToString(callSuper = true)
@Setter
@Getter
public class DomesticPetDTO extends PetDTO {

    private LocalDate birthDate;

    @Builder
    public DomesticPetDTO(int id, String name, Gender gender, PetType type, OwnerDTO ownerDTO, LocalDate birthDate) {
        super(id, name, gender, type, ownerDTO);
        this.birthDate = birthDate;
    }

}