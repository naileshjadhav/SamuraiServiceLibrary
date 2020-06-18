package com.zensar.service.library.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zensar.service.library.entity.ServiceInstance;
import com.zensar.service.library.entity.ServiceLibrary;
import com.zensar.service.library.exception.ResourceNotFound;
import com.zensar.service.library.model.ServiceInstanceDto;
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

	public ServiceInstanceDto createServiceInstance(ServiceInstanceDto dto) {
		log.info("Start createServiceInstance..");
		List<List<ServiceLibrary>> libraryArray = dto.getServiceLibrary().stream()
				.filter(e -> e.isServiceDecommisioned() == false)
				.map(e -> libraryRespository.findAllByServiceName(e.getServiceName()).orElseThrow(
						() -> new ResourceNotFound("Resource not found for service name: " + e.getServiceName())))
				.collect(Collectors.toList());
		ServiceInstance entity = new ServiceInstance();
		BeanUtils.copyProperties(dto, entity);
		List<ServiceLibrary> serviceLibrary = libraryArray.stream()
				.flatMap(e -> e.stream()
						.map(l -> new ServiceLibrary(l.getServiceId(), null, l.getSuperCategory(), l.getSubCategory(),
								l.getServiceName(), l.getTypeOfService(), l.isServiceDecommisioned(),
								l.getServiceDescription(), l.getCreationDate(), l.getLogoImage())))
				.collect(Collectors.toList());
		entity.setServiceLibrary(serviceLibrary);
		log.info("entity: {}", entity);
		entity = instanceRepository.save(entity);
		BeanUtils.copyProperties(entity, dto);
		List<ServiceLibraryDto> target = libraryArray.stream()
				.flatMap(e -> e.stream()
						.map(l -> new ServiceLibraryDto(null, null, l.getServiceName(), l.getTypeOfService(),
								l.isServiceDecommisioned(), l.getServiceDescription(), l.getCreationDate(),
								l.getServiceId(), l.getLogoImage())))
				.collect(Collectors.toList());
		dto.setServiceLibrary(target);
		log.info("Finish createServiceInstance..");
		return dto;
	}

	public ServiceInstanceDto updateServiceInstance(ServiceInstanceDto dto) {

		log.info("Start updateServiceInstance..");
		String instanceName = dto.getServiceInstanceName();
		ServiceInstance instance = instanceRepository.findByServiceInstanceName(dto.getServiceInstanceName())
				.orElseThrow(() -> new ResourceNotFound("Resource not found for instance name: " + instanceName));
		BeanUtilToCopyNonNullProperties<ServiceInstanceDto> util = new BeanUtilToCopyNonNullProperties<ServiceInstanceDto>();
		ServiceInstanceDto instanceDto = new ServiceInstanceDto();
		BeanUtils.copyProperties(instance, instanceDto);
		ServiceInstanceDto valueObj = util.copyNonNullProperties(instanceDto, dto);
		BeanUtils.copyProperties(valueObj, instance);
		if (dto.getServiceLibrary() != null) {
			List<List<ServiceLibrary>> libraryArray = dto.getServiceLibrary().stream()
					.filter(e -> e.isServiceDecommisioned() == false)
					.map(e -> libraryRespository.findAllByServiceName(e.getServiceName()).orElseThrow(
							() -> new ResourceNotFound("Resource not found service name: " + e.getServiceName())))
					.collect(Collectors.toList());
			List<ServiceLibrary> serviceLibrary = libraryArray.stream()
					.flatMap(e -> e.stream()
							.map(l -> new ServiceLibrary(l.getServiceId(), null, null, null, l.getServiceName(),
									l.getTypeOfService(), l.isServiceDecommisioned(), l.getServiceDescription(),
									l.getCreationDate(), l.getLogoImage())))
					.collect(Collectors.toList());

			instance.setServiceLibrary(serviceLibrary);

			List<ServiceLibraryDto> libraryDtos = serviceLibrary.stream()
					.map(l -> new ServiceLibraryDto(null, null, l.getServiceName(), l.getTypeOfService(),
							l.isServiceDecommisioned(), l.getServiceDescription(), l.getCreationDate(),
							l.getServiceId(), l.getLogoImage()))
					.collect(Collectors.toList());
			instanceDto.setServiceLibrary(libraryDtos);
		}
		instance = instanceRepository.save(instance);
		BeanUtils.copyProperties(instance, instanceDto);
		log.info("Finish updateServiceInstance..");
		return instanceDto;

//		List<List<ServiceLibrary>> libraryArray = dto.getServiceLibrary().stream()
//				.filter(e -> e.isServiceDecommisioned() == false)
//				.map(e -> libraryRespository.findAllByServiceName(e.getServiceName()).orElseThrow(
//						() -> new ResourceNotFound("Resource not found service name: " + e.getServiceName())))
//				.collect(Collectors.toList());
//		List<ServiceLibrary> serviceLibrary = libraryArray.stream()
//				.flatMap(e -> e.stream()
//						.map(l -> new ServiceLibrary(l.getServiceId(), null, null, null, l.getServiceName(),
//								l.getTypeOfService(), l.isServiceDecommisioned(), l.getServiceDescription(),
//								l.getCreationDate(), l.getLogoImage())))
//				.collect(Collectors.toList());
//		String instanceName = dto.getServiceInstanceName();
//		ServiceInstance entity = instanceRepository.findByServiceInstanceName(dto.getServiceInstanceName())
//				.orElseThrow(() -> new ResourceNotFound("Resource not found for instance name: " + instanceName));
//		BeanUtilToCopyNonNullProperties<ServiceInstanceDto> copyNonNullProperties = new BeanUtilToCopyNonNullProperties<ServiceInstanceDto>();
//		log.info("DTO: {}", dto.toString());
//		ServiceInstanceDto target = new ServiceInstanceDto();
//		BeanUtils.copyProperties(entity, target);
//		ServiceInstanceDto copied = copyNonNullProperties.copyNonNullProperties(target, dto);
//		BeanUtils.copyProperties(copied, entity);
//		log.info("entity: {}", entity);
//		entity.setServiceLibrary(serviceLibrary);
//		entity = instanceRepository.save(entity);
//		BeanUtils.copyProperties(entity, dto);
//		List<ServiceLibraryDto> dtos = libraryArray.stream()
//				.flatMap(e -> e.stream()
//						.map(l -> new ServiceLibraryDto(null, null, l.getServiceName(), l.getTypeOfService(),
//								l.isServiceDecommisioned(), l.getServiceDescription(), l.getCreationDate(),
//								l.getServiceId(), l.getLogoImage())))
//				.collect(Collectors.toList());
//		dto.setServiceLibrary(dtos);
//		log.info("Finish updateServiceInstance..");
//		return dto;
	}

	public ServiceInstanceDto getServiceInstanceByName(String name) {
		log.info("Start getServiceInstanceByName..");
		ServiceInstance instance = instanceRepository.findByServiceInstanceName(name)
				.orElseThrow(() -> new ResourceNotFound("Resource not found for instance name: " + name));
		ServiceInstanceDto target = new ServiceInstanceDto();
		BeanUtils.copyProperties(instance, target);
		List<ServiceLibrary> library = instance.getServiceLibrary();
		List<ServiceLibraryDto> serviceLibrary = library.stream()
				.map(e -> new ServiceLibraryDto(null, null, e.getServiceName(), e.getTypeOfService(),
						e.isServiceDecommisioned(), e.getServiceDescription(), e.getCreationDate(), e.getServiceId(),
						null))
				.collect(Collectors.toList());
		target.setServiceLibrary(serviceLibrary);
		log.info("End getServiceInstanceByName..");
		return target;
	}

}
