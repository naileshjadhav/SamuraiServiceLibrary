package com.zensar.service.library.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.zensar.service.library.model.ServiceInstanceDto;
import com.zensar.service.library.service.SamuraiServiceInstanceService;

@RestController
@CrossOrigin(origins = "*")
public class SamuraiServiceInstanceController {

	private static final Logger log = LoggerFactory.getLogger(SamuraiServiceInstanceController.class);
	@Autowired
	private SamuraiServiceInstanceService service;

	@PostMapping(value = "/instance")
	public ResponseEntity<ServiceInstanceDto> createSamuraiServiceInstance(@RequestBody ServiceInstanceDto dto) {
		log.info("Starting createSamuraiServiceInstance....");
		if (dto.getIsDecomissioned() == null)
			dto.setIsDecomissioned(false);
		if (dto.getIsInActive() == null)
			dto.setIsInActive(false);
		dto = this.service.createServiceInstance(dto);
		log.info("End createSamuraiServiceInstance....");
		return new ResponseEntity<ServiceInstanceDto>(dto, HttpStatus.CREATED);
	}

	@PutMapping(value = "/instance")
	public ResponseEntity<ServiceInstanceDto> updateSamuraiServiceInstance(@RequestBody ServiceInstanceDto dto) {
		log.info("Starting updateSamuraiServiceInstance....");
		if (dto.getIsDecomissioned() == null)
			dto.setIsDecomissioned(false);
		if (dto.getIsInActive() == null)
			dto.setIsInActive(false);
		dto = this.service.updateServiceInstance(dto);
		log.info("End updateSamuraiServiceInstance....");
		return new ResponseEntity<ServiceInstanceDto>(dto, HttpStatus.OK);
	}

	@GetMapping(value = "/instance/{name}")
	public ResponseEntity<ServiceInstanceDto> getServiceInstanceByName(@PathVariable String name) {
		log.info("Starting getServiceInstanceByName....");
		ServiceInstanceDto dto = this.service.getServiceInstanceByName(name);
		log.info("End getServiceInstanceByName....");
		return new ResponseEntity<ServiceInstanceDto>(dto, HttpStatus.OK);
	}

	@GetMapping(value = "/instance/id/{instanceId}")
	public ResponseEntity<ServiceInstanceDto> getServiceInstanceById(@PathVariable Long instanceId) {
		log.info("Starting getServiceInstanceById....");
		ServiceInstanceDto dto = this.service.getServiceInstanceById(instanceId);
		log.info("End getServiceInstanceById....");
		return new ResponseEntity<ServiceInstanceDto>(dto, HttpStatus.OK);
	}

}
