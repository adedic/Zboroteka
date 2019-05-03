package hr.tvz.java.zboroteka.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "chord")
public class Chord implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1444989249374358882L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "naming_style")
	private Integer namingStyle;

	@Column(name = "long_name")
	private String longName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getNamingStyle() {
		return namingStyle;
	}

	public void setNamingStyle(Integer namingStyle) {
		this.namingStyle = namingStyle;
	}

	public String getLongName() {
		return longName;
	}

	public void setLongName(String longName) {
		this.longName = longName;
	}

}
