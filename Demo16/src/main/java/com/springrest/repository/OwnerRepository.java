package com.springrest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springrest.entity.Owner;


public interface OwnerRepository extends JpaRepository<Owner, Integer> {

}
