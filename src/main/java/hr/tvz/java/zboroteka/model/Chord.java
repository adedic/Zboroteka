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

	@Column(name = "naming_style", nullable = false)
	private Integer namingStyle;

	@Column(name = "long_name", nullable = false)
	private String longName;

}
