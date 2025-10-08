package com.springrest.dto;

import com.springrest.enums.Gender;
import com.springrest.enums.PetType;
import lombok.*;

@NoArgsConstructor
@ToString(callSuper = true)
@Setter
@Getter
public class WildPetDTO extends PetDTO {

    private String birthPlace;

    @Builder
    public WildPetDTO(int id, String name, Gender gender, PetType type, OwnerDTO ownerDTO, String birthPlace) {
        super(id, name, gender, type, ownerDTO);
        this.birthPlace = birthPlace;
    }

}