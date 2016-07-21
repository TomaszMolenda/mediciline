package pl.tomo.entity;

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
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "disease_medicament")
@Getter
@Setter
@NamedQueries({
    @NamedQuery(name = "DiseaseMedicament.findByMedicament", query = "SELECT dm FROM DiseaseMedicament dm WHERE dm.medicament = :medicament"),
    @NamedQuery(name = "DiseaseMedicament.findByMedicamentsId", 
    	query = "SELECT dm FROM DiseaseMedicament dm JOIN FETCH dm.medicament WHERE dm.medicament.id IN :ids"),
    @NamedQuery(name = "DiseaseMedicament.findByDisease", query = "SELECT dm FROM DiseaseMedicament dm WHERE dm.disease = :disease"),
    @NamedQuery(name = "DiseaseMedicament.findByDiseaseAndMedicament", 
    	query = "SELECT dm FROM DiseaseMedicament dm WHERE dm.disease = :disease AND dm.medicament = :medicament")
	})
@NamedEntityGraphs({
    @NamedEntityGraph(
        name = "medicamentsAndDisease",
	        attributeNodes = {
	        		@NamedAttributeNode("medicament"),
	        		@NamedAttributeNode("disease")
            }
    ),
    @NamedEntityGraph(
            name = "medicaments",
    	        attributeNodes = {
    	        		@NamedAttributeNode("medicament")
                }
    ),
    @NamedEntityGraph(
            name = "diseases",
    	        attributeNodes = {
    	        		@NamedAttributeNode("disease")
                }
        )
})
public class DiseaseMedicament {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Disease disease;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Medicament medicament;
	
	@Override
	public int hashCode() {
		int result = 17;
		int multipler = 31;
		result = multipler * result + (medicament == null ? 0 : medicament.hashCode());
		result = multipler * result + (disease == null ? 0 : disease.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
	    if (obj == this) return true;
	    if (!(obj instanceof DiseaseMedicament))return false;
	    DiseaseMedicament diseaseMedicament = (DiseaseMedicament)obj;
	    if(this.hashCode() == diseaseMedicament.hashCode()) return true;
	    else return false;
	}

}
