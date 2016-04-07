package pl.tomo.entity;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class MedicamentDb {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String name = "";
	
	private String producent = "";
	
	private double price = 0;
	
	private String kind = "";
	
	@Transient
	private String description = "";
	
	
	public MedicamentDb() {
	
	}

	
	public MedicamentDb(int id, String name, String producent, double price, String kind, String description) {
		super();
		this.id = id;
		this.name = name;
		this.producent = producent;
		this.price = price;
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

	public double getPrice() {
		return price;
	}


	public void setPrice(double price) {
		this.price = price;
	}


	public String getDescription() {
		return kind + ", " + producent;
	}


	public void setDescription(String description) {
		this.description = description;
	}
	

	
	
	
	
	

}
