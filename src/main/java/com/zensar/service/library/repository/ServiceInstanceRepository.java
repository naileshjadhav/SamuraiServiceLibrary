package com.zensar.service.library.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.zensar.service.library.entity.ServiceInstance;

public interface ServiceInstanceRepository extends CrudRepository<ServiceInstance, Long>{

	Optional<ServiceInstance> findByServiceInstanceName(String serviceInstanceName);

}
