package pl.tomo.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Dosage {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private int idMD;
	
	@Temporal(TemporalType.DATE)
	private Date createDate;
	
	@Temporal(TemporalType.TIME)
	private Date takeTime;
	
	private int wholePackage;
	
	private String unit;
	
	private int dose;

	public Dosage() {
	}

	public Dosage(Medicament medicament) {
		String kind = medicament.getKind().replaceAll("\\s+", " ").trim();
		int firstSpace = 0;
		if(kind.contains(" ")) {
			firstSpace = kind.indexOf(" ");
			String substring = kind.substring(0, firstSpace++);
			if(isInteger(substring)) {
				this.wholePackage = Integer.parseInt(substring);
				this.unit = kind.substring(firstSpace, kind.length());
			}
			else {
				if(kind.substring(firstSpace, kind.length()).contains(" ")) {
					int secondSpace = kind.indexOf(" ", firstSpace);
					substring = kind.substring(firstSpace, secondSpace++);
					if(isInteger(substring)) {
						this.wholePackage = Integer.parseInt(substring);
						this.unit = kind.substring(secondSpace, kind.length()) +  " - " + kind.substring(0, firstSpace);
					}
					else {
						this.wholePackage = 1;
						this.unit = kind;
					}
				}
				else {
					this.wholePackage = 1;
					this.unit = kind;
				}
			}
		}
		else {
			this.wholePackage = 1;
			this.unit = kind;
		}
	}

	private static boolean isInteger(String s) {
	    try { 
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    } catch(NullPointerException e) {
	        return false;
	    }
	    return true;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdMD() {
		return idMD;
	}

	public void setIdMD(int idMD) {
		this.idMD = idMD;
	}

	public Date getCreate() {
		return createDate;
	}

	public void setCreate(Date create) {
		this.createDate = create;
	}

	public Date getTakeTime() {
		return takeTime;
	}

	public void setTakeTime(Date take) {
		this.takeTime = take;
	}

	public int getWholePackage() {
		return wholePackage;
	}

	public void setWholePackage(int wholePackage) {
		this.wholePackage = wholePackage;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public int getDose() {
		return dose;
	}

	public void setDose(int dose) {
		this.dose = dose;
	}

	@Override
	public String toString() {
		return "Dosage [id=" + id + ", idMD=" + idMD + ", createDate=" + createDate + ", takeTime=" + takeTime
				+ ", wholePackage=" + wholePackage + ", unit=" + unit + ", dose=" + dose + "]";
	}
	
	
	
}
