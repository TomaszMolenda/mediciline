package pl.tomo.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;
import javax.persistence.PreRemove;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import pl.tomo.utill.date.DateConverter;

@Entity
@Getter
@Setter
public class Medicament {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String search;
	
	private String name;
	
	private String producent;
	
	private double price;
	
	private String kind;
	
	private int productLineID;
	
	private int packageID;
	
	private int quantity;
	
	private String unit;
	
	@Type(type="true_false")
	private boolean archive;
	
	@Transient
	private int idServer;
	
	@Transient
	private long date;
	
	@Transient String sDate;
	
	@Temporal(TemporalType.DATE)
	private Date dateExpiration;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;
	
	@Setter(value = AccessLevel.NONE)
	@ManyToMany(mappedBy="medicaments", fetch=FetchType.LAZY)
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
	
	//http://stackoverflow.com/a/37341652/5753094
	@PostLoad
    public void prepare(){
		if(dateExpiration != null) {
			date = dateExpiration.getTime();
			sDate = DateConverter.longToMonthYear(date);
		}
		idServer = id;
		
    }
	
	public long setDate() {
		dateExpiration = new Date(date);
		return date;
	}

	public void prepareDosage() {
		if(!kind.equals("")) {
			Dosage dosage = new Dosage(kind);
			quantity = dosage.getWholePackage();
			unit = dosage.getUnit();
		}
	}

	
}
