package pl.tomo.medicament.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedEntityGraphs;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.NamedSubgraph;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.owlike.genson.annotation.JsonIgnore;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.tomo.entity.Dosage;

@Entity
@Getter
@Setter
@ToString
@NamedQueries({
    @NamedQuery(name = "Medicament.findAll", query = "SELECT d FROM Medicament d"),
})
@NamedEntityGraphs({
    @NamedEntityGraph(
        name = "medicament",
        attributeNodes = {
            @NamedAttributeNode("medicamentAdditional")
        }
    )
})
public class Medicament {
	
	@Id
	@JsonProperty(value = "PackageID")
	private int packageID;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private MedicamentAdditional medicamentAdditional;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@Setter(value = AccessLevel.NONE)
	private Set<ATC> atcs = new HashSet<ATC>();	
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Distributor distributor;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private ProductType productType;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Prescription prescription;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@Setter(value = AccessLevel.NONE)
	private Set<Disease> diseases = new HashSet<Disease>();	
	
	@Transient
	private Dosage dosageObject; 
	
	@JsonProperty(value = "ActiveSubstance")
	private String activeSubstance;
	
	@JsonProperty(value = "DistributorID")
	private int distributorID;
	
	@JsonProperty(value = "Dosage")
	private String dosage;
	
	@JsonProperty(value = "Driving")
	private int driving;
	
	@JsonProperty(value = "DrivingInfo")
	@Column(columnDefinition="TEXT")
	private String drivingInfo;
	
	@JsonProperty(value = "DrugCardLimit")
	private int drugCardLimit;
	
	@JsonProperty(value = "DrugPromoID")
	private int drugPromoID;
	
	@JsonProperty(value = "EAN")
	private String ean;
	
	@JsonProperty(value = "FinalSort")
	private int finalSort;
	
	@JsonProperty(value = "Form")
	private String form;
	
	@JsonProperty(value = "IsAlco")
	private int isAlco;
	
	@JsonProperty(value = "IsAlcoInfo")
	@Column(columnDefinition="TEXT")
	private String isAlcoInfo;
	
	@JsonProperty(value = "IsNarcPsych")
	private int isNarcPsych;
	
	@JsonProperty(value = "IsNarcPsychIcon")
	@Column(columnDefinition="TEXT")
	private String isNarcPsychInfo;
	
	@JsonProperty(value = "IsReimbursed")
	private int isReimbursed;
	
	@JsonProperty(value = "Lactatio")
	private int lactatio;
	
	@JsonProperty(value = "LactatioInfo")
	@Column(columnDefinition="TEXT")
	private String lactatioInfo;
	
	@JsonProperty(value = "Pack")
	private String pack;
	
	@JsonProperty(value = "Pregnancy")
	private int pregnancy;
	
	@JsonProperty(value = "PregnancyInfo")
	@Column(columnDefinition="TEXT")
	private String pregnancyInfo;
	
	@JsonProperty(value = "PrescriptionID")
	private int prescriptionID;
	
	@JsonProperty(value = "PrescriptionName")
	private String prescriptionName;
	
	@JsonProperty(value = "PrescriptionShortName")
	private String prescriptionShortName;
	
	@JsonProperty(value = "Price")
	private double price;
	
	@JsonProperty(value = "Producer")
	private String producer;
	
	@JsonProperty(value = "ProducerID")
	private int producerID;
	
	@JsonProperty(value = "ProductID")
	private int productID;
	
	@JsonProperty(value = "ProductLineID")
	private int productLineID;
	
	@JsonProperty(value = "ProductLineName")
	private String productLineName;
	
	@JsonProperty(value = "ProductName")
	private String productName;
	
	@JsonProperty(value = "ProductTypeID")
	private int productTypeID;
	
	@JsonProperty(value = "ProductTypeName")
	private String productTypeName;
	
	@JsonProperty(value = "ProductTypeShortName")
	private String productTypeShortName;
	
	@JsonProperty(value = "RegNo")
	private String regNo;
	
	@JsonProperty(value = "SponsorID")
	private int sponsorID;
	
	@JsonProperty(value = "TherapeuticClass")
	private String therapeuticClass;
	
	@JsonProperty(value = "Trimester1")
	private int trimester1;
	
