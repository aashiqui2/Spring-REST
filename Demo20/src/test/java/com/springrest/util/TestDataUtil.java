package com.springrest.util;

import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;

import org.springframework.core.io.ClassPathResource;

import com.springrest.entity.DomesticPet;
import com.springrest.entity.Owner;
import com.springrest.entity.Pet;
import com.springrest.enums.Gender;
import com.springrest.enums.PetType;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TestDataUtil {

	public static Owner createMockOwnerWithMockDomesticPet(String firstName, String lastName, Gender gender,
			String city, String state, String mobileNumber, String emailId, String petName, Gender petGender,
			PetType type, LocalDate birthDate) {
		Owner owner = createMockOwner(firstName, lastName, gender, city, state, mobileNumber, emailId);
		Pet domesticPet = createMockDomesticPet(petName, petGender, type, birthDate);
		owner.setPet(domesticPet);
		return owner;
	}

	public static Owner createMockOwner(String firstName, String lastName, Gender gender, String city, String state,
			String mobileNumber, String emailId) {
		Owner owner = new Owner();
		owner.setFirstName(firstName);
		owner.setLastName(lastName);
		owner.setGender(gender);
		owner.setCity(city);
		owner.setState(state);
		owner.setMobileNumber(mobileNumber);
		owner.setEmailId(emailId);
		return owner;
	}

	public static DomesticPet createMockDomesticPet(String petName, Gender gender, PetType type, LocalDate birthDate) {
		DomesticPet domesticPet = new DomesticPet();
		domesticPet.setName(petName);
		domesticPet.setGender(gender);
		domesticPet.setType(type);
		domesticPet.setBirthDate(birthDate); 
		return domesticPet;
	}
	
	public static String convertToJson(String jsonFileName) throws IOException {
		ClassPathResource classPathResource = new ClassPathResource(jsonFileName);
		return new String(Files.readAllBytes(classPathResource.getFile().toPath())); 
	}

}
