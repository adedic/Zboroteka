package hr.tvz.java.zboroteka.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "song")
public class Song implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 393192385042172713L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "creation_date", nullable = false)
	private Date creationDate;

	@Column(name = "song_text", nullable = false)
	private String songText;

	@Column(name = "name")
	private String name;

	// TODO dodati predefinirane mjere u bazu - tablica measure
	@Column(name = "measure")
	private String measure;

	// TODO dodati predefinirane tonalitete u bazu - tablica key
	@Column(name = "key")
	private String key;

	// TODO dodati predefinirane zanrove u bazu - tablica genre
	@Column(name = "genre")
	private String genre;

	@Column(name = "usage")
	private String usage;

	@Column(name = "author")
	private String author;

	@Column(name = "description")
	private String description;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinTable(name = "song_set", joinColumns = {
			@JoinColumn(name = "song_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "set_id", referencedColumnName = "id") })
	private Set set;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "band_id", referencedColumnName = "id")
	private Band band;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User creator;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "chord", joinColumns = {
			@JoinColumn(name = "song_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "chord_id", referencedColumnName = "id") })
	private List<Chord> chords;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getSongText() {
		return songText;
	}

	public void setSongText(String songText) {
		this.songText = songText;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMeasure() {
		return measure;
	}

	public void setMeasure(String measure) {
		this.measure = measure;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getUsage() {
		return usage;
	}

	public void setUsage(String usage) {
		this.usage = usage;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set getSet() {
		return set;
	}

	public void setSet(Set set) {
		this.set = set;
	}

	public Band getBand() {
		return band;
	}

	public void setBand(Band band) {
		this.band = band;
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public List<Chord> getChords() {
		return chords;
	}

	public void setChords(List<Chord> chords) {
		this.chords = chords;
	}

}
