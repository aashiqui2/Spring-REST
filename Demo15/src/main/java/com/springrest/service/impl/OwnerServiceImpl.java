package com.springrest.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.springrest.dto.OwnerDTO;
import com.springrest.dto.OwnerPetInfoDTO;
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
		List<OwnerPetInfoDTO> detailsDTOList = page.stream()
				.map(ownerPetInfoMapper::mapObjectArrayToOwnerPetInfoDTO)
				.toList();
		return new PageImpl<>(detailsDTOList, pageable, page.getTotalElements());
	}

}
