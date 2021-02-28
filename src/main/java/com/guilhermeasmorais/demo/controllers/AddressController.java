package com.guilhermeasmorais.demo.controllers;

import org.hibernate.PropertyValueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.guilhermeasmorais.demo.entities.dto.AddressDTO;
import com.guilhermeasmorais.demo.services.AddressService;

@RestController
public class AddressController {

	@Autowired
	private AddressService addressService;

	@PostMapping("/save")
	public ResponseEntity<?> save(@RequestBody AddressDTO address) throws JsonMappingException, JsonProcessingException {
		try {
			return ResponseEntity.ok(this.addressService.save(address));
		} catch (RuntimeException e) {
			if (e.getCause() instanceof PropertyValueException) {
				PropertyValueException p = (PropertyValueException) e.getCause();
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The field " + p.getPropertyName() + " is invalid.");
			}
		}
		return null;
	}
	
	@GetMapping("/findAll")
	public ResponseEntity<?> findAll() {
		return ResponseEntity.ok(this.addressService.findAll());
	}
	
	@GetMapping("/findById")
	public ResponseEntity<?> findById(@RequestParam("id") Integer id) {
		return ResponseEntity.ok(this.addressService.findById(Long.parseLong(id.toString())));
	}
	
	@DeleteMapping("/deleteById")
	public ResponseEntity<?> deleteById(@RequestParam("id") Integer id) {
		this.addressService.deleteById(Long.parseLong(id.toString()));
		return ResponseEntity.ok().build();
	}
	
	@PutMapping("/update")
	public ResponseEntity<?> update(@RequestParam("id") Integer id) {
		this.addressService.deleteById(Long.parseLong(id.toString()));
		return ResponseEntity.ok().build();
	}
	
}
