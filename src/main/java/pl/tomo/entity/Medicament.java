package pl.tomo.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
public class Medicament {
	
	@Id
	@GeneratedValue
	private int id;
	
	private int liczba;
	
	private String name;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@Transient
	private String dateStringOpen;
	@Transient
	private String dateStringExpiration;
	@Transient
	private String dateStringEnd;
	
	@Temporal(TemporalType.DATE)
	private Date dateOpen;
	
	@Temporal(TemporalType.DATE)
	private Date dateExpiration;	
	
	@Temporal(TemporalType.DATE)
	private Date dateEnd;
	
	@ManyToOne
	private MedicamentDb medicamentDb;
	
	@ManyToMany(mappedBy="medicaments")
	private List<Disease> disease;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public MedicamentDb getMedicamentDb() {
		return medicamentDb;
	}

	public void setMedicamentDb(MedicamentDb medicamentDb) {
		this.medicamentDb = medicamentDb;
	}

	public int getLiczba() {
		return liczba;
	}

	public void setLiczba(int liczba) {
		this.liczba = liczba;
	}

	public Date getDateOpen() {
		return dateOpen;
	}

	public void setDateOpen(Date dateOpen) {
		this.dateOpen = dateOpen;
	}

	public Date getDateExpiration() {
		return dateExpiration;
	}

	public void setDateExpiration(Date dateExpiration) {
		this.dateExpiration = dateExpiration;
	}

	public Date getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}

	public String getDateStringOpen() {
		return dateStringOpen;
	}

	public void setDateStringOpen(String dateStringOpen) {
		this.dateStringOpen = dateStringOpen;
	}

	public String getDateStringExpiration() {
		return dateStringExpiration;
	}

	public void setDateStringExpiration(String dateStringExpiration) {
		this.dateStringExpiration = dateStringExpiration;
	}

	public String getDateStringEnd() {
		return dateStringEnd;
	}

	public void setDateStringEnd(String dateStringEnd) {
		this.dateStringEnd = dateStringEnd;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}




	
	
	
	

}
