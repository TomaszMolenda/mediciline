package pl.tomo.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedEntityGraphs;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.NamedSubgraph;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Getter;
import lombok.Setter;
import pl.tomo.provider.json.deserialize.TimeDeserializer;

@Getter
@Setter
@Entity
@NamedQueries({
    @NamedQuery(name = "Dosage.findById", query = "SELECT d FROM Dosage d WHERE d.id = :id"),
    @NamedQuery(name = "Dosage.findAddNotSended", query = "SELECT d FROM Dosage d WHERE d.sended = false")
})
@NamedEntityGraphs({
    @NamedEntityGraph(
        name = "dosageWithUser",
	        attributeNodes = {
	        		@NamedAttributeNode("user"),
            }
    ),
    @NamedEntityGraph(
            name = "withDiseaseMedicamentDetails",
    	        attributeNodes = {
    	        		@NamedAttributeNode(value = "diseaseMedicament", subgraph = "diseaseMedicamentGraph"),
                },
        		subgraphs = {
                        @NamedSubgraph(
                                name = "diseaseMedicamentGraph",
                                attributeNodes = {
                                    @NamedAttributeNode(value = "medicament"),
                                    @NamedAttributeNode(value = "disease")
                                }
                                
                        )
                    }
    	       
        )
})
public class Dosage implements Comparable<Dosage>{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Transient
	private int idServer;
	
	private int idD;

	private int idM;
	
	@Transient
	private Disease disease;
	
	@Transient
	private Medicament medicament;
		
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;
	
	@Temporal(TemporalType.TIME)
	@JsonDeserialize(using = TimeDeserializer.class)
	private Date takeTime;
	
	private int wholePackage;
	
	private String unit;
	
	private int dose;
	
	private boolean sended;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private User user;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private DiseaseMedicament diseaseMedicament;

	public Dosage() {
	}

	public Dosage(String kind) {
		kind = kind.replaceAll("\\s+", " ").trim();
		int firstSpace = 0;
		if(kind.contains(" ")) {
			firstSpace = kind.indexOf(" ");
			String substring = kind.substring(0, firstSpace++);
			if(isInteger(substring)) {
				this.wholePackage = Integer.parseInt(substring);
				this.unit = kind.substring(firstSpace, kind.length());
			}
			else {
				if(kind.substring(firstSpace, kind.length()).contains(" ")) {
					int secondSpace = kind.indexOf(" ", firstSpace);
					substring = kind.substring(firstSpace, secondSpace++);
					if(isInteger(substring)) {
						this.wholePackage = Integer.parseInt(substring);
						this.unit = kind.substring(secondSpace, kind.length()) +  " - " + kind.substring(0, firstSpace);
					}
					else {
						this.wholePackage = 1;
						this.unit = kind;
					}
				}
				else {
					this.wholePackage = 1;
					this.unit = kind;
				}
			}
		}
		else {
			this.wholePackage = 1;
			this.unit = kind;
		}
	}

	private static boolean isInteger(String s) {
	    try { 
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    } catch(NullPointerException e) {
	        return false;
	    }
	    return true;
	}
	
	@PrePersist
	public void prePersist(){
		createDate = new Date();
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
	    if (!(obj instanceof Dosage))return false;
	    Dosage dosage = (Dosage)obj;
	    if(this.hashCode() == dosage.hashCode()) return true;
	    else return false;
	}

	@Override
	public int compareTo(Dosage o) {
		if (o == null) {return 1;}
		return this.takeTime.after(o.takeTime) ? 1 : 
            this.takeTime.before(o.takeTime) ? -1 : 0;
	}

	@Override
	public String toString() {
		return "Dosage [id=" + id + "]";
	}
	
	
}
