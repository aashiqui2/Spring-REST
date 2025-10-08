package com.springrest.controller;

import com.springrest.dto.ErrorDTO;
import com.springrest.dto.OwnerDTO;
import com.springrest.dto.OwnerPetInfoDTO;
import com.springrest.dto.UpdatePetDTO;
import com.springrest.exception.OwnerNotFoundException;
import com.springrest.exception.ValidationException;
import com.springrest.service.OwnerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PastOrPresent;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Tag(name = "Owner", description = "APIs for managing owners and their pets")
@Validated
@RequestMapping("/owners")
@RequiredArgsConstructor
@RestController
public class OwnerController {

    private final OwnerService ownerService;

    @Operation(summary = "Create owner", description = "Save a new owner and their pet to the database.")
    @ApiResponse(responseCode = "201", description = "Owner Successfully Created")
    @ApiResponse(responseCode = "400", description = "Constraint Violation Error",
            content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
    @ApiResponse(responseCode = "500", description = "Internal Server Error",
            content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
    @PostMapping(consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Integer> saveOwner(@Valid @RequestBody OwnerDTO ownerDTO) {
        Integer ownerId = ownerService.saveOwner(ownerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(ownerId);
    }

    //-----------------------------------------------------------------------------------------------------------------
    @Operation(summary = "Get owner by owner ID", description = "Retrieve owner details using their unique id.")
    @ApiResponse(responseCode = "200", description = "Owner Details Retrieved Successfully")
    @ApiResponse(responseCode = "400", description = "Constraint Violation Error",
            content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
    @ApiResponse(responseCode = "404", description = "Owner Not Found",
            content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
    @ApiResponse(responseCode = "500", description = "Internal Server Error",
            content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
    @GetMapping(value = "/{ownerId}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<OwnerDTO> findOwner(@PathVariable @Min(value = 1, message = "{owner.id.positive}") int ownerId) throws OwnerNotFoundException {
        OwnerDTO ownerDTO = ownerService.findOwner(ownerId);
        return ResponseEntity.status(HttpStatus.OK).body(ownerDTO);
    }

    //-----------------------------------------------------------------------------------------------------------------
    @Operation(summary = "Update owner details", description = "Update an existing owner's details.")
    @ApiResponse(responseCode = "200", description = "Owner Updated Successfully")
    @ApiResponse(responseCode = "400", description = "Constraint Violation Error",
            content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
    @ApiResponse(responseCode = "404", description = "Owner Not Found",
            content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
    @PutMapping(value = "/{ownerId}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Void> updateOwner(@PathVariable @Min(value = 1, message = "{owner.id.positive}") int ownerId,
                                            @Valid @RequestBody OwnerDTO ownerDTO) throws OwnerNotFoundException {
        ownerService.updateOwner(ownerId, ownerDTO);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    //-----------------------------------------------------------------------------------------------------------------
    @Operation(summary = "Update pet details for an owner", description = "Update pet details of a specific owner.")
    @ApiResponse(responseCode = "200", description = "Pet Details Updated Successfully")
    @ApiResponse(responseCode = "400", description = "Constraint Violation Error",
            content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
    @ApiResponse(responseCode = "404", description = "Owner Not Found",
            content = @Content(schema = @Schema(implementation = ErrorDTO.class)))

    @PatchMapping(value = "/{ownerId}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Void> updatePetDetails(@PathVariable @Min(value = 1, message = "{owner.id.positive}") int ownerId,
                                                 @Valid @RequestBody UpdatePetDTO updatePetDTO) throws OwnerNotFoundException {
        ownerService.updatePetDetails(ownerId, updatePetDTO.getName());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    //----------------------------------------------------------------------------------------------------------------
    @Operation(summary = "Delete owner", description = "Delete an owner by their ID.")
    @ApiResponse(responseCode = "200", description = "Owner Deleted Successfully")
    @ApiResponse(responseCode = "400", description = "Constraint Violation Error",
            content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
    @ApiResponse(responseCode = "404", description = "Owner Not Found",
            content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
    @DeleteMapping("/{ownerId}")
    public ResponseEntity<Void> deleteOwner(@PathVariable @Min(value = 1, message = "{owner.id.positive}") int ownerId) throws OwnerNotFoundException {
        ownerService.deleteOwner(ownerId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    //----------------------------------------------------------------------------------------------------------------------
    @Operation(summary = "Get all owners", description = "Retrieve all owners.")
    @ApiResponse(responseCode = "200", description = "List of Owners Retrieved Successfully")
    @ApiResponse(responseCode = "500", description = "Internal Server Error",
            content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<OwnerDTO>> findAllOwners() {
        List<OwnerDTO> ownerDTOList = ownerService.findAllOwners();
        return ResponseEntity.status(HttpStatus.OK).body(ownerDTOList);
    }

    //----------------------------------------------------------------------------------------------------------------------
    @Operation(summary = "Get all owners without pets", description = "Retrieve all owners excluding pet information.")
    @ApiResponse(responseCode = "200", description = "List of Owners Retrieved Successfully")
    @ApiResponse(responseCode = "500", description = "Internal Server Error",
            content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
    @GetMapping(params = "version=2")
    public ResponseEntity<List<OwnerDTO>> findAllOwnersWithoutPets() {
        List<OwnerDTO> ownerDTOList = ownerService.findAllOwnersWithoutPets();
        return ResponseEntity.status(HttpStatus.OK).body(ownerDTOList);
    }

    //----------------------------------------------------------------------------------------------------------------------
    @Operation(summary = "Filter owners by pet's date of birth", description = "Retrieve owners whose pets were born within a specific date range.")
    @ApiResponse(responseCode = "200", description = "List of Owners Retrieved Successfully")
    @ApiResponse(responseCode = "400", description = "Constraint Violation Error", content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
    @ApiResponse(responseCode = "409", description = "Validation Error", content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
    @GetMapping(value = "/pets/dob", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<OwnerDTO>> getAllOwnersByPetDateOfBirthBetween(
            @RequestParam(defaultValue = "2010-01-01") @PastOrPresent(message = "{startDate.old}") LocalDate startDate,
            @RequestParam(defaultValue = "2020-12-12") @PastOrPresent(message = "{endDate.old}") LocalDate endDate
    ) throws ValidationException {
        List<OwnerDTO> ownerDTOList = ownerService.findByAllOwnersByPetDateOfBirthBetween(startDate, endDate);
        return ResponseEntity.status(HttpStatus.OK).body(ownerDTOList);
    }

    //----------------------------------------------------------------------------------------------------------------------
    @Operation(summary = "Filter owners by location", description = "Use matrix variables for filtering.\n\n" +
                    "Example: `/owners/filter-by-location/location;city=Kolkata;state=West Bengal`"
    )
    @GetMapping("/filter-by-location/{location}")
    public ResponseEntity<List<OwnerDTO>> getAllOwnersByCityAndState(
            @Parameter(description = "Matrix variables should be appended. Example: location;city=Kolkata;state=West Bengal")
            @PathVariable String location,
            @MatrixVariable(pathVar = "location") Map<String, String> locationParams
    ) throws ValidationException {
        List<OwnerDTO> owners = ownerService.findAllOwnersByCityAndState(
                locationParams.get("city"), locationParams.get("state"));
        return ResponseEntity.ok(owners);
    }
    //----------------------------------------------------------------------------------------------------------------------


    @Operation(summary = "Get all owner details with pets per Page", description = "Retrieve detailed owner and pet information.")
    @ApiResponse(responseCode = "200", description = "Owner Details Retrieved Successfully")
    @ApiResponse(responseCode = "500", description = "Internal Server Error",
            content = @Content(schema = @Schema(implementation = ErrorDTO.class)))

    @GetMapping(value = "/details", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<OwnerPetInfoDTO>> getAllOwnerDetails(
            @RequestParam(defaultValue = "1") int pageNumber,
            @RequestParam(defaultValue = "1") int pageSize,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "true") boolean order
    ) {
        List<OwnerPetInfoDTO> ownerPetInfoDTOList = ownerService.findOwnerDetails(pageNumber, pageSize, sortBy, order);
        return ResponseEntity.status(HttpStatus.OK).body(ownerPetInfoDTOList);
    }

    //----------------------------------------------------------------------------------------------------------------------
    @Operation(summary = "Get owner details as list", description = "Retrieve owner details as a pageable list.")
    @ApiResponse(responseCode = "200", description = "Owner Details Retrieved Successfully")
    @ApiResponse(responseCode = "500", description = "Internal Server Error",
            content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
    @GetMapping(value = "/details/list", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<OwnerPetInfoDTO>> getOwnerDetailsAsList(@ParameterObject Pageable pageable) {
        List<OwnerPetInfoDTO> ownerPetInfoDTOList = ownerService.findOwnerDetailsAsList(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(ownerPetInfoDTOList);
    }

    //----------------------------------------------------------------------------------------------------------------------
    @Operation(summary = "Get owner details as pageable", description = "Retrieve owner details as pageable data.")
    @ApiResponse(responseCode = "200", description = "Owner Details Retrieved Successfully")
    @ApiResponse(responseCode = "500", description = "Internal Server Error",
            content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
    @GetMapping(value = "/details/page", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Page<OwnerPetInfoDTO>> getOwnerDetailsAsPage(@ParameterObject Pageable pageable) {
        Page<OwnerPetInfoDTO> ownerPetInfoDTOPage = ownerService.findOwnerDetailsAsPage(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(ownerPetInfoDTOPage);
    }
}
