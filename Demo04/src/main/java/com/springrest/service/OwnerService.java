package com.springrest.service;

import java.util.List;

import com.springrest.dto.OwnerDTO;
import com.springrest.exception.OwnerNotFoundException;

public interface OwnerService {

	Integer saveOwner(OwnerDTO ownerDTO);

	OwnerDTO findOwner(int ownerId) throws OwnerNotFoundException;

	void updateOwner(int ownerId, OwnerDTO ownerDTO) throws OwnerNotFoundException;

	void updatePetDetails(int ownerId, String petName) throws OwnerNotFoundException;

	void deleteOwner(int ownerId) throws OwnerNotFoundException;

	List<OwnerDTO> findAllOwners();

}
