package pl.tomo.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import pl.tomo.entity.Medicament;

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
	
	private Date date = new Date();
	
	@Transient
	private String confirmPassword;
	
	private String JSESSIONID;
	
	private String auth;
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, orphanRemoval = true)
	private Set<Medicament> medicaments;
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, orphanRemoval = true)
	private Set<Disease> diseases;
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, orphanRemoval = true)
	private Set<Patient> patients;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable
	private Set<Role> roles = new HashSet<Role>();
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, orphanRemoval = true)
	private Set<File> files;
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, orphanRemoval = true)
	private Set<Dosage> dosages;

	@Override
	public int hashCode() {
		int result = 17;
		int multipler = 31;
		result = multipler * result + id;
		result = multipler * result + (name == null ? 0 : name.hashCode());
		result = multipler * result + (email == null ? 0 : email.hashCode());
		result = multipler * result + (auth == null ? 0 : auth.hashCode());

		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
	    if (obj == this) return true;
	    if (!(obj instanceof User))return false;
	    User user = (User)obj;
	    if(this.hashCode() == user.hashCode()) return true;
	    else return false;
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

	public Set<Medicament> getMedicaments() {
		return medicaments;
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
	
	public Set<Disease> getDiseases() {
		return diseases;
	}


	public Set<Patient> getPatients() {
		return patients;
	}

	public int getDemoNo() {
		return demoNo;
	}

	public void setDemoNo(int demoNo) {
		this.demoNo = demoNo;
	}
	

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	

	public Set<File> getFiles() {
		return files;
	}
	
	

	public String getJSESSIONID() {
		return JSESSIONID;
	}

	public void setJSESSIONID(String jSESSIONID) {
		JSESSIONID = jSESSIONID;
	}


	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}
	
	

	public Set<Dosage> getDosages() {
		return dosages;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", uniqueID="
				+ uniqueID + ", active=" + active + ", demoNo=" + demoNo + ", date=" + date + ", confirmPassword="
				+ confirmPassword + ", medicaments=" + medicaments + ", diseases=" + diseases + ", patients=" + patients
				+ ", roles=" + roles + "]";
	}
	
	public String toLogger() {
		return "User [id=" + id + ", name=" + name + ", email=" + email + ", uniqueID="
				+ uniqueID + ", active=" + active + ", demoNo=" + demoNo + ", date=" + date + ", roles=" + roles + "]";
	}

	public void addRoles(Role role) {
		roles.add(role);
		
	}
	

	
	
	

}
