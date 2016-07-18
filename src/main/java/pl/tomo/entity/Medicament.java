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
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedEntityGraphs;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PostLoad;
import javax.persistence.PreRemove;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.tomo.utill.DateConverter;

@Entity
@Getter
@Setter
@NamedQueries({
    @NamedQuery(name = "Medicament.findById", query = "SELECT m FROM Medicament m WHERE m.id = :id"),
    @NamedQuery(name = "Medicament.findAllByUser", query = "SELECT m FROM Medicament m WHERE m.user = :user"),
    @NamedQuery(name = "Medicament.findAllByArchiveAndUser", query = "SELECT m FROM Medicament m WHERE m.archive = :archive AND m.user = :user")
})
@NamedEntityGraphs({
	@NamedEntityGraph(
	        name = "medicament"
	    ),
    @NamedEntityGraph(
        name = "medicamentWithUserAndDiseases",
	        attributeNodes = {
	        		@NamedAttributeNode("user"),
	        		@NamedAttributeNode("disease")
            }
    ),
    @NamedEntityGraph(
            name = "medicamentWithUser",
    	        attributeNodes = {
    	        		@NamedAttributeNode("user"),
                }
        )
})
public class Medicament implements Comparable<Medicament>{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String search;
	
	private String name;
	
	private String producent;
	
	private double price;
	
	private String pack;
	
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
		if(!pack.equals("")) {
			Dosage dosage = new Dosage(pack);
			quantity = dosage.getWholePackage();
			unit = dosage.getUnit();
		}
	}

	@Override
	public int compareTo(Medicament m) {
		if (m == null) {return 1;}
		return this.id > m.id ? 1 : 
            this.id < m.id ? -1 : 0;
	}

	
}
