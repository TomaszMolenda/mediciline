package pl.tomo.medicament.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Product_Type")
@Getter
@Setter
public class ProductType {
	
	@OneToMany(mappedBy = "productType", fetch = FetchType.LAZY)
	@Setter(value = AccessLevel.NONE)
	private Set<Medicament> medicaments;
	
	@Id
	@JsonProperty(value = "ProductTypeID")
	private int productTypeID;
	
	@JsonProperty(value = "TypeDescr")
	private String typeDescr;
	
	@JsonProperty(value = "TypeShort")
	private String typeShort;
	
	@JsonProperty(value = "ProductTypeSort")
	private int productTypeSort;
	
	@Type(type="true_false")
	private boolean active;
	
	public ProductType() {
		active = true;
	}
}
