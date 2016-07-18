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
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedEntityGraphs;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.NamedSubgraph;
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

@Getter
@Setter
@Entity
@NamedQueries({
    @NamedQuery(name = "Patient.findAll", query = "SELECT p FROM Patient p")
})
@NamedEntityGraphs({
	@NamedEntityGraph(
	        name = "patient"
	    )
})
public class Patient {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;
	
	@Transient
	private int idServer;
	
	private String name;
	
	@Temporal(TemporalType.DATE)
	private Date birthday;
	
	@Transient
	@Getter(value = AccessLevel.NONE)
	private long birthdayLong;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private User user;
	
	@Setter(value = AccessLevel.NONE)
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "patient")
	private Set<Disease> diseases;

	public long getBirthdayLong() {
		if(this.birthdayLong == 0)
			return this.birthday.getTime();
		return birthdayLong;
	}

	@Override
	public String toString() {
		return "Patient [id=" + id + ", idServer=" + idServer + ", name=" + name + ", birthday=" + birthday
				+ ", birthdayLong=" + birthdayLong + "]";
	}

	
	
	
	
	
}
