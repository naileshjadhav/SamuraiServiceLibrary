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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "sub_category")
public class SubCategory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "sub_category_id")
	private Long subCategoryId;
	@Column(name = "sub_category_name", length = 100)
	private String subCategoryName;
	@Column(name = "sub_category_enable")
	private boolean subCategoryEnable;
	@Column(name = "creation_date")
	private LocalDateTime creationDate;

//	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//	@JoinColumn(name = "super_category_id",insertable = true,updatable = true)
//	private List<SuperCategory> superCategory = new ArrayList<SuperCategory>();
	@ManyToOne
	@JoinColumn(name = "super_category_id",insertable = true,updatable = true)
	private SuperCategory superCategory;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "sub_category_id",insertable = true,updatable = true)
	private List<ServiceLibrary> libraries = new ArrayList<ServiceLibrary>();
	
//	public boolean addSuperCategory(SuperCategory e) {
//		return this.superCategory.add(e);
//	}
	
	public boolean addLibrary(ServiceLibrary library) {
		return this.libraries.add(library);
	}
}
