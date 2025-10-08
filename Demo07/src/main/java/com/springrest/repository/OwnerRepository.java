package com.springrest.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.springrest.entity.Owner;

public interface OwnerRepository extends JpaRepository<Owner, Integer> {
	
	// Direct Subclass Join
	@Query("SELECT o FROM Owner o JOIN FETCH DomesticPet p ON o.pet = p WHERE p.birthDate BETWEEN :startDate AND :endDate")
	List<Owner> findAllOwnersByPetDateOfBirthRange(LocalDate startDate, LocalDate endDate);
}
