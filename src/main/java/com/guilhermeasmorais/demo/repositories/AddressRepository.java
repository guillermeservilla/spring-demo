package com.guilhermeasmorais.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.guilhermeasmorais.demo.entities.AddressEntity;

public interface AddressRepository extends JpaRepository<AddressEntity, Long> {
	

}
