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

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class File {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Disease disease;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private User user;
	
	private String name;
	
	private String path;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date uploadDate;
	
}
