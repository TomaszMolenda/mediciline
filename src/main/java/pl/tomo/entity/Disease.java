package pl.tomo.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Disease {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String name;
	
	@Column(columnDefinition="TEXT")
	private String description;
	
	@Temporal(TemporalType.DATE)
	private Date start;
	
	@Temporal(TemporalType.DATE)
	private Date stop;
	
	@Transient
	private String startString;
	
	@Transient
	private String stopString;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	private User user;
	
	@Setter(value = AccessLevel.NONE)
	@ManyToMany(fetch = FetchType.LAZY)
	private Set<Medicament> medicaments;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Patient patient;
	
	@Setter(value = AccessLevel.NONE)
	@OneToMany(mappedBy = "disease", fetch = FetchType.LAZY, orphanRemoval = true)
	private Set<File> files;

}
