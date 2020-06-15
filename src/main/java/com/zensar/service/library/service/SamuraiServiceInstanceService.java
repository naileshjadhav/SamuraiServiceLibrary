package com.zensar.service.library.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zensar.service.library.entity.ServiceInstance;
import com.zensar.service.library.entity.ServiceLibrary;
import com.zensar.service.library.exception.ResourceNotFound;
import com.zensar.service.library.model.SamuraiServiceInstanceDto;
import com.zensar.service.library.model.ServiceLibraryDto;
import com.zensar.service.library.repository.ServiceInstanceRepository;
import com.zensar.service.library.repository.ServiceLibraryRespository;
import com.zensar.service.library.util.BeanUtilToCopyNonNullProperties;

@Service
public class SamuraiServiceInstanceService {

	private static final Logger log = LoggerFactory.getLogger(SamuraiServiceInstanceService.class);
	@Autowired
	private ServiceInstanceRepository instanceRepository;

	@Autowired
	private ServiceLibraryRespository libraryRespository;

	public SamuraiServiceInstanceDto createServiceInstance(SamuraiServiceInstanceDto dto) {
		log.info("Start createServiceInstance..");
		String serviceName = dto.getServiceName();
		ServiceLibrary library = libraryRespository.findByServiceName(dto.getServiceName())
				.orElseThrow((() -> new ResourceNotFound("Resource not found service name: " + serviceName)));
		ServiceInstance entity = new ServiceInstance();
		// BeanUtilToCopyNonNullProperties copyNonNullProperties = new
		// BeanUtilToCopyNonNullProperties();
		log.info("DTO: {}", dto.toString());
		// entity = (ServiceInstance) copyNonNullProperties.copyNonNullProperties(dto,
		// entity);
		BeanUtils.copyProperties(dto, entity);
		log.info("entity: {}", entity);
		entity.setServiceLibrary(library);
		entity = instanceRepository.save(entity);
		// dto = (SamuraiServiceInstanceDto)
		// copyNonNullProperties.copyNonNullProperties(entity,dto);
		BeanUtils.copyProperties(entity, dto);
		log.info("Finish createServiceInstance..");
		return dto;
	}

	public SamuraiServiceInstanceDto updateServiceInstance(SamuraiServiceInstanceDto dto) {
		log.info("Start updateServiceInstance..");
		String serviceName = dto.getServiceName();
		ServiceLibrary library = libraryRespository.findByServiceName(dto.getServiceName())
				.orElseThrow((() -> new ResourceNotFound("Resource not found service name: " + serviceName)));
		String instanceName = dto.getServiceInstanceName();
		ServiceInstance entity = instanceRepository.findByServiceInstanceName(dto.getServiceInstanceName())
				.orElseThrow(() -> new ResourceNotFound("Resource not found for instance name: " + instanceName));
		BeanUtilToCopyNonNullProperties<SamuraiServiceInstanceDto> copyNonNullProperties = new BeanUtilToCopyNonNullProperties<SamuraiServiceInstanceDto>();
		log.info("DTO: {}", dto.toString());
		SamuraiServiceInstanceDto target = new SamuraiServiceInstanceDto();
		BeanUtils.copyProperties(entity, target);
		SamuraiServiceInstanceDto copied = copyNonNullProperties.copyNonNullProperties(target, dto);
		BeanUtils.copyProperties(copied, entity);
		log.info("entity: {}", entity);
		entity.setServiceLibrary(library);
		entity = instanceRepository.save(entity);
		BeanUtils.copyProperties(entity, dto);
		log.info("Finish updateServiceInstance..");
		return dto;
	}

	public SamuraiServiceInstanceDto getServiceInstanceByName(String name) {
		log.info("Start getServiceInstanceByName..");
		ServiceInstance instance = instanceRepository.findByServiceInstanceName(name)
				.orElseThrow(() -> new ResourceNotFound("Resource not found for instance name: " + name));
		SamuraiServiceInstanceDto target = new SamuraiServiceInstanceDto();
		BeanUtils.copyProperties(instance, target);
		ServiceLibrary library = instance.getServiceLibrary();
		ServiceLibraryDto serviceLibrary = new ServiceLibraryDto();
		BeanUtils.copyProperties(library, serviceLibrary);
		//ObjectMapper mapper = new ObjectMapper();
		//SamuraiServiceInstanceDto target = mapper.convertValue(instance, SamuraiServiceInstanceDto.class);
		target.setServiceLibrary(serviceLibrary);
		log.info("End getServiceInstanceByName..");
		return target;
	}

	
}
