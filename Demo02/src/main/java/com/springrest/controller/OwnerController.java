package com.springrest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springrest.exception.OwnerNotFoundException;
import com.springrest.service.OwnerService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/owners")
@RequiredArgsConstructor
@RestController //= @Controller + @ResponseBody
public class OwnerController {

	private final OwnerService ownerService;

	/* spring framework 4.3 introduced variants  of the @RequstMapping(method = RequestMethod.POST)
	 * @PostMapping
	 * @GetMapping
	 * @PutMapping
	 * @PatchMapping
	 * @DeleteMapping
	 * @GetMapping
	 */
	
	 /* ResponseEntity<String>
	    @GetMapping("/hello")
		public ResponseEntity<String> hello() {
		    return ResponseEntity
		            .status(200)                      // HTTP status
		            .header("Custom-Header", "Hi")    // extra header
		            .body("Hello World");             // response body
		}
	 */
	
	@PostMapping
	public ResponseEntity<String> saveOwner() {
		String response = ownerService.saveOwner();
		return ResponseEntity.status(HttpStatus.CREATED).body(response);// Response body
	}

	@GetMapping
	public ResponseEntity<String> findOwner() {
		try {
			String response = ownerService.findOwner();
			return ResponseEntity.status(HttpStatus.OK).body(response);
		} catch (OwnerNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	@PutMapping
	public ResponseEntity<String> updateOwner() {
		try {
			String response = ownerService.updateOwner();
			return ResponseEntity.status(HttpStatus.OK).body(response);
		} catch (OwnerNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
	
	@PatchMapping
	public ResponseEntity<String> updatePetDetails() {
		try {
			String response = ownerService.updatePetDetails();
			return ResponseEntity.status(HttpStatus.OK).body(response);
		} catch (OwnerNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	@DeleteMapping
	public ResponseEntity<String> deleteOwner() {
		try {
			String response = ownerService.deleteOwner();
			return ResponseEntity.status(HttpStatus.OK).body(response);
		} catch (OwnerNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	@GetMapping("/all")
	public ResponseEntity<String> findAllOwners() {
		String response = ownerService.findAllOwners();
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}


}
