package pl.tomo.entity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PreRemove;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;

import pl.tomo.service.PatientService;

@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Size(min = 4, message = "Nazwa musi zawierać min 4 znaków")
	private String name;
	
	@Email(message = "Wpisz poprawny e-mail")
	@Pattern(regexp=".+@.+\\.[a-z]+", message = "Nieprawidłowy format")
	@NotEmpty(message = "E-mail musi coś zawierać")
	private String email;
	
	@Size(min = 4, message = "Hasło musi mieć min 4 znaków")
	private String password;
	
	private String uniqueID;
	
	private boolean active = false;
	
	private int demoNo;
	
	@Transient
	private String confirmPassword;
	
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER, orphanRemoval = true)
	private List<Medicament> medicaments;
	
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER, orphanRemoval = true)
	private List<Disease> diseases;
	
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER, orphanRemoval = true)
	private List<Patient> patients;
	
	@ManyToMany
	private Set<Role> roles = new HashSet<Role>();

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

	public List<Medicament> getMedicaments() {
		return medicaments;
	}

	public void setMedicaments(List<Medicament> medicaments) {
		this.medicaments = medicaments;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	

	public String getUniqueID() {
		return uniqueID;
	}

	public void setUniqueID(String uniqueID) {
		this.uniqueID = uniqueID;
	}
	

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	public List<Disease> getDiseases() {
		return diseases;
	}

	public void setDiseases(List<Disease> diseases) {
		this.diseases = diseases;
	}

	public List<Patient> getPatients() {
		return patients;
	}

	public void setPatients(List<Patient> patients) {
		this.patients = patients;
	}

	public int getDemoNo() {
		return demoNo;
	}

	public void setDemoNo(int demoNo) {
		this.demoNo = demoNo;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + "]";
	}
	
	
	

}
