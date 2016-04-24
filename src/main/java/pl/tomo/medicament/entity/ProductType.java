package pl.tomo.medicament.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "Product_Type")
public class ProductType {
	
	@OneToMany(mappedBy = "productType", fetch = FetchType.LAZY)
	private Set<Medicament> medicaments;
	
	@Id
	@JsonProperty(value = "ProductTypeID")
	int productTypeID;
	@JsonProperty(value = "TypeDescr")
	String typeDescr;
	@JsonProperty(value = "TypeShort")
	String typeShort;
	@JsonProperty(value = "ProductTypeSort")
	int productTypeSort;
	@Type(type="true_false")
	private boolean active;
	
	public ProductType() {
		active = true;
	}
	public int getProductTypeID() {
		return productTypeID;
	}
	public void setProductTypeID(int productTypeID) {
		this.productTypeID = productTypeID;
	}
	public String getTypeDescr() {
		return typeDescr;
	}
	public void setTypeDescr(String typeDescr) {
		this.typeDescr = typeDescr;
	}
	public String getTypeShort() {
		return typeShort;
	}
	public void setTypeShort(String typeShort) {
		this.typeShort = typeShort;
	}
	public int getProductTypeSort() {
		return productTypeSort;
	}
	public void setProductTypeSort(int productTypeSort) {
		this.productTypeSort = productTypeSort;
	}
	
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	
	
	public Set<Medicament> getMedicaments() {
		return medicaments;
	}
	@Override
	public String toString() {
		return "ProductType [productTypeID=" + productTypeID + ", typeDescr=" + typeDescr + ", typeShort=" + typeShort
				+ ", productTypeSort=" + productTypeSort + ", active=" + active + "]";
	}
	
	
	

}
