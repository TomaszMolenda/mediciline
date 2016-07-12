package pl.tomo.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Transient;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
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
	
	@Setter(value = AccessLevel.NONE)
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, orphanRemoval = true)
	@OrderBy("id ASC")
	private SortedSet<Medicament> medicaments;
	
	@Setter(value = AccessLevel.NONE)
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, orphanRemoval = true)
	private Set<Disease> diseases;
	
	@Setter(value = AccessLevel.NONE)
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, orphanRemoval = true)
	private Set<Patient> patients;
	
	@Setter(value = AccessLevel.NONE)
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable
	private Set<Role> roles = new HashSet<Role>();
	
	@Setter(value = AccessLevel.NONE)
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, orphanRemoval = true)
	private Set<File> files;
	
	@Setter(value = AccessLevel.NONE)
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

}
