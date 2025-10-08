package com.springrest.service.impl;

import com.fasterxml.jackson.databind.util.BeanUtil;
import com.springrest.dto.DomesticPetDTO;
import com.springrest.dto.OwnerDTO;
import com.springrest.dto.OwnerPetInfoDTO;
import com.springrest.dto.PetDTO;
import com.springrest.entity.Owner;
import com.springrest.exception.OwnerNotFoundException;
import com.springrest.exception.ValidationException;
import com.springrest.repository.OwnerRepository;
import com.springrest.service.OwnerService;
import com.springrest.util.OwnerMapper;
import com.springrest.util.OwnerPetInfoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class OwnerServiceImpl implements OwnerService {

    private final OwnerRepository ownerRepository;
    private final OwnerMapper ownerMapper;
    private final OwnerPetInfoMapper ownerPetInfoMapper;

    // For L10n
    private final MessageSource messageSource;
    private static final String OWNER_NOT_FOUND_KEY = "owner.not.found";

    @Value("${owner.not.found}")
    private String ownerNotFound;

    @Value("${date.range.invalid}")
    private String dateRangeInvalid;

    @Value("${city.is.null}")
    private String cityIsNull;

    @Value("${state.is.null}")
    private String stateIsNull;

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
                .map(this::formatDates)
                .orElseThrow(() -> new OwnerNotFoundException(String.format(getMessage(OWNER_NOT_FOUND_KEY), ownerId)));
    }

    @Override
    public void updateOwner(int ownerId, OwnerDTO ownerDTO) throws OwnerNotFoundException {
        Owner owner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new OwnerNotFoundException(String.format(getMessage(OWNER_NOT_FOUND_KEY), ownerId)));
        ownerMapper.customUpdateOwnerFromDTO(ownerDTO, owner);
        ownerRepository.save(owner);
    }

    @Override
    public void updatePetDetails(int ownerId, String petName) throws OwnerNotFoundException {
        Owner owner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new OwnerNotFoundException(String.format(getMessage(OWNER_NOT_FOUND_KEY), ownerId)));
        owner.getPet().setName(petName);
        ownerRepository.save(owner);
    }

    @Override
    public void deleteOwner(Integer ownerId) throws OwnerNotFoundException {
        Owner owner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new OwnerNotFoundException(String.format(getMessage(OWNER_NOT_FOUND_KEY), ownerId)));
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
        if (startDate.isAfter(endDate)) {
            throw new ValidationException(String.format(getMessage(dateRangeInvalid), startDate, endDate));
        } else {
            return ownerRepository.findByAllOwnersByPetDateOfBirthRange(startDate, endDate)
                    .stream()
                    .map(ownerMapper::ownerToOwnerDTO)
                    .toList();
        }
    }

    @Override
    public List<OwnerDTO> findAllOwnersByCityAndState(String city, String state) throws ValidationException {
        if (Objects.isNull(city)) {
            throw new ValidationException(getMessage(cityIsNull));
        } else if (Objects.isNull(state)) {
            throw new ValidationException(getMessage(stateIsNull));
        } else {
            return ownerRepository.findByCityAndState(city, state)
                    .stream()
                    .map(ownerMapper::ownerToOwnerDTO)
                    .toList();
        }
    }

    @Override
    public List<OwnerPetInfoDTO> findOwnerDetails(int pageNumber, int pageSize, String sortBy, boolean order) {
        Sort.Direction direction = order ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, sort);
        return ownerRepository.findIdAndFirstNameAndLastNameAndPetNameList(pageable)
                .stream()
                .map(ownerPetInfoMapper::mapObjectArrayToOwnerPetInfoDTO)
                .toList();
    }

    @Override
    public List<OwnerPetInfoDTO> findOwnerDetailsAsList(Pageable pageable) {
        return ownerRepository.findIdAndFirstNameAndLastNameAndPetNameList(pageable)
                .stream()
                .map(ownerPetInfoMapper::mapObjectArrayToOwnerPetInfoDTO)
                .toList();
    }

    @Override
    public Page<OwnerPetInfoDTO> findOwnerDetailsAsPage(Pageable pageable) {
        Page<Object[]> page = ownerRepository.findIdAndFirstNameAndLastNameAndPetNamePage(pageable);
        List<OwnerPetInfoDTO> detailsDTOList = page.stream()
                .map(ownerPetInfoMapper::mapObjectArrayToOwnerPetInfoDTO)
                .toList();
        return new PageImpl<>(detailsDTOList, pageable, page.getTotalElements());

    }

    @Override
    public List<OwnerDTO> findAllOwnersWithoutPets() {
        return ownerRepository.findAll().stream().map(ownerMapper::ownerToOwnerDTOWithoutPet).toList();
    }

    //Retrieves the localized message for the given key from the MessageSource based on the current request locale;
    private String getMessage(String key) {
        return messageSource.getMessage(key, null, LocaleContextHolder.getLocale());
    }

    private OwnerDTO formatDates(OwnerDTO ownerDTO) {
        PetDTO petDTO = ownerDTO.getPetDTO();
        if (petDTO instanceof DomesticPetDTO domesticPetDTO) {
            domesticPetDTO.setFormattedBirthDate(formatLocalDate(domesticPetDTO.getBirthDate()));
        }
        return ownerDTO;
    }

    private String formatLocalDate(LocalDate localDate) {
        return DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)
                .withLocale(LocaleContextHolder.getLocale())
                .format(localDate);
    }

}
