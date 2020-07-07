package com.zensar.service.library.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "service_library")
@ToString(exclude = "logoImage")
@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "serviceId")
public class ServiceLibrary {

	@Column(name = "service_id", length = 10)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long serviceId;

	@ManyToMany(mappedBy = "serviceLibrary", fetch = FetchType.LAZY, targetEntity = ServiceInstance.class)
	@JsonBackReference
	// @JsonSerialize(using = CustomListSerializer.class)
	// @JsonDeserialize(using = CustomListDeserializer.class)
	private Set<ServiceInstance> serviceInstance = new HashSet<ServiceInstance>();

	public boolean addServiceInstance(ServiceInstance instance) {
		return serviceInstance.add(instance);
	}

	@ManyToOne
	@JoinColumn(name = "super_category_id", insertable = true, updatable = true)
	@JsonBackReference
	private SuperCategory superCategory;

	@ManyToOne
	@JoinColumn(name = "sub_category_id", insertable = true, updatable = true)
	@JsonBackReference
	private SubCategory subCategory;

	@Column(name = "service_name", length = 100)
	private String serviceName;
	@Column(name = "type_of_service", length = 50)
	private String typeOfService;
	@Column(name = "service_decommisioned")
	private boolean serviceDecommisioned;
	@Column(name = "service_description", length = 200)
	private String serviceDescription;
	@Column(name = "creation_date")
	private LocalDateTime creationDate;
	@Column(name = "logo_image")
	@Lob
	private byte[] logoImage;
}
