package pl.tomo.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedEntityGraphs;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PostLoad;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import pl.tomo.utill.DateConverter;

@Entity
@Getter
@Setter
@NamedQueries({
    @NamedQuery(name = "Medicament.findById", query = "SELECT m FROM Medicament m WHERE m.id = :id"),
    @NamedQuery(name = "Medicament.findByIds", query = "SELECT m FROM Medicament m WHERE m.id IN :ids"),
    @NamedQuery(name = "Medicament.findAllByUser", query = "SELECT m FROM Medicament m WHERE m.user = :user"),
    @NamedQuery(name = "Medicament.findAllByArchiveAndUser", query = "SELECT m FROM Medicament m WHERE m.archive = :archive AND m.user = :user"),
    @NamedQuery(name = "Medicament.findAllByArchiveAndOverdueAndUser", query = "SELECT m FROM Medicament m WHERE m.archive = :archive AND m.overdue = :overdue AND m.user = :user")
})
@NamedEntityGraphs({
	@NamedEntityGraph(
	        name = "medicament"
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
	
	@Type(type="true_false")
	private boolean overdue;
	
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
	@OneToMany(mappedBy = "medicament", fetch = FetchType.LAZY, orphanRemoval = true)
	private Set<DiseaseMedicament> diseaseMedicaments;
	
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
	
	@Override
	public int hashCode() {
		int result = 17;
		int multipler = 31;

		result = multipler * result + id;
		
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
	    if (obj == this) return true;
	    if (!(obj instanceof Medicament))return false;
	    Medicament medicament = (Medicament)obj;
	    if(this.hashCode() == medicament.hashCode()) return true;
	    else return false;
	}

	@Override
	public String toString() {
		return "Medicament [id=" + id + ", name=" + name + "]";
	}

	
	
	

	
}
