package com.springrest.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.springrest.dto.OwnerDTO;
import com.springrest.entity.Owner;
import com.springrest.exception.OwnerNotFoundException;
import com.springrest.exception.ValidationException;
import com.springrest.repository.OwnerRepository;
import com.springrest.service.OwnerService;
import com.springrest.util.OwnerMapper;

import lombok.RequiredArgsConstructor;


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
		Owner owner = ownerMapper.ownerDTOToOwner(ownerDTO);
		ownerRepository.save(owner);
		return owner.getId();
	}

	@Override
	public OwnerDTO findOwner(int ownerId) throws OwnerNotFoundException {
		return ownerRepository.findById(ownerId).map(ownerMapper::ownerToOwnerDTO)
				.orElseThrow(() -> new OwnerNotFoundException(String.format(ownerNotFound, ownerId)));
	}

	@Override
	public void updatePetDetails(int ownerId, String petName) throws OwnerNotFoundException {
		Owner owner = ownerRepository.findById(ownerId)
				.orElseThrow(() -> new OwnerNotFoundException(String.format(ownerNotFound, ownerId)));
		owner.getPet().setName(petName);
		ownerRepository.save(owner);
	}

	@Override
	public void deleteOwner(int ownerId) throws OwnerNotFoundException {
		boolean ownerExists = ownerRepository.existsById(ownerId);
		if (!ownerExists) {
			throw new OwnerNotFoundException(String.format(ownerNotFound, ownerId));
		} else {
			ownerRepository.deleteById(ownerId);
		}
	}

	@Override
	public List<OwnerDTO> findByAllOwnersByPetDateOfBirthBetween(LocalDate startDate, LocalDate endDate)
			throws ValidationException {
		if (startDate.isAfter(endDate)) {
			throw new ValidationException(String.format(dateRangeInvalid, startDate, endDate));
		} else {
			return ownerRepository.findAllOwnersByPetDateOfBirthRange(startDate, endDate)
					.stream()
					.map(ownerMapper::ownerToOwnerDTO)
					.toList();
		}
	}

}
