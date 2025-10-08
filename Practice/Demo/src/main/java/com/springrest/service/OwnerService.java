package com.springrest.service;

import com.springrest.exception.OwnerNotFoundException;

public interface OwnerService {

    String saveOwner();

    String findOwner() throws OwnerNotFoundException;

    String updateOwner() throws OwnerNotFoundException;

    String updatePetDetails() throws OwnerNotFoundException;

    String deleteOwner() throws OwnerNotFoundException;

    String findAllOwners();

}
