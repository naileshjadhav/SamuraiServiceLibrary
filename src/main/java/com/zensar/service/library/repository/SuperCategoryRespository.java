package com.zensar.service.library.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.zensar.service.library.entity.SuperCategory;

@Repository
public interface SuperCategoryRespository extends CrudRepository<SuperCategory, Long> {

	SuperCategory findBySuperCategoryName(String superCategoryName);

}
