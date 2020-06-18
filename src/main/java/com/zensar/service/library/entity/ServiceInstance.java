package com.zensar.service.library.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "service_instance")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceInstance {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "service_instance_id", length = 10)
	private Long serviceInstanceId;
	@Column(name = "service_instance_name", length = 50)
	private String serviceInstanceName;
	@Column(name = "is_decomissioned", columnDefinition = "bit default false")
	private Boolean isDecomissioned;
	@Column(name = "is_inactive", columnDefinition = "bit default false")
	private Boolean isInActive;

	@JsonBackReference
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "service_instance_id", insertable = true, updatable = true)
	private List<ServiceLibrary> serviceLibrary = new ArrayList<ServiceLibrary>();

	public boolean addServiceLibrary(ServiceLibrary e) {
		return this.serviceLibrary.add(e);
	}

}
