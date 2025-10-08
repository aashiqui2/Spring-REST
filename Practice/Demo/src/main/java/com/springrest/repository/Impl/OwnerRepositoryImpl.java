package com.springrest.repository.Impl;

import com.springrest.repository.OwnerRepository;
import org.springframework.stereotype.Repository;

@Repository
public class OwnerRepositoryImpl implements OwnerRepository {
    @Override
    public String save() {
        return "Saved owner.";
    }

    @Override
    public String find() {
        return "Found owner.";
    }

    @Override
    public String updateOwner() {
        return "Updated owner.";
    }

    @Override
    public String updatePetDetails() {
        return "Updated pet details of owner.";
    }

    @Override
    public String delete() {
        return "Deleted owner.";
    }

    @Override
    public String findAll() {
        return "Found all owners.";
    }
}
