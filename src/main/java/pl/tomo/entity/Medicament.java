package pl.tomo.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PreRemove;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
public class Medicament {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String search;
	
	@NotNull(message="Proszę wybrać lek")
	private String name;
	private String producent;
	private double price;
	private String kind;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
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
	
	@Transient
	private DateExpirationYearMonth dateExpirationYearMonth;
	
	@ManyToMany(mappedBy="medicaments", fetch=FetchType.LAZY)
	private List<Disease> disease;
	
	
	//http://stackoverflow.com/questions/1082095/how-to-remove-entity-with-manytomany-relationship-in-jpa-and-corresponding-join/14911910#14911910
	//https://github.com/fommil/zibaldone/blob/master/src/main/java/com/github/fommil/zibaldone/Note.java#L74
	@PreRemove
	private void removeMedicamentFromDiseases()
	{
		for (Disease disease2 : disease) {
			disease2.getMedicaments().remove(this);
		}
	}

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

	public List<Disease> getDisease() {
		return disease;
	}

	public void setDisease(List<Disease> disease) {
		this.disease = disease;
	}

	public DateExpirationYearMonth getDateExpirationYearMonth() {
		return dateExpirationYearMonth;
	}

	public void setDateExpirationYearMonth(DateExpirationYearMonth dateExpirationYearMonth) {
		this.dateExpirationYearMonth = dateExpirationYearMonth;
	}

	public String getProducent() {
		return producent;
	}

	public void setProducent(String producent) {
		this.producent = producent;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	@Override
	public String toString() {
		return "Medicament [id=" + id + ", search=" + search + ", name=" + name + ", producent=" + producent
				+ ", price=" + price + ", kind=" + kind + ", dateStringOpen=" + dateStringOpen
				+ ", dateStringExpiration=" + dateStringExpiration + ", dateStringEnd=" + dateStringEnd + ", dateOpen="
				+ dateOpen + ", dateExpiration=" + dateExpiration + ", dateEnd=" + dateEnd
				+ ", dateExpirationYearMonth=" + dateExpirationYearMonth + "]";
	}

	
	


	
	
	
	

}
