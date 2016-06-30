package pl.tomo.entity;

import java.util.Date;
import java.util.Set;

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
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@NamedQueries({
    @NamedQuery(name = "Disease.findAll", query = "SELECT d FROM Disease d")
})
@NamedEntityGraphs({
    @NamedEntityGraph(
        name = "diseaseAndUser",
        attributeNodes = {
            @NamedAttributeNode("user")
        }
    ),
    @NamedEntityGraph(
            name = "diseaseAndUserWithMedicaments",
            attributeNodes = {
                @NamedAttributeNode(value = "user", subgraph = "userGraph")
            },
            subgraphs = {
                @NamedSubgraph(
                        name = "userGraph",
                        attributeNodes = {
                            @NamedAttributeNode("medicaments")
                        }
                )
            }
        )
})
public class Disease {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
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
	
	@Setter(value = AccessLevel.NONE)
	@ManyToMany(fetch = FetchType.LAZY)
	private Set<Medicament> medicaments;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Patient patient;
	
	@Setter(value = AccessLevel.NONE)
	@OneToMany(mappedBy = "disease", fetch = FetchType.LAZY, orphanRemoval = true)
	private Set<File> files;
	
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
	@Override
	public String toString() {
		return "Disease [id=" + id + ", name=" + name + ", description=" + description + ", start=" + start + ", stop="
				+ stop + ", startLong=" + startLong + ", stopLong=" + stopLong + ", startString=" + startString
				+ ", stopString=" + stopString + ", archive=" + archive + "]";
	}
	
}
