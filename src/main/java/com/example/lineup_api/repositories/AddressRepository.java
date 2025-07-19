package com.example.lineup_api.repositories;

import com.example.lineup_api.entities.Address;
import org.springframework.data.repository.CrudRepository;

public interface AddressRepository extends CrudRepository<Address, Long> {
}