package com.springrest.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.springrest.dto.OwnerDTO;
import com.springrest.dto.OwnerPetInfoDTO;

public interface OwnerService {
	
	List<OwnerDTO> findAllOwners();

	List<OwnerPetInfoDTO> findOwnerDetails(int pageNumber, int pageSize, String sortBy, boolean descending);

	List<OwnerPetInfoDTO> findOwnerDetailsAsList(Pageable pageable);

	Page<OwnerPetInfoDTO> findOwnerDetailsAsPage(Pageable pageable);

}
