package com.guilhermeasmorais.demo.entities.converters;

import com.guilhermeasmorais.demo.entities.AddressEntity;
import com.guilhermeasmorais.demo.entities.dto.AddressDTO;

public abstract class ToAddressDTO {
	
	protected AddressEntity addressEntity;
	
	public AddressDTO toAddressDTO() {
		
		return AddressDTO.builder()
				.id(this.addressEntity.getId())
				.streetName(this.addressEntity.getStreetName())
				.number(this.addressEntity.getNumber())
				.complement(this.addressEntity.getComplement())
				.neighbourhood(this.addressEntity.getNeighbourhood())
				.city(this.addressEntity.getCity())
				.state(this.addressEntity.getState())
				.country(this.addressEntity.getCountry())
				.zipCode(this.addressEntity.getZipCode())
				.latitude(this.addressEntity.getLatitude())
				.longitude(this.addressEntity.getLongitude())
				.build();
		
	}

}
