package com.springrest.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springrest.dto.OwnerDTO;
import com.springrest.dto.OwnerPetInfoDTO;
import com.springrest.service.OwnerService;

import lombok.RequiredArgsConstructor;

@Validated
@RequestMapping("/owners")
@RequiredArgsConstructor
@RestController
public class OwnerController {

	private final OwnerService ownerService;

	@GetMapping
	public ResponseEntity<List<OwnerDTO>> findAllOwners() {
		List<OwnerDTO> ownerDTOList = ownerService.findAllOwners();
		return ResponseEntity.status(HttpStatus.OK).body(ownerDTOList);
	}

	@GetMapping("/details")
	public ResponseEntity<List<OwnerPetInfoDTO>> getOwnerDetails(
			@RequestParam(defaultValue = "1") int pageNumber,
			@RequestParam(defaultValue = "1") int pageSize, 
			@RequestParam(defaultValue = "id") String sortBy,
			@RequestParam(defaultValue = "false") boolean descending) {
		List<OwnerPetInfoDTO> ownerPetInfoDTOList = ownerService.findOwnerDetails(pageNumber, pageSize, sortBy, descending);
		return ResponseEntity.status(HttpStatus.OK).body(ownerPetInfoDTOList);
	}
	
	// spring will automatically creates the PageRequest object if we didn't give the pageable
	// PageRequest.of(0, 20, Sort.unsorted())
	// http://localhost:8080/owners/details/list?page=1&size=5&sort=firstName,desc
	// here size must be named as size no other names should be used
	// here sort must be named as sort no other names should be used
	@GetMapping("/details/list")
	public ResponseEntity<List<OwnerPetInfoDTO>> getOwnerDetailsAsList(Pageable pageable) {
		List<OwnerPetInfoDTO> ownerPetInfoDTOList = ownerService.findOwnerDetailsAsList(pageable);
		return ResponseEntity.status(HttpStatus.OK).body(ownerPetInfoDTOList);
	}
	
	// By default size is 20 for pageable
	// http://localhost:8080/owners/details/page?page=1&size=5&sort=lastName,desc
	@GetMapping("/details/page")
	public ResponseEntity<Page<OwnerPetInfoDTO>> getOwnerDetailsAsPage(Pageable pageable) {
		Page<OwnerPetInfoDTO> ownerPetInfoDTOPage = ownerService.findOwnerDetailsAsPage(pageable);
		return ResponseEntity.status(HttpStatus.OK).body(ownerPetInfoDTOPage);
	}
}
