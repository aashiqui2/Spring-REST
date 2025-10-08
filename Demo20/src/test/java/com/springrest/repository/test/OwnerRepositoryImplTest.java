package com.springrest.repository.test;

import static com.springrest.enums.Gender.M;
import static com.springrest.enums.PetType.BIRD;
import static com.springrest.util.TestDataUtil.createMockOwnerWithMockDomesticPet;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;

import com.springrest.config.TestConfig;
import com.springrest.entity.Owner;
import com.springrest.repository.OwnerRepository;


//Scans for JPA @Entity classes in the specified package 
//so that Hibernate/JPA knows which classes should be mapped to tables.
@EntityScan(basePackages = "com.springrest.entity")  

//Enables Spring Data JPA repositories. 
//Instead of scanning the whole project, it restricts scanning to the package of OwnerRepository.
@EnableJpaRepositories(basePackageClasses = OwnerRepository.class)  

//Loads only the beans/configuration defined in TestConfig into the Spring test context.
//This helps avoid loading the entire application context and keeps tests lightweight.
@ContextConfiguration(classes = TestConfig.class)  

//A Spring Boot test slice for JPA components (repositories).
//It:
//- Configures an embedded/in-memory database by default (like H2).
//- Scans for @Entity classes and sets up Spring Data JPA repositories.
//- Applies @Transactional to each test method, so changes are rolled back after the test.
//  (➡ meaning tests won’t actually write permanent data to the database unless @Rollback(false) or @Commit is used).
//This is ideal for repository layer unit tests.
@DataJpaTest

class OwnerRepositoryImplTest {

	@Autowired
	private OwnerRepository ownerRepository;

	@Test
	void test_FindAllOwnersByPetDateOfBirthRange_WhenOwnersExist_ShouldReturnOwners() {
		// Given
		Owner owner1 = createMockOwnerWithMockDomesticPet("FirstName1", "LastName1", M, "City1", "State1",
				"9876543211", "firstname1.lastname1@scaleupindia.com", "PetName1", M, BIRD, LocalDate.of(2021, 1, 1));
		ownerRepository.save(owner1);
		
		Owner owner2 = createMockOwnerWithMockDomesticPet("FirstName2", "LastName2", M, "City2", "State2",
				"9876543212", "firstname2.lastname2@scaleupindia.com", "PetName2", M, BIRD, LocalDate.of(2022, 2, 2));
		ownerRepository.save(owner2);
		
		Owner owner3 = createMockOwnerWithMockDomesticPet("FirstName3", "LastName3", M, "City3", "State3",
				"9876543213", "firstname3.lastname3@scaleupindia.com", "PetName3", M, BIRD, LocalDate.of(2023, 3, 3));
		ownerRepository.save(owner3);
		
		LocalDate inputStartDate = LocalDate.of(2022, 1, 1);
		LocalDate inputEndDate = LocalDate.of(2022, 3, 3);
		// When
		List<Owner> actualOwnerList = ownerRepository.findAllOwnersByPetDateOfBirthRange(inputStartDate, inputEndDate);
		// Then
		assertThat(actualOwnerList).isNotEmpty();
		assertThat(actualOwnerList.get(0)).isNotNull().isEqualTo(owner2);
	}

	@Test
	void test_FindAllOwnersByPetDateOfBirthRange_WhenNoOwnersExist_ShouldReturnEmptyList() {
		// Given
		LocalDate inputStartDate = LocalDate.of(2022, 1, 1);
		LocalDate inputEndDate = LocalDate.of(2022, 3, 3);
		// When
		List<Owner> actualOwnerList = ownerRepository.findAllOwnersByPetDateOfBirthRange(inputStartDate, inputEndDate);
		// Then
		assertThat(actualOwnerList).isEmpty();
	}

}
