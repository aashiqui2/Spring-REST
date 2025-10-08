package com.springrest.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
@Getter
@JacksonXmlRootElement(localName = "updatePetDTO")
public class UpdatePetDTO {
	
	@Size(max=255,message="{pet.name.length}")
	@NotBlank(message="{pet.name.required}")
	private String name;

}
