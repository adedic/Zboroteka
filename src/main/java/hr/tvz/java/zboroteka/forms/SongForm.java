package hr.tvz.java.zboroteka.forms;

import java.io.Serializable;

public class SongForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7054243497044038859L;

	private Integer id;

	private String creationDate;

	private String rawSongText;

	private String name;

	private String measure;

	private Integer key;

	private Integer genre;

	private String usage;

	private String author;

	private String description;

	private Integer bandId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public String getRawSongText() {
		return rawSongText;
	}

	public void setRawSongText(String rawSongText) {
		this.rawSongText = rawSongText;
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

	public Integer getKey() {
		return key;
	}

	public void setKey(Integer key) {
		this.key = key;
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

	public Integer getBandId() {
		return bandId;
	}

	public void setBandId(Integer bandId) {
		this.bandId = bandId;
	}

}
