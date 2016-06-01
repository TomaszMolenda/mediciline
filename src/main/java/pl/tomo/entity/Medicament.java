package pl.tomo.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
	
	private int productLineID;
	private int packageID;
	
	private int quantity;
	private String unit;
	
	@Transient
	private int idServer;
	
	
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
	//private List<Disease> disease;
	private Set<Disease> disease = new HashSet<Disease>();
	
	//http://stackoverflow.com/questions/1082095/how-to-remove-entity-with-manytomany-relationship-in-jpa-and-corresponding-join/14911910#14911910
	//https://github.com/fommil/zibaldone/blob/master/src/main/java/com/github/fommil/zibaldone/Note.java#L74
	@PreRemove
	private void preRemove()
	{
		for (Disease disease2 : disease) {
			Set<Medicament> medicaments = disease2.getMedicaments();
			medicaments.remove(this);
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

	public Set<Disease> getDisease() {
		return disease;
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

	public int getProductLineID() {
		return productLineID;
	}

	public void setProductLineID(int productLineID) {
		this.productLineID = productLineID;
	}
	

	public int getPackageID() {
		return packageID;
	}

	public void setPackageID(int packageID) {
		this.packageID = packageID;
	}
	
	

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	

	public int getIdServer() {
		return idServer;
	}

	public void setIdServer(int idServer) {
		this.idServer = idServer;
	}

	@Override
	public String toString() {
		return "Medicament [id=" + id + ", search=" + search + ", name=" + name + ", producent=" + producent
				+ ", price=" + price + ", kind=" + kind + ", productLineID=" + productLineID + ", user=" + user
				+ ", dateStringOpen=" + dateStringOpen + ", dateStringExpiration=" + dateStringExpiration
				+ ", dateStringEnd=" + dateStringEnd + ", dateOpen=" + dateOpen + ", dateExpiration=" + dateExpiration
				+ ", dateEnd=" + dateEnd + ", dateExpirationYearMonth=" + dateExpirationYearMonth + ", disease="
				+ disease + "]";
	}



	
	


	
	
	
	

}
