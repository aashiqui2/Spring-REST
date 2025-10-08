package com.springrest.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springrest.dto.OwnerDTO;
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

	// @GetMapping("/v2") // URL Versioning
	// @GetMapping(headers = "version=2") // custom header
	// @GetMapping(produces = "application/vnd.demo-v2+json") // accept header (vnd ->vendor specific)
	@GetMapping(params = "version=2") //request param
	public ResponseEntity<List<OwnerDTO>> findAllOwnersWithoutPets() {
		List<OwnerDTO> ownerDTOList = ownerService.findAllOwnersWithoutPets();
		return ResponseEntity.status(HttpStatus.OK).body(ownerDTOList);
	}
}
