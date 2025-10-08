package com.springrest.service;

import java.time.LocalDate;
import java.util.List;

import com.springrest.dto.OwnerDTO;
import com.springrest.exception.OwnerNotFoundException;
import com.springrest.exception.ValidationException;

public interface OwnerService {
	
	OwnerDTO findOwner(int ownerId) throws OwnerNotFoundException;

	List<OwnerDTO> findByAllOwnersByPetDateOfBirthBetween(LocalDate startDate, LocalDate endDate)
			throws ValidationException;

}
