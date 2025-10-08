package com.springrest.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.springrest.enums.Gender;
import com.springrest.enums.PetType;
import lombok.*;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "category"
)
@JsonSubTypes({
        @Type(value = DomesticPetDTO.class, name = "domestic"),
        @Type(value = WildPetDTO.class, name = "wild")
})
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded=true)
@ToString
@Setter
@Getter
public abstract class PetDTO {

    @EqualsAndHashCode.Include
    private int id;
    private String name;
    private Gender gender;
    private PetType  type;
    private OwnerDTO ownerDTO;
}
