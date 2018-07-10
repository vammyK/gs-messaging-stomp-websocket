package org.siemens.mindverse.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.*;

import org.hibernate.annotations.NaturalId;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;

@Entity
@Table(name = "asset")
@EntityListeners(AuditingEntityListener.class)
@Data
public class Asset {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id;
	

	private String name;
	private Double price;
	private String url;
	
	private String onboardedBy;
	private String status;
	private Date onboardedOn;
	
	private Boolean isOnboarded;
	
	private String latitude;
	private String longitude;

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "aspect_assets", joinColumns = { @JoinColumn(name = "asset_id") }, inverseJoinColumns = {
			@JoinColumn(name = "aspect_id") })
	Set<Aspect> aspect;

}
