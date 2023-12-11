package com.milexpress.milexpressserver.repository;

import com.milexpress.milexpressserver.model.db.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
}
