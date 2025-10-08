package com.springrest.controller;

import com.springrest.dto.OwnerDTO;
import com.springrest.dto.UpdatePetDTO;
import com.springrest.exception.OwnerNotFoundException;
import com.springrest.service.OwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/owners")
@RequiredArgsConstructor
@RestController
public class OwnerController {

    private final OwnerService ownerService;

    @PostMapping
    public ResponseEntity<Integer> saveOwner(@RequestBody OwnerDTO ownerDTO) {
        Integer ownerId = ownerService.saveOwner(ownerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(ownerId);
    }

    @PutMapping
    public ResponseEntity<Void> updateOwner(@RequestBody OwnerDTO ownerDTO) {
        try {
            ownerService.updateOwner(ownerDTO.getId(), ownerDTO);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (OwnerNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PatchMapping
    public ResponseEntity<Void> updatePetDetails(@RequestBody UpdatePetDTO updatePetDTO) {
        try {
            ownerService.updatePetDetails(updatePetDTO.getId(), updatePetDTO.getName());
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (OwnerNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<OwnerDTO>> findAllOwners() {
        List<OwnerDTO> ownerDTOList = ownerService.findAllOwners();
        return ResponseEntity.status(HttpStatus.OK).body(ownerDTOList);
    }

}