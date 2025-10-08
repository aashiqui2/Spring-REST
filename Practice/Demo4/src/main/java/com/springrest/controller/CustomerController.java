package com.springrest.controller;

import com.springrest.dto.CompanionDTO;
import com.springrest.dto.CustomerDTO;
import com.springrest.exception.CustomerNotFoundException;
import com.springrest.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/customers")
@RestController
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<Integer> saveCustomer(@RequestBody CustomerDTO customerDTO) {
        Integer customerId = customerService.saveCustomer(customerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(customerId);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerDTO> findCustomer(@PathVariable int customerId) throws CustomerNotFoundException {
        CustomerDTO customerDTO = customerService.findCustomer(customerId);
        return ResponseEntity.status(HttpStatus.OK).body(customerDTO);
    }

    @PatchMapping("/{customerId}")
    public ResponseEntity<Void> updateCompanionDetails(@PathVariable int customerId,
                                                       @RequestBody CompanionDTO companionDTO) throws CustomerNotFoundException {
        customerService.updateCompanionDetails(customerId, companionDTO);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable int customerId) throws CustomerNotFoundException {
        customerService.deleteCustomer(customerId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}

