package com.springrest.repository;

import com.springrest.entity.Owner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public interface OwnerRepository extends JpaRepository<Owner, Integer> {

    @Query("SELECT o FROM Owner o JOIN FETCH DomesticPet dp ON o.pet = dp WHERE dp.birthDate BETWEEN :startDate AND :endDate")
    List<Owner> findByAllOwnersByPetDateOfBirthRange(LocalDate startDate, LocalDate endDate);

    List<Owner> findByCityAndState(String city, String state);

    @Query("SELECT o.id,o.firstName,o.lastName,o.pet.name from Owner o")
    List<Object[]> findIdAndFirstNameAndLastNameAndPetNameList(Pageable pageable);

    @Query("SELECT o.id,o.firstName,o.lastName,o.pet.name from Owner o")
    Page<Object[]> findIdAndFirstNameAndLastNameAndPetNamePage(Pageable pageable);
}
