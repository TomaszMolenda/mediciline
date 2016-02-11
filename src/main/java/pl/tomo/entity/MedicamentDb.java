package pl.tomo.entity;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class MedicamentDb {
	
	@Id
	@GeneratedValue
	private int id;
	private String name = "";
	private String producent = "";
	private double cena = 0;
	private String kind = "";
	
	@Transient
	private String description = "";
	
	@JsonIgnore
	@OneToMany(mappedBy = "medicamentDb")
	private List<Medicament> listLek;
	
	public MedicamentDb() {
	
	}

	
	public MedicamentDb(int id, String name, String producent, double cena, String kind, String description) {
		super();
		this.id = id;
		this.name = name;
		this.producent = producent;
		this.cena = cena;
		this.kind = kind;
		this.description = description;
	}


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProducent() {
		return producent;
	}
	public void setProducent(String producent) {
		this.producent = producent;
	}
	
	public String getKind() {
		return kind;
	}
	public void setKind(String kind) {
		this.kind = kind;
	}
		
	
	public List<Medicament> getListLek() {
		return listLek;
	}
	public void setListLek(List<Medicament> listLek) {
		this.listLek = listLek;
	}

	


	public double getCena() {
		return cena;
	}


	public void setCena(double cena) {
		this.cena = cena;
	}


	public String getDescription() {
		return kind + ", " + producent;
	}


	public void setDescription(String description) {
		this.description = description;
	}
	

	
	
	
	
	

}
