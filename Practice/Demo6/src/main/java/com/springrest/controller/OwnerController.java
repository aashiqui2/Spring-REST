package com.springrest.controller;

import com.springrest.dto.OwnerDTO;
import com.springrest.dto.OwnerIdDTO;
import com.springrest.dto.OwnerPetInfoDTO;
import com.springrest.dto.UpdatePetDTO;
import com.springrest.exception.OwnerNotFoundException;
import com.springrest.exception.ValidationException;
import com.springrest.service.OwnerService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PastOrPresent;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Validated
@RequestMapping("/owners")
@RequiredArgsConstructor
@RestController
public class OwnerController {

    private final OwnerService ownerService;

    @PostMapping(consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<EntityModel<OwnerIdDTO>> saveOwner(@Valid @RequestBody OwnerDTO ownerDTO) throws ValidationException, OwnerNotFoundException {
        Integer ownerId = ownerService.saveOwner(ownerDTO);
        OwnerIdDTO ownerIdDTO = new OwnerIdDTO(ownerId);
        EntityModel<OwnerIdDTO> entityModel = EntityModel.of(ownerIdDTO,
                linkTo(methodOn(OwnerController.class).saveOwner(ownerDTO)).withSelfRel(),
                linkTo(methodOn(OwnerController.class).findOwner(ownerId)).withRel("findOwner"),
                linkTo(methodOn(OwnerController.class).findAllOwners()).withRel("findAllOwners"),
                linkTo(methodOn(OwnerController.class).findAllOwnersWithoutPets()).withRel("findAllOwnersWithoutPet"));

        return ResponseEntity.status(HttpStatus.CREATED).body(entityModel);
    }

    @GetMapping("/{ownerId}")
    public ResponseEntity<EntityModel<OwnerDTO>> findOwner(@PathVariable @Min(value = 1, message = "{owner.id.positive}") int ownerId) throws OwnerNotFoundException {
        OwnerDTO ownerDTO = ownerService.findOwner(ownerId);
        EntityModel<OwnerDTO> entityModel = EntityModel.of(ownerDTO,
                linkTo(methodOn(OwnerController.class).findOwner(ownerId)).withSelfRel(),
                linkTo(methodOn(OwnerController.class).findAllOwners()).withRel("findAllOwners"),
                linkTo(methodOn(OwnerController.class).findAllOwnersWithoutPets()).withRel("findAllOwnersWithoutPet"));

        return ResponseEntity.ok(entityModel);
    }

    @PutMapping("/{ownerId}")
    public ResponseEntity<Void> updateOwner(@PathVariable @Min(value = 1, message = "{owner.id.positive}") int ownerId, @Valid @RequestBody OwnerDTO ownerDTO) throws OwnerNotFoundException {
        ownerService.updateOwner(ownerId, ownerDTO);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{ownerId}")
    public ResponseEntity<Void> updatePetDetails(@PathVariable @Min(value = 1, message = "{owner.id.positive}") int ownerId, @Valid @RequestBody UpdatePetDTO updatePetDTO) throws OwnerNotFoundException {
        ownerService.updatePetDetails(ownerId, updatePetDTO.getName());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{ownerId}")
    public ResponseEntity<Void> deleteOwner(@PathVariable @Min(value = 1, message = "{owner.id.positive}") int ownerId) throws OwnerNotFoundException {
        ownerService.deleteOwner(ownerId);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<OwnerDTO>>> findAllOwners() {
        List<OwnerDTO> owners = ownerService.findAllOwners();

        List<EntityModel<OwnerDTO>> ownerModelList = owners
                .stream()
                .map(owner -> {
                    try {
                        return EntityModel.of(owner,
                                linkTo(methodOn(OwnerController.class).findOwner(owner.getId())).withSelfRel()
                        , linkTo(methodOn(OwnerController.class).findAllOwnersWithoutPets()).withRel("findAllOwnersWithoutPet"));
                    } catch (OwnerNotFoundException e) {
                       return null;
                    }
                })
                .toList();

        return ResponseEntity.ok(CollectionModel.of(ownerModelList, linkTo(methodOn(OwnerController.class).findAllOwners()).withSelfRel()));
    }

    @GetMapping(params = "version=2")
    public ResponseEntity<CollectionModel<EntityModel<OwnerDTO>>> findAllOwnersWithoutPets() {
        List<OwnerDTO> owners = ownerService.findAllOwnersWithoutPets();

        List<EntityModel<OwnerDTO>> ownerModelList = owners
                .stream()
                .map(owner -> {
                    try {
                        return EntityModel.of(owner,
                                linkTo(methodOn(OwnerController.class).findOwner(owner.getId())).withSelfRel());
                    } catch (OwnerNotFoundException e) {
                        return null;
                    }
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(ownerModelList));
    }

    @GetMapping("/pets/dob")
    public ResponseEntity<CollectionModel<EntityModel<OwnerDTO>>> getAllOwnersByPetDateOfBirthBetween(@RequestParam(defaultValue = "2010-01-01") @PastOrPresent(message = "{startDate.old}") LocalDate startDate, @RequestParam(defaultValue = "2020-12-12") @PastOrPresent(message = "{endDate.old}") LocalDate endDate) throws ValidationException {
        List<OwnerDTO> owners = ownerService.findByAllOwnersByPetDateOfBirthBetween(startDate, endDate);
        List<EntityModel<OwnerDTO>> ownerModels = owners
                .stream()
                .map(owner -> {
                    try {
                        return EntityModel.of(owner,
                                linkTo(methodOn(OwnerController.class).findOwner(owner.getId())).withSelfRel());
                    } catch (OwnerNotFoundException e) {
                        return null;
                    }
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(ownerModels));
    }

    @GetMapping("/filter-by-location/{location}")
    public ResponseEntity<CollectionModel<EntityModel<OwnerDTO>>> getAllOwnersByCityAndState(@PathVariable String location, @MatrixVariable(pathVar = "location") Map<String, String> locationParams) throws ValidationException {
        List<OwnerDTO> owners = ownerService.findAllOwnersByCityAndState(locationParams.get("city"), locationParams.get("state"));

        List<EntityModel<OwnerDTO>> ownerModels = owners
                .stream()
                .map(owner -> {
                    try {
                        return EntityModel.of(owner,
                                linkTo(methodOn(OwnerController.class).findOwner(owner.getId())).withSelfRel());
                    } catch (OwnerNotFoundException e) {
                        return null;
                    }
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(ownerModels));
    }

    @GetMapping("/details")
    public ResponseEntity<CollectionModel<EntityModel<OwnerPetInfoDTO>>> getAllOwnerDetails(@RequestParam(defaultValue = "1") int pageNumber, @RequestParam(defaultValue = "1") int pageSize, @RequestParam(defaultValue = "id") String sortBy, @RequestParam(defaultValue = "true") boolean order) {
        List<OwnerPetInfoDTO> ownerDetails = ownerService.findOwnerDetails(pageNumber, pageSize, sortBy, order);

        List<EntityModel<OwnerPetInfoDTO>> detailModels = ownerDetails
                .stream()
                .map(EntityModel::of)
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(detailModels));
    }

    @GetMapping("/details/list")
    public ResponseEntity<CollectionModel<EntityModel<OwnerPetInfoDTO>>> getOwnerDetailsAsList(Pageable pageable) {
        List<OwnerPetInfoDTO> ownerDetails = ownerService.findOwnerDetailsAsList(pageable);

        List<EntityModel<OwnerPetInfoDTO>> detailModels = ownerDetails
                .stream()
                .map(EntityModel::of)
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(detailModels));
    }

    @GetMapping("/details/page")
    public ResponseEntity<PagedModel<EntityModel<OwnerPetInfoDTO>>> getOwnerDetailsAsPage(
            Pageable pageable,
            PagedResourcesAssembler<OwnerPetInfoDTO> pagedResourcesAssembler
    ) {
        Page<OwnerPetInfoDTO> page = ownerService.findOwnerDetailsAsPage(pageable);
        PagedModel<EntityModel<OwnerPetInfoDTO>> pagedModel = pagedResourcesAssembler.toModel(page);
        return ResponseEntity.ok(pagedModel);
    }

}
