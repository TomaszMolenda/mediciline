package pl.tomo.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
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
	
	@ManyToMany(fetch = FetchType.LAZY)
	private List<Medicament> medicaments;
	
	@Transient
	private List<Medicament> medicaments2;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Patient patient;
	
	public Disease() {
		medicaments2 = new ArrayList<Medicament>();
	}
	
	public void addMedicament(Medicament medicament) {
		medicaments2.add(medicament);
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getStop() {
		return stop;
	}

	public void setStop(Date stop) {
		this.stop = stop;
	}

	public String getStartString() {
		return startString;
	}

	public void setStartString(String startString) {
		this.startString = startString;
	}

	public String getStopString() {
		return stopString;
	}

	public void setStopString(String stopString) {
		this.stopString = stopString;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Medicament> getMedicaments() {
		return medicaments;
	}

	public void setMedicaments(List<Medicament> medicaments) {
		this.medicaments = medicaments;
	}
	

	public List<Medicament> getMedicaments2() {
		return medicaments2;
	}

	public void setMedicaments2(List<Medicament> medicaments2) {
		this.medicaments2 = medicaments2;
	}
	

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	@Override
	public String toString() {
		return "Disease [id=" + id + ", name=" + name + ", description=" + description + ", start=" + start + ", stop="
				+ stop + ", startString=" + startString + ", stopString=" + stopString + "]";
	}
	
	


	
	
	
	
	
	
	
	
	

}
