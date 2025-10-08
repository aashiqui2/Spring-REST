package com.springrest.service.impl;

import com.springrest.dto.OwnerDTO;
import com.springrest.entity.Owner;
import com.springrest.exception.OwnerNotFoundException;
import com.springrest.exception.ValidationException;
import com.springrest.repository.OwnerRepository;
import com.springrest.service.OwnerService;
import com.springrest.util.OwnerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class OwnerServiceImpl implements OwnerService {

    private final OwnerRepository ownerRepository;
    private final OwnerMapper ownerMapper;

    @Value("${owner.not.found}")
    private String ownerNotFound;

    @Value("${date.range.invalid}")
    private String dateRangeInvalid;

    @Override
    public Integer saveOwner(OwnerDTO ownerDTO) {
      Owner owner=ownerMapper.ownerDTOToOwner(ownerDTO);
      ownerRepository.save(owner);
      return owner.getId();
    }

    @Override
    public OwnerDTO findOwner(int ownerId) throws OwnerNotFoundException {
       Owner owner=ownerRepository.findById(ownerId)
               .orElseThrow(()->new OwnerNotFoundException(String.format(ownerNotFound,ownerId)));
       return ownerMapper.ownerToOwnerDTO(owner);
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
    public void deleteOwner(Integer ownerId) throws OwnerNotFoundException {
        Owner owner=ownerRepository.findById(ownerId)
                .orElseThrow(()->new OwnerNotFoundException(String.format(ownerNotFound,ownerId)));
        ownerRepository.delete(owner);
    }

    @Override
    public List<OwnerDTO> findAllOwners() {
        return ownerRepository.findAll()
                .stream()
                .map(ownerMapper::ownerToOwnerDTO)
                .toList();
    }

    @Override
    public List<OwnerDTO> findByAllOwnersByPetDateOfBirthBetween(LocalDate startDate, LocalDate endDate) throws ValidationException {
        if(startDate.isAfter(endDate)){
            throw new ValidationException(String.format(dateRangeInvalid,startDate,endDate));
        }else{
            return ownerRepository.findByAllOwnersByPetDateOfBirthRange(startDate,endDate)
                    .stream()
                    .map(ownerMapper::ownerToOwnerDTO)
                    .toList();
        }
    }
}
