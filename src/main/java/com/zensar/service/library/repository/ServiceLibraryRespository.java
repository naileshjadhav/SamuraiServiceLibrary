package com.zensar.service.library.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.zensar.service.library.entity.ServiceLibrary;

@Repository
public interface ServiceLibraryRespository extends CrudRepository<ServiceLibrary, Long> {

	//Optional<ServiceLibrary> findByServiceName(String serviceName);

	Optional<List<ServiceLibrary>> findAllByServiceName(String name);

}
