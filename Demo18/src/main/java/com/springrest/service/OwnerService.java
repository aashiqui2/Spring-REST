package com.springrest.service;

import com.springrest.dto.OwnerDTO;
import com.springrest.exception.OwnerNotFoundException;


public interface OwnerService {

	OwnerDTO findOwner(int ownerId) throws OwnerNotFoundException;

}
