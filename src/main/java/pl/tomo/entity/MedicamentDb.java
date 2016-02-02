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
	private int idBazyl;
	private String name = "";
	private String producent = "";
	private String pack = "";
	private String kind = "";
	
	@Transient
	private String description = "";
	
	@JsonIgnore
	@OneToMany(mappedBy = "medicamentDb")
	private List<Medicament> listLek;
	
	public MedicamentDb() {
	
	}
	public MedicamentDb(int idBazyl, String name, String producent, String pack, String kind) {
		super();
		this.idBazyl = idBazyl;
		this.name = name;
		this.producent = producent;
		this.pack = pack;
		this.kind = kind;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIdBazyl() {
		return idBazyl;
	}
	public void setIdBazyl(int idBazyl) {
		this.idBazyl = idBazyl;
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
	public String getPack() {
		return pack;
	}
	public void setPack(String pack) {
		this.pack = pack;
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
	
	
	public String getDescription() {
		return producent + ", " + pack + ", " + kind;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "MedicamentDb [id=" + id + ", name=" + name + ", producent=" + producent + ", pack=" + pack + ", kind="
				+ kind + "]";
	}

	
	
	
	
	

}
