package com.springrest.controller;

import com.springrest.dto.OwnerDTO;
import com.springrest.dto.UpdatePetDTO;
import com.springrest.exception.OwnerNotFoundException;
import com.springrest.exception.ValidationException;
import com.springrest.service.OwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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

    @GetMapping("/{ownerId}")
    public ResponseEntity<?> findOwner(@PathVariable int ownerId) {
        try{
           OwnerDTO ownerDTO= ownerService.findOwner(ownerId);
           return ResponseEntity.status(HttpStatus.OK).body(ownerDTO);
        }catch(OwnerNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{ownerId}")
    public ResponseEntity<Void> updateOwner(@PathVariable int ownerId,@RequestBody OwnerDTO ownerDTO) {
        try {
            ownerService.updateOwner(ownerId, ownerDTO);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (OwnerNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PatchMapping("/{ownerId}")
    public ResponseEntity<Void> updatePetDetails(@PathVariable int ownerId,@RequestBody UpdatePetDTO updatePetDTO) {
        try {
            ownerService.updatePetDetails(ownerId, updatePetDTO.getName());
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (OwnerNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{ownerId}")
    public ResponseEntity<?> deleteOwner(@PathVariable int ownerId) {
        try{
            ownerService.deleteOwner(ownerId);
            return ResponseEntity.status(HttpStatus.OK).build();
        }catch (OwnerNotFoundException e)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<OwnerDTO>> findAllOwners() {
        List<OwnerDTO> ownerDTOList = ownerService.findAllOwners();
        return ResponseEntity.status(HttpStatus.OK).body(ownerDTOList);
    }
    @GetMapping("/pets/dob")
    public ResponseEntity<?> getAllOwnersByPetDateOfBirthBetween(
           @RequestParam(defaultValue = "2010-01-01") LocalDate startDate,
           @RequestParam(defaultValue = "2020-12-12") LocalDate endDate){
        try{
            List<OwnerDTO> ownerDTOList=ownerService.findByAllOwnersByPetDateOfBirthBetween(startDate,endDate);
            return ResponseEntity.status(HttpStatus.OK).body(ownerDTOList);
        }catch(ValidationException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

}