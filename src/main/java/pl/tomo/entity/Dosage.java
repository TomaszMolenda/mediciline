package pl.tomo.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Getter;
import lombok.Setter;
import pl.tomo.provider.json.deserialize.TimeDeserializer;

@Getter
@Setter
@Entity
public class Dosage {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private int idMD;
	
	@Temporal(TemporalType.DATE)
	private Date createDate;
	
	@Temporal(TemporalType.TIME)
	@JsonDeserialize(using = TimeDeserializer.class)
	private Date takeTime;
	
	private int wholePackage;
	
	private String unit;
	
	private int dose;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private User user;

	public Dosage() {
	}

	public Dosage(String kind) {
		kind = kind.replaceAll("\\s+", " ").trim();
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
}
