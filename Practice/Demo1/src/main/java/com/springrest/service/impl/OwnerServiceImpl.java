package com.springrest.service.impl;

import com.springrest.dto.OwnerDTO;
import com.springrest.entity.Owner;
import com.springrest.exception.OwnerNotFoundException;
import com.springrest.repository.OwnerRepository;
import com.springrest.service.OwnerService;
import com.springrest.util.OwnerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OwnerServiceImpl implements OwnerService {

    private final OwnerRepository ownerRepository;
    private final OwnerMapper ownerMapper;

    @Value("${owner.not.found}")
    private String ownerNotFound;

    @Override
    public Integer saveOwner(OwnerDTO ownerDTO) {
      Owner owner=ownerMapper.ownerDTOToOwner(ownerDTO);
      ownerRepository.save(owner);
      return owner.getId();
    }

    @Override
    public void updateOwner(int ownerId, OwnerDTO ownerDTO) throws OwnerNotFoundException {
        Owner owner=ownerRepository.findById(ownerId)
                .orElseThrow(()->new OwnerNotFoundException(String.format(ownerNotFound,ownerId)));
        ownerMapper.customUpdateOwnerFromDTO(ownerDTO,owner);
        ownerRepository.save(owner);
    }

    @Override
    public void updatePetDetails(int ownerId, String petName) throws OwnerNotFoundException {
        Owner owner= ownerRepository.findById(ownerId)
                .orElseThrow(()->new OwnerNotFoundException(String.format(ownerNotFound,ownerId)));
        owner.getPet().setName(petName);
        ownerRepository.save(owner);
    }

    @Override
    public List<OwnerDTO> findAllOwners() {
        return ownerRepository.findAll()
                .stream()
                .map(ownerMapper::ownerToOwnerDTO)
                .toList();
    }
}