	@JsonProperty(value = "Trimester1Info")
	private String trimester1Info;
	
	@JsonProperty(value = "Trimester2")
	private int trimester2;
	
	@JsonProperty(value = "Trimester2Info")
	private String trimester2Info;
	
	@JsonProperty(value = "Trimester3")
	private int trimester3;
	
	@JsonProperty(value = "Trimester3Info")
	private String trimester3Info;
	
	@JsonProperty(value = "isFavorite")
	private int isFavorite;
	
	@JsonProperty(value = "oi")
	private int oi;
	
	@Type(type="true_false")
	private boolean active;
	
	public Medicament() {
		active = true;
	}
	
	@Override
	public int hashCode() {
		int result = 17;
		int multipler = 31;
		//double
		long priceBits = Double.doubleToLongBits(price);

		result = multipler * result + (int)(priceBits ^ (priceBits >>> 32));
		//int
		result = multipler * result + distributorID;
		result = multipler * result + driving;
		result = multipler * result + drugCardLimit;
		result = multipler * result + drugPromoID;
		result = multipler * result + finalSort;
		result = multipler * result + isAlco;
		result = multipler * result + isFavorite;
		result = multipler * result + isNarcPsych;
		result = multipler * result + isReimbursed;
		result = multipler * result + lactatio;
		result = multipler * result + oi;
		result = multipler * result + packageID;
		result = multipler * result + pregnancy;
		result = multipler * result + prescriptionID;
		result = multipler * result + producerID;
		result = multipler * result + productID;
		result = multipler * result + productLineID;
		result = multipler * result + productTypeID;
		result = multipler * result + sponsorID;
		result = multipler * result + trimester1;
		result = multipler * result + trimester2;
		result = multipler * result + trimester3;
		//string
		result = multipler * result + (activeSubstance == null ? 0 : activeSubstance.hashCode());
//		result = multipler * result + (distributor22 == null ? 0 : distributor22.hashCode());
		result = multipler * result + (dosage == null ? 0 : dosage.hashCode());
		result = multipler * result + (drivingInfo == null ? 0 : drivingInfo.hashCode());
		result = multipler * result + (ean == null ? 0 : ean.hashCode());
		result = multipler * result + (form == null ? 0 : form.hashCode());
		result = multipler * result + (isAlcoInfo == null ? 0 : isAlcoInfo.hashCode());
		result = multipler * result + (isNarcPsychInfo == null ? 0 : isNarcPsychInfo.hashCode());
		result = multipler * result + (lactatioInfo == null ? 0 : lactatioInfo.hashCode());
		result = multipler * result + (pack == null ? 0 : pack.hashCode());
		result = multipler * result + (pregnancyInfo == null ? 0 : pregnancyInfo.hashCode());
		result = multipler * result + (prescriptionName == null ? 0 : prescriptionName.hashCode());
		result = multipler * result + (prescriptionShortName == null ? 0 : prescriptionShortName.hashCode());
		result = multipler * result + (producer == null ? 0 : producer.hashCode());
		result = multipler * result + (productLineName == null ? 0 : productLineName.hashCode());
		result = multipler * result + (productName == null ? 0 : productName.hashCode());
		result = multipler * result + (productTypeName == null ? 0 : productTypeName.hashCode());
		result = multipler * result + (productTypeShortName == null ? 0 : productTypeShortName.hashCode());
		result = multipler * result + (regNo == null ? 0 : regNo.hashCode());
		result = multipler * result + (therapeuticClass == null ? 0 : therapeuticClass.hashCode());
		result = multipler * result + (trimester1Info == null ? 0 : trimester1Info.hashCode());
		result = multipler * result + (trimester2Info == null ? 0 : trimester2Info.hashCode());
		result = multipler * result + (trimester3Info == null ? 0 : trimester3Info.hashCode());

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

	@JsonIgnore
	public String getActiveSubstance() {
		return activeSubstance;
	}
	public void setActiveSubstance(String activeSubstance) {
		this.activeSubstance = activeSubstance;
	}
	@JsonIgnore
	public int getDistributorID() {
		return distributorID;
	}
	public void setDistributorID(int distributorID) {
		this.distributorID = distributorID;
	}
	@JsonIgnore
	public String getDosage() {
		return dosage;
	}
	public void setDosage(String dosage) {
		this.dosage = dosage;
	}
	@JsonIgnore
	public int getDriving() {
		return driving;
	}
	public void setDriving(int driving) {
		this.driving = driving;
	}
	@JsonIgnore
	public String getDrivingInfo() {
		return drivingInfo;
	}
	public void setDrivingInfo(String drivingInfo) {
		this.drivingInfo = drivingInfo;
	}
	@JsonIgnore
	public int getDrugCardLimit() {
		return drugCardLimit;
	}
	public void setDrugCardLimit(int drugCardLimit) {
		this.drugCardLimit = drugCardLimit;
	}
	@JsonIgnore
	public int getDrugPromoID() {
		return drugPromoID;
	}
	public void setDrugPromoID(int drugPromoID) {
		this.drugPromoID = drugPromoID;
	}
	@JsonIgnore
	public String getEan() {
		return ean;
	}
	public void setEan(String ean) {
		this.ean = ean;
	}
	@JsonIgnore
	public int getFinalSort() {
		return finalSort;
	}
	public void setFinalSort(int finalSort) {
		this.finalSort = finalSort;
	}
	@JsonIgnore
	public String getForm() {
		return form;
	}
	public void setForm(String form) {
		this.form = form;
	}
	@JsonIgnore
	public int getIsAlco() {
		return isAlco;
	}
	public void setIsAlco(int isAlco) {
		this.isAlco = isAlco;
	}
	@JsonIgnore
	public String getIsAlcoInfo() {
		return isAlcoInfo;
	}
	public void setIsAlcoInfo(String isAlcoInfo) {
		this.isAlcoInfo = isAlcoInfo;
	}
	@JsonIgnore
	public int getIsNarcPsych() {
		return isNarcPsych;
	}
	public void setIsNarcPsych(int isNarcPsych) {
		this.isNarcPsych = isNarcPsych;
	}
	@JsonIgnore
	public String getIsNarcPsychInfo() {
		return isNarcPsychInfo;
	}
	public void setIsNarcPsychInfo(String isNarcPsychInfo) {
		this.isNarcPsychInfo = isNarcPsychInfo;
	}
	@JsonIgnore
	public int getIsReimbursed() {
		return isReimbursed;
	}
	public void setIsReimbursed(int isReimbursed) {
		this.isReimbursed = isReimbursed;
	}
	@JsonIgnore
	public int getLactatio() {
		return lactatio;
	}
	public void setLactatio(int lactatio) {
		this.lactatio = lactatio;
	}
	@JsonIgnore
	public String getLactatioInfo() {
		return lactatioInfo;
	}
	public void setLactatioInfo(String lactatioInfo) {
		this.lactatioInfo = lactatioInfo;
	}
	@JsonIgnore
	public String getPack() {
		return pack;
	}
	public void setPack(String pack) {
		this.pack = pack;
	}
	@JsonIgnore
	public int getPackageID() {
		return packageID;
	}
	public void setPackageID(int packageID) {
		this.packageID = packageID;
	}
	@JsonIgnore
	public int getPregnancy() {
		return pregnancy;
	}
	public void setPregnancy(int pregnancy) {
		this.pregnancy = pregnancy;
	}
	@JsonIgnore
	public String getPregnancyInfo() {
		return pregnancyInfo;
	}
	public void setPregnancyInfo(String pregnancyInfo) {
		this.pregnancyInfo = pregnancyInfo;
	}
	@JsonIgnore
	public int getPrescriptionID() {
		return prescriptionID;
	}
	public void setPrescriptionID(int prescriptionID) {
		this.prescriptionID = prescriptionID;
	}
	@JsonIgnore
	public String getPrescriptionName() {
		return prescriptionName;
	}
	public void setPrescriptionName(String prescriptionName) {
		this.prescriptionName = prescriptionName;
	}
	@JsonIgnore
	public String getPrescriptionShortName() {
		return prescriptionShortName;
	}
	public void setPrescriptionShortName(String prescriptionShortName) {
		this.prescriptionShortName = prescriptionShortName;
	}
	@JsonIgnore
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	@JsonIgnore
	public String getProducer() {
		return producer;
	}
	public void setProducer(String producer) {
		this.producer = producer;
	}
	@JsonIgnore
	public int getProducerID() {
		return producerID;
	}
	public void setProducerID(int producerID) {
		this.producerID = producerID;
	}
	@JsonIgnore
	public int getProductID() {
		return productID;
	}
	public void setProductID(int productID) {
		this.productID = productID;
	}
	@JsonIgnore
	public int getProductLineID() {
		return productLineID;
	}
	public void setProductLineID(int productLineID) {
		this.productLineID = productLineID;
	}
	@JsonIgnore
	public String getProductLineName() {
		return productLineName;
	}
	public void setProductLineName(String productLineName) {
		this.productLineName = productLineName;
	}
	@JsonIgnore
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	@JsonIgnore
	public int getProductTypeID() {
		return productTypeID;
	}
	public void setProductTypeID(int productTypeID) {
		this.productTypeID = productTypeID;
	}
	@JsonIgnore
	public String getProductTypeName() {
		return productTypeName;
	}
	public void setProductTypeName(String productTypeName) {
		this.productTypeName = productTypeName;
	}
	@JsonIgnore
	public String getProductTypeShortName() {
		return productTypeShortName;
	}
	public void setProductTypeShortName(String productTypeShortName) {
		this.productTypeShortName = productTypeShortName;
	}
	@JsonIgnore
	public String getRegNo() {
		return regNo;
	}
	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}
	@JsonIgnore
	public int getSponsorID() {
		return sponsorID;
	}
	public void setSponsorID(int sponsorID) {
		this.sponsorID = sponsorID;
	}
	@JsonIgnore
	public String getTherapeuticClass() {
		return therapeuticClass;
	}
	public void setTherapeuticClass(String therapeuticClass) {
		this.therapeuticClass = therapeuticClass;
	}
	@JsonIgnore
	public int getTrimester1() {
		return trimester1;
	}
	public void setTrimester1(int trimester1) {
		this.trimester1 = trimester1;
	}
	@JsonIgnore
	public String getTrimester1Info() {
		return trimester1Info;
	}
	public void setTrimester1Info(String trimester1Info) {
		this.trimester1Info = trimester1Info;
	}
	@JsonIgnore
	public int getTrimester2() {
		return trimester2;
	}
	public void setTrimester2(int trimester2) {
		this.trimester2 = trimester2;
	}
	@JsonIgnore
	public String getTrimester2Info() {
		return trimester2Info;
	}
	public void setTrimester2Info(String trimester2Info) {
		this.trimester2Info = trimester2Info;
	}
	@JsonIgnore
	public int getTrimester3() {
		return trimester3;
	}
	public void setTrimester3(int trimester3) {
		this.trimester3 = trimester3;
	}
	@JsonIgnore
	public String getTrimester3Info() {
		return trimester3Info;
	}
	public void setTrimester3Info(String trimester3Info) {
		this.trimester3Info = trimester3Info;
	}
	@JsonIgnore
	public int getIsFavorite() {
		return isFavorite;
	}
	public void setIsFavorite(int isFavorite) {
		this.isFavorite = isFavorite;
	}
	@JsonIgnore
	public int getOi() {
		return oi;
	}
	public void setOi(int oi) {
		this.oi = oi;
	}
	@JsonIgnore
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	

	public MedicamentAdditional getMedicamentAdditional() {
		return medicamentAdditional;
	}

	public void setMedicamentAdditional(MedicamentAdditional medicamentAdditional) {
		this.medicamentAdditional = medicamentAdditional;
	}
	

	public Set<ATC> getAtcs() {
		return atcs;
	}

	
	public Distributor getDistributor() {
		return distributor;
	}

	public void setDistributor(Distributor distributor) {
		this.distributor = distributor;
	}

	public ProductType getProductType() {
		return productType;
	}

	public void setProductType(ProductType productType) {
		this.productType = productType;
	}

	public Prescription getPrescription() {
		return prescription;
	}

	public void setPrescription(Prescription prescription) {
		this.prescription = prescription;
	}

	public Set<Disease> getDiseases() {
		return diseases;
	}

	public Dosage getDosageObject() {
		return dosageObject;
	}

	public void setDosageObject(Dosage dosageObject) {
		this.dosageObject = dosageObject;
	}
	
	





	

	
	
}
