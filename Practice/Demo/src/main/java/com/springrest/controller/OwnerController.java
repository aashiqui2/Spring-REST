package com.springrest.controller;

import com.springrest.exception.OwnerNotFoundException;
import com.springrest.service.OwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping(value = "/owners")
@RestController
public class OwnerController {
    private final OwnerService ownerService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> saveOwner() {
        String response=ownerService.saveOwner();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @RequestMapping(method = RequestMethod.GET)
    public String findOwner() {
        try {
            return ownerService.findOwner();
        } catch (OwnerNotFoundException ex) {
            return ex.getMessage();
        }
    }

    @RequestMapping(method = RequestMethod.PUT)
    public String updateOwner() {
        try {
            return ownerService.updateOwner();
        } catch (OwnerNotFoundException e) {
            return e.getMessage();
        }
    }


    @RequestMapping(method = RequestMethod.PATCH)
    public String updatePetDetails() {
        try {
            return ownerService.updatePetDetails();
        } catch (OwnerNotFoundException e) {
            return e.getMessage();
        }
    }


    @RequestMapping(method = RequestMethod.DELETE)
    public String deleteOwner() {
        try {
            return ownerService.deleteOwner();
        } catch (OwnerNotFoundException e) {
            return e.getMessage();
        }
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public String findAllOwners() {
        return ownerService.findAllOwners();
    }


}
