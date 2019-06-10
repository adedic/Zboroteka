package hr.tvz.java.zboroteka.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "creation_date", nullable = false)
	private Date creationDate;

	@Column(name = "raw_song_text", nullable = false, columnDefinition = "text")
	private String rawSongText;

	@Column(name = "song_text", nullable = false, columnDefinition = "text")
	private String songText;

	@Column(name = "name")
	private String name;

	/*
	 * 2/4 = dvije četvrtinke (četvrtine nota) u svakome taktu. Brzi korak i brzi
	 * marš imaju ovu mjeru. 3/4 = tri četvrtinke u svakome taktu. Primjeri su
	 * valcer i menuet. 4/4 = četiri četvrtinke u svakome taktu 2/2 = dvije
	 * polovinke 6/8 //neuobicajene: 5/4 i 7/4. 9/8 kao (4 + 2 + 3)/8; 7/8 kao (2 +
	 * 2 + 3)/8; 5/8; 8/8 kao (3 + 2 + 3)/8 ili (3 + 3 + 2)/8; 9/8 kao 2 + 2 + 2 +
	 * 3/8. 2/16, 3/16, 5/16, 3/4
	 */
	@Column(name = "measure")
	private String measure;

	// table SongKey
	@Column(name = "song_key")
	private Integer songKey;

	@Column(name = "genre") // enum SongGenre
	private Integer genre;

	@Column(name = "usage")
	private String usage;

	@Column(name = "author")
	private String author;

	@Column(name = "description")
	private String description;

	// song can exist without set
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinTable(name = "song_song_set", joinColumns = {
			@JoinColumn(name = "song_id", referencedColumnName = "id", nullable = true) }, inverseJoinColumns = {
					@JoinColumn(name = "set_id", referencedColumnName = "id", nullable = true) })
	private SongSet songSet;

	// @ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "band_id", referencedColumnName = "id", nullable = true) // song can exist without band
	private Integer bandId;

	// @ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private Integer creatorId;

	@ManyToMany(cascade = CascadeType.ALL) // song can be only with text, no chords
	@JoinTable(name = "song_chord", joinColumns = {
			@JoinColumn(name = "song_id", nullable = true) }, inverseJoinColumns = {
					@JoinColumn(name = "chord_id", insertable = false, updatable = false, nullable = true) })
	private List<Chord> chords;

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getRawSongText() {
		return rawSongText;
	}

	public void setRawSongText(String rawSongText) {
		this.rawSongText = rawSongText;
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

	public Integer getGenre() {
		return genre;
	}

	public void setGenre(Integer genre) {
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

	public SongSet getSongSet() {
		return songSet;
	}

	public void setSongSet(SongSet set) {
		this.songSet = set;
	}

	public Integer getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Integer creatorId) {
		this.creatorId = creatorId;
	}

	public List<Chord> getChords() {
		return chords;
	}

	public void setChords(List<Chord> chords) {
		this.chords = chords;
	}

	public Integer getSongKey() {
		return songKey;
	}

	public void setSongKey(Integer songKey) {
		this.songKey = songKey;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getBandId() {
		return bandId;
	}

	public void setBandId(Integer bandId) {
		this.bandId = bandId;
	}

}
