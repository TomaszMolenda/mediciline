package pl.tomo.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@ToString
public class Patient {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;
	
	@NotNull(message = "Problem z imieniem")
	@Size(min = 1, max = 20, message = "Problem z imieniem (maksymalnie 20 znaków")
	private String name;
	
	@Temporal(TemporalType.DATE)
	private Date birthday;
	
	@Transient
	@Min(value = 1, message = "Problem z datą")
	private long birthdayLong;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private User user;
	
	@Setter(value = AccessLevel.NONE)
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "patient")
	private Set<Disease> diseases;

	
	
	
	
	
}
