package com.springrest.service;

import com.springrest.dto.OwnerDTO;
import com.springrest.exception.OwnerNotFoundException;

import java.util.List;

public interface OwnerService {

    Integer saveOwner(OwnerDTO ownerDTO);

    void updateOwner(int ownerId, OwnerDTO ownerDTO) throws OwnerNotFoundException;

    void updatePetDetails(int ownerId, String petName) throws OwnerNotFoundException;

    List<OwnerDTO> findAllOwners();
}
