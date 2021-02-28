package com.guilhermeasmorais.demo.entities.dto;

import java.math.BigDecimal;

import com.guilhermeasmorais.demo.entities.AddressEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressDTO {

	private Long id;
	private String streetName;
	private String number;
	private String complement;
	private String neighbourhood;
	private String city;
	private String state;
	private String country;
	private String zipCode;
	private BigDecimal latitude;
	private BigDecimal longitude;

	public AddressEntity toAddressEntity() {

		AddressEntity addressEntity = new AddressEntity();
		addressEntity.setStreetName(this.getStreetName());
		addressEntity.setNumber(this.getNumber());
		addressEntity.setComplement(this.getComplement());
		addressEntity.setNeighbourhood(this.getNeighbourhood());
		addressEntity.setCity(this.getCity());
		addressEntity.setState(this.getState());
		addressEntity.setCountry(this.getCountry());
		addressEntity.setZipCode(this.getZipCode());
		addressEntity.setLatitude(this.getLatitude());
		addressEntity.setLongitude(this.getLongitude());

		return addressEntity;

	}

}
