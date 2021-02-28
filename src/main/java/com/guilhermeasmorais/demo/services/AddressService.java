package com.guilhermeasmorais.demo.services;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.guilhermeasmorais.demo.entities.AddressEntity;
import com.guilhermeasmorais.demo.entities.dto.AddressDTO;
import com.guilhermeasmorais.demo.repositories.AddressRepository;

@Service
public class AddressService {

	@Autowired
	private AddressRepository addressRepository;

	public AddressDTO save(AddressDTO address) throws JsonMappingException, JsonProcessingException {

		try {

			this.validarZipCode(address.getZipCode());

			if (address.getLatitude() == null || address.getLongitude() == null
					|| String.valueOf(address.getLatitude()).trim().equals("")
					|| String.valueOf(address.getLongitude()).trim().equals("")) {

				this.fillLatitudeLongitude(address);

			}

			return this.addressRepository.save(address.toAddressEntity()).loadSuper().toAddressDTO();

		} catch (RuntimeException e) {

			throw e;

		}

	}

	public List<AddressDTO> findAll() {

		return this.convertList(this.addressRepository.findAll());

	}

	public AddressDTO findById(Long id) {

		AddressEntity addressEntity = this.addressRepository.findById(id).get();
		return addressEntity != null ? addressEntity.loadSuper().toAddressDTO() : null;

	}

	public AddressDTO deleteById(Long id) {

		AddressDTO address = this.findById(id);
		this.addressRepository.deleteById(id);

		return address;

	}

	public AddressDTO update(AddressDTO address) throws JsonMappingException, JsonProcessingException {

		AddressDTO dbAddress = this.findById(address.getId());
		address.setId(dbAddress.getId());
		return this.save(address);

	}

	public List<AddressDTO> convertList(List<AddressEntity> addresses) {

		return addresses.stream().map(address -> {
			return address.loadSuper().toAddressDTO();
		}).collect(Collectors.toList());

	}

	public String validarZipCode(String zipCode) {

		String regex = "^[0-9]{5}(?:-[0-9]{3})?$";

		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(zipCode);

		if (!matcher.matches()) {
			throw new RuntimeException("Teste");
		}

		return zipCode;

	}

	public String fillLatitudeLongitude(AddressDTO addressDto) throws JsonMappingException, JsonProcessingException {

		RestTemplate restTemplate = new RestTemplate();

		Map<String, String> vars = new HashMap<String, String>();
		vars.put("number", addressDto.getNumber());
		vars.put("streetName", addressDto.getStreetName().replace(" ", "+"));
		vars.put("city", addressDto.getCity());
		vars.put("state", addressDto.getState());

		String result = restTemplate.getForObject(
				"https://maps.googleapis.com/maps/api/geocode/json?" + "address=" + "{number}" + "+{streetName},"
						+ "+{city}," + "+{state}" + "&key=AIzaSyCj0cY2yEvVfYhAaTz3-P2MW-YRKmhz5Uw",
				String.class, vars);

		ObjectMapper mapper = new ObjectMapper();
		JsonNode jsonNode = mapper.readTree(result);

		addressDto.setLatitude(new BigDecimal(
				String.valueOf(jsonNode.get("results").get(0).get("geometry").get("location").get("lat"))));
		addressDto.setLongitude(new BigDecimal(
				String.valueOf(jsonNode.get("results").get(0).get("geometry").get("location").get("lng"))));

		return result;

	}

}
