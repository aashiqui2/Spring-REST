package com.springrest.service;

import com.springrest.dto.OwnerDTO;
import com.springrest.dto.OwnerPetInfoDTO;
import com.springrest.exception.OwnerNotFoundException;
import com.springrest.exception.ValidationException;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Page;

public interface OwnerService {

    Integer saveOwner(OwnerDTO ownerDTO);

    OwnerDTO findOwner(int ownerId) throws OwnerNotFoundException;

    void updateOwner(int ownerId, OwnerDTO ownerDTO) throws OwnerNotFoundException;

    void updatePetDetails(int ownerId, String petName) throws OwnerNotFoundException;

    void deleteOwner(Integer ownerId) throws OwnerNotFoundException;

    List<OwnerDTO> findAllOwners();

    List<OwnerDTO> findByAllOwnersByPetDateOfBirthBetween(LocalDate startDate, LocalDate endDate)  throws ValidationException;

    List<OwnerDTO> findAllOwnersByCityAndState(String city, String state) throws ValidationException;

    List<OwnerPetInfoDTO> findOwnerDetails(int pageNumber, int pageSize, String sortBy, boolean order);

    List<OwnerPetInfoDTO> findOwnerDetailsAsList(Pageable pageable);

    Page<OwnerPetInfoDTO> findOwnerDetailsAsPage(Pageable pageable);

    List<OwnerDTO> findAllOwnersWithoutPets();
}
