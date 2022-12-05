package com.kakura.museum.repo;

import com.kakura.museum.models.Address;
import org.springframework.data.repository.CrudRepository;

public interface AddressRepository extends CrudRepository<Address, Long> {
}
