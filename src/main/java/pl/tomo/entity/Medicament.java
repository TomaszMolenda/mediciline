package pl.tomo.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;

@Entity
public class Medicament {
	
	@Id
	@GeneratedValue
	private int id;
	
	@Min(1)
	@NotNull(message="Proszę wybrać lek")
	private Integer idMedicamentDb;
	
	private String name;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@Transient
	private String dateStringOpen;
	
	@Pattern(regexp = "^((((19|20)(([02468][048])|([13579][26]))-02-29))|((20[0-9][0-9])|(19[0-9][0-9]))-((((0[1-9])|(1[0-2]))-((0[1-9])|(1\\d)|(2[0-8])))|((((0[13578])|(1[02]))-31)|(((0[1,3-9])|(1[0-2]))-(29|30)))))$", message = "Błędna wartość daty")
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
	
	@ManyToMany(mappedBy="medicaments", fetch = FetchType.EAGER)
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

	public Integer getidMedicamentDb() {
		return idMedicamentDb;
	}

	public void setidMedicamentDb(Integer idMedicamentDb) {
		this.idMedicamentDb = idMedicamentDb;
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
