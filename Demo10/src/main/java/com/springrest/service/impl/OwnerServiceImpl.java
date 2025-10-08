package com.springrest.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.springrest.dto.OwnerDTO;
import com.springrest.dto.OwnerPetInfoDTO;
import com.springrest.entity.Owner;
import com.springrest.exception.OwnerNotFoundException;
import com.springrest.exception.ValidationException;
import com.springrest.repository.OwnerRepository;
import com.springrest.service.OwnerService;
import com.springrest.util.OwnerMapper;
import com.springrest.util.OwnerPetInfoMapper;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class OwnerServiceImpl implements OwnerService {

	private final OwnerRepository ownerRepository;
	private final OwnerMapper ownerMapper;
	private final OwnerPetInfoMapper ownerPetInfoMapper;
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
		return ownerRepository.findById(ownerId)
				.map(ownerMapper::ownerToOwnerDTO)
				.orElseThrow(() -> new OwnerNotFoundException(String.format(ownerNotFound, ownerId)));
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

	@Override
	public List<OwnerDTO> findAllOwners() {
		return ownerRepository.findAll()
				.stream()
				.map(ownerMapper::ownerToOwnerDTO)
				.toList();
	}

	@Override
	public Page<OwnerPetInfoDTO> findOwnerDetails(Pageable pageable) {
		Page<Object[]> page = ownerRepository.findIdAndFirstNameAndLastNameAndPetName(pageable);
		List<OwnerPetInfoDTO> detailsDTOList = page
				.stream()
				.map(ownerPetInfoMapper::mapObjectArrayToOwnerPetInfoDTO)
				.toList();
		return new PageImpl<>(detailsDTOList, pageable, page.getTotalElements());
	}

}
