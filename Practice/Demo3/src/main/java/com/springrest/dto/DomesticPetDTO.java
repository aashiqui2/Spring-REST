package com.springrest.dto;

import java.time.LocalDate;

import com.springrest.enums.Gender;
import com.springrest.enums.PetType;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@ToString(callSuper = true)
@Setter
@Getter
public class DomesticPetDTO extends PetDTO {
    @PastOrPresent(message = "{pet.birth.date.old}")
    @NotNull(message = "{pet.birth.date.required}")
    private LocalDate birthDate;
    private String formattedBirthDate;

    @Builder
    public DomesticPetDTO(int id, String name, Gender gender, PetType type, OwnerDTO ownerDTO, LocalDate birthDate, String formattedBirthDate) {
        super(id, name, gender, type, ownerDTO);
        this.birthDate = birthDate;
        this.formattedBirthDate = formattedBirthDate;
    }
}
