package com.springrest.service;

import com.springrest.dto.CompanionDTO;
import com.springrest.dto.CustomerDTO;
import com.springrest.exception.CustomerNotFoundException;


public interface CustomerService {

	Integer saveCustomer(CustomerDTO customerDTO);

	CustomerDTO findCustomer(int customerId) throws CustomerNotFoundException;

	void updateCompanionDetails(int ownerId, CompanionDTO companionDTO) throws CustomerNotFoundException;

	void deleteCustomer(int customerId) throws CustomerNotFoundException;

}
