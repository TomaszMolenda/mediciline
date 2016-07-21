package pl.tomo.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedEntityGraphs;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.NamedSubgraph;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import pl.tomo.entity.Medicament;

@Entity
@Getter
@Setter
@NamedQueries({
    @NamedQuery(name = "Disease.findAll", query = "SELECT d FROM Disease d"),
    @NamedQuery(name = "Disease.findById", query = "SELECT d FROM Disease d WHERE d.id = :id"),
    @NamedQuery(name = "Disease.findAllByPatient", query = "SELECT d FROM Disease d WHERE d.patient = :patient"),
    @NamedQuery(name = "Disease.findAllByPatientAndActive", query = "SELECT d FROM Disease d WHERE d.patient = :patient AND d.archive = :archive")
})
@NamedEntityGraphs({
	@NamedEntityGraph(
	        name = "disease"
	    ),
    @NamedEntityGraph(
        name = "diseaseAndUser",
        attributeNodes = {
            @NamedAttributeNode("user")
        }
    ),
    @NamedEntityGraph(
            name = "diseaseAndFilesUser",
            attributeNodes = {
                @NamedAttributeNode("files"),
                @NamedAttributeNode("user")
            }
        ),
    @NamedEntityGraph(
            name = "diseaseAndUserWithMedicaments",
            attributeNodes = {
                @NamedAttributeNode(value = "user", subgraph = "userGraph"),
                @NamedAttributeNode("files")
            },
            subgraphs = {
                @NamedSubgraph(
                        name = "userGraph",
                        attributeNodes = {
                            @NamedAttributeNode(value = "medicaments")
                        }
                        
                )
            }
    )
})
public class Disease {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Transient
	private int idServer;
	
	private String name;
	
	@Column(columnDefinition="TEXT")
	private String description;
	
	@Temporal(TemporalType.DATE)
	private Date start;
	
	@Temporal(TemporalType.DATE)
	private Date stop;
	
	private long startLong;
	
	private long stopLong;
	
	@Transient
	private String startString;
	
	@Transient
	private String stopString;
	
	@Type(type="true_false")
	private boolean archive;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private User user;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Patient patient;
	
	@Setter(value = AccessLevel.NONE)
	@OneToMany(mappedBy = "disease", fetch = FetchType.LAZY, orphanRemoval = true)
	@OrderBy("id ASC")
	private SortedSet<File> files;
	
	@Setter(value = AccessLevel.NONE)
	@OneToMany(mappedBy = "disease", fetch = FetchType.LAZY, orphanRemoval = true)
	private Set<DiseaseMedicament> diseaseMedicaments;
	
	@PrePersist
	public void prePersist() {
		if(startLong != 0)
			start = new Date(startLong);
	}
	@PreUpdate
	public void PreUpdate() {
		if(stopLong != 0)
			stop = new Date(stopLong);
	}
	
	public List<Medicament> getMedicamentsId2() {
		List<Medicament> list = new ArrayList<Medicament>();
		for (DiseaseMedicament diseaseMedicament : diseaseMedicaments) {
			list.add(diseaseMedicament.getMedicament());
		}
		return list;
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
	    if (!(obj instanceof Disease))return false;
	    Disease disease = (Disease)obj;
	    if(this.hashCode() == disease.hashCode()) return true;
	    else return false;
	}
	
	@Override
	public String toString() {
		return "Disease [id=" + id + ", idServer=" + idServer + ", name=" + name + ", description=" + description
				+ ", start=" + start + ", stop=" + stop + ", startLong=" + startLong + ", stopLong=" + stopLong
				+ ", startString=" + startString + ", stopString=" + stopString + ", archive=" + archive + "]";
	}
	
	
	
}
