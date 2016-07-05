package pl.tomo.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedEntityGraphs;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@NamedQueries({
    @NamedQuery(name = "File.findByDisease", query = "SELECT f FROM File f WHERE f.disease = :disease"),
})
@NamedEntityGraphs({
    @NamedEntityGraph(
        name = "fileWithUserAndDiseases",
	        attributeNodes = {
	        		@NamedAttributeNode("user"),
	        		@NamedAttributeNode("disease")
            }
    )
})
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
	
	@Transient
	private String url;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date uploadDate;
	
}
