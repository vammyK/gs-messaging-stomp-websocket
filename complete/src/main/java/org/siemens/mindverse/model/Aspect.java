package org.siemens.mindverse.model;

import java.util.Set;

import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "aspect")
@Data
public class Aspect {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	String name;
	Boolean isonboarded;
	
	String value;
	



}
