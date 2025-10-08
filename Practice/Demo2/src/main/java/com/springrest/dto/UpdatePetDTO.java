package com.springrest.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Setter
@Getter
public class UpdatePetDTO {

    @EqualsAndHashCode.Include
    private int id;
    private String name;

}