package com.zensar.service.library.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "super_category")
public class SuperCategory {

	@Id
	@Column(name = "super_category_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long superCategoryId;
	@Column(name = "super_category_name", length = 100)
	private String superCategoryName;
	@Column(name = "super_category_enable")
	private boolean superCategoryEnable;
	@Column(name = "creation_date")
	private LocalDateTime creationDate;
	
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name = "super_category_id",insertable = true,updatable = true)
	private List<ServiceLibrary> libraries = new ArrayList<ServiceLibrary>();
	
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name = "super_category_id",insertable = true,updatable = true)
	private List<SubCategory> subCategory = new ArrayList<SubCategory>();
	
	public boolean addSubCategory(SubCategory e) {
		return this.subCategory.add(e);
	}
	
	public boolean addLibrary(ServiceLibrary library) {
		return this.libraries.add(library);
	}
}
