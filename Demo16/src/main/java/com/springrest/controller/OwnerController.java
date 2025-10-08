package com.springrest.controller;


import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springrest.dto.OwnerDTO;
import com.springrest.dto.OwnerIdDTO;
import com.springrest.exception.OwnerNotFoundException;
import com.springrest.service.OwnerService;

import lombok.RequiredArgsConstructor;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequiredArgsConstructor
@RequestMapping("/owners")
@RestController
public class OwnerController {

	private final OwnerService ownerService;
    // Hypermedia As The Engine Of Application State. (HATEOAS)
	// EntityModel does not work with primitive wrapper 
	 
	@PostMapping
	public ResponseEntity<EntityModel<OwnerIdDTO>> saveOwner(@RequestBody OwnerDTO ownerDTO) throws OwnerNotFoundException {
		Integer ownerId = ownerService.saveOwner(ownerDTO);
		OwnerIdDTO ownerIdDTO = new OwnerIdDTO(ownerId);
		EntityModel<OwnerIdDTO> entityModel = EntityModel.of(ownerIdDTO,
				linkTo(methodOn(OwnerController.class).saveOwner(ownerDTO)).withSelfRel(),
				linkTo(methodOn(OwnerController.class).findOwner(ownerId)).withRel("findOwner"),
				linkTo(methodOn(OwnerController.class).findAllOwners()).withRel("findAllOwners"));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(entityModel);
	}

	@GetMapping("/{ownerId}")
	public ResponseEntity<EntityModel<OwnerDTO>> findOwner(@PathVariable int ownerId) throws OwnerNotFoundException {
		OwnerDTO ownerDTO = ownerService.findOwner(ownerId);
		EntityModel<OwnerDTO> entityModel = EntityModel.of(ownerDTO,
				linkTo(methodOn(OwnerController.class).findOwner(ownerId)).withSelfRel(),
				linkTo(methodOn(OwnerController.class).findAllOwners()).withRel("findAllOwners"));
	
		return ResponseEntity.status(HttpStatus.OK).body(entityModel);
	}

	@GetMapping
	public ResponseEntity<CollectionModel<EntityModel<OwnerDTO>>> findAllOwners() {
		List<OwnerDTO> ownerDTOList = ownerService.findAllOwners();
		List<EntityModel<OwnerDTO>> entityModelList = ownerDTOList
				.stream()
				.map(ownerDTO -> {
					try {
						return EntityModel.of(ownerDTO)
							   .add(linkTo(methodOn(OwnerController.class)
							   .findOwner(ownerDTO.getId()))
							   .withRel("findOwner"));
					} catch (OwnerNotFoundException e) {
						return null;
					}
				})
				.toList();
		CollectionModel<EntityModel<OwnerDTO>> collectionModel = CollectionModel.of(entityModelList);
		collectionModel.add(linkTo(methodOn(OwnerController.class).findAllOwners()).withSelfRel());
		return ResponseEntity.status(HttpStatus.OK).body(collectionModel);
	}

}
