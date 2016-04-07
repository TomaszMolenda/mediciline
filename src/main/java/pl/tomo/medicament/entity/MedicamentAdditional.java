package pl.tomo.medicament.entity;

import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class MedicamentAdditional {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
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
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getProductLineID() {
		return productLineID;
	}
	public void setProductLineID(int productLineID) {
		this.productLineID = productLineID;
	}
	public String getComposition() {
		return composition;
	}
	public void setComposition(String composition) {
		this.composition = composition;
	}
	public String getEffects() {
		return effects;
	}
	public void setEffects(String effects) {
		this.effects = effects;
	}
	public String getIndications() {
		return indications;
	}
	public void setIndications(String indications) {
		this.indications = indications;
	}
	public String getContraindications() {
		return contraindications;
	}
	public void setContraindications(String contraindications) {
		this.contraindications = contraindications;
	}
	public String getPrecaution() {
		return precaution;
	}
	public void setPrecaution(String precaution) {
		this.precaution = precaution;
	}
	public String getPregnancy() {
		return pregnancy;
	}
	public void setPregnancy(String pregnancy) {
		this.pregnancy = pregnancy;
	}
	public String getSideeffects() {
		return sideeffects;
	}
	public void setSideeffects(String sideeffects) {
		this.sideeffects = sideeffects;
	}
	public String getInteractions() {
		return interactions;
	}
	public void setInteractions(String interactions) {
		this.interactions = interactions;
	}
	public String getDosage() {
		return dosage;
	}
	public void setDosage(String dosage) {
		this.dosage = dosage;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Override
	public String toString() {
		return "MedicamentAdditional [id=" + id + ", productLineID=" + productLineID + ", composition=" + composition
				+ ", effects=" + effects + ", indications=" + indications + ", contraindications=" + contraindications
				+ ", precaution=" + precaution + ", pregnancy=" + pregnancy + ", sideeffects=" + sideeffects
				+ ", interactions=" + interactions + ", dosage=" + dosage + ", remark=" + remark + "]";
	}
	
	
	
	
	
	
}
