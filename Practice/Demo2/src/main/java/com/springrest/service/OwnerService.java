package com.springrest.service;

import com.springrest.dto.OwnerDTO;
import com.springrest.exception.OwnerNotFoundException;
import com.springrest.exception.ValidationException;

import java.time.LocalDate;
import java.util.List;

public interface OwnerService {

    Integer saveOwner(OwnerDTO ownerDTO);

    OwnerDTO findOwner(int ownerId) throws OwnerNotFoundException;

    void updateOwner(int ownerId, OwnerDTO ownerDTO) throws OwnerNotFoundException;

    void updatePetDetails(int ownerId, String petName) throws OwnerNotFoundException;

    void deleteOwner(Integer ownerId) throws OwnerNotFoundException;

    List<OwnerDTO> findAllOwners();

    List<OwnerDTO> findByAllOwnersByPetDateOfBirthBetween(LocalDate startDate, LocalDate endDate) throws ValidationException;
}
