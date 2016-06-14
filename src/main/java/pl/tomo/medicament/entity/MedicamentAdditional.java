package pl.tomo.medicament.entity;

import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class MedicamentAdditional {

	@OneToMany(mappedBy = "medicamentAdditional")
	@Setter(value = AccessLevel.NONE)
	private Set<Medicament> medicaments;
	
	@Id
	private int productLineID;
	
	@Column(columnDefinition="TEXT")
	private String composition;
	
	@Column(columnDefinition="TEXT")
	private String effects;
	
	@Column(columnDefinition="TEXT")
	private String indications;
	
	@Column(columnDefinition="TEXT")
	private String contraindications;
	
	@Column(columnDefinition="TEXT")
	private String precaution;
	
	@Column(columnDefinition="TEXT")
	private String pregnancy;
	
	@Column(columnDefinition="TEXT")
	private String sideeffects;
	
	@Column(columnDefinition="TEXT")
	private String interactions;
	
	@Column(columnDefinition="TEXT")
	private String dosage;
	
	@Column(columnDefinition="TEXT")
	private String remark;
	
	public MedicamentAdditional() {
	}

	public MedicamentAdditional(Map<String, String> information) {
		productLineID = Integer.parseInt(information.get("id"));
		composition = information.get("composition");
		effects = information.get("effects");
		indications = information.get("indications");
		contraindications = information.get("contraindications");
		precaution = information.get("precaution");
		pregnancy = information.get("pregnancy");
		sideeffects = information.get("sideeffects");
		interactions = information.get("interactions");
		dosage = information.get("dosage");
		remark = information.get("remark");
	}
}
