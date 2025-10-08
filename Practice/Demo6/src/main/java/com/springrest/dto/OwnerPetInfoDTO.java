package com.springrest.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Setter
@Getter
@JacksonXmlRootElement(localName = "ownerPetInfoDTO")
public class OwnerPetInfoDTO {

    @EqualsAndHashCode.Include
    private int id;
    private String firstName;
    private String lastName;
    private String petName;
}
