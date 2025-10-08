package com.springrest.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springrest.dto.OwnerDTO;
import com.springrest.dto.OwnerPetInfoDTO;
import com.springrest.service.OwnerService;

import lombok.RequiredArgsConstructor;

//@CrossOrigin(origins="http://ip.port")
@RequiredArgsConstructor
@RequestMapping("/owners")
@RestController
public class OwnerController {

	private final OwnerService ownerService;
	
    //@CrossOrigin
	@GetMapping
	public ResponseEntity<List<OwnerDTO>> findAllOwners() {
		List<OwnerDTO> ownerDTOList = ownerService.findAllOwners();
		return ResponseEntity.status(HttpStatus.OK).body(ownerDTOList);
	}
	
	//@CrossOrigin
	@GetMapping("/details/page")
	public ResponseEntity<Page<OwnerPetInfoDTO>> getOwnerDetailsAsPage(Pageable pageable) {
		Page<OwnerPetInfoDTO> ownerPetInfoDTOPage = ownerService.findOwnerDetails(pageable);
		return ResponseEntity.status(HttpStatus.OK).body(ownerPetInfoDTOPage);
	}

}
