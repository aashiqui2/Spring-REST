package com.springrest.service;

import java.util.List;

import com.springrest.dto.OwnerDTO;

public interface OwnerService {
	
	List<OwnerDTO> findAllOwners();
	List<OwnerDTO> findAllOwnersWithoutPets();

}
