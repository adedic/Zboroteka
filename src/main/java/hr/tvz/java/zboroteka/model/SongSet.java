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
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "song_set")
public class SongSet implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5547890318449259580L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;

	@Column(name = "creation_date", nullable = false)
	private Date creationDate;

	@Column(name = "play_date", nullable = false)
	private Date playDate;

	@Column(name = "name", nullable = false)
	private String name;

	// @ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private Integer creatorId;

	// @ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "band_id", referencedColumnName = "id")
	private Integer bandId;

	@OneToMany(mappedBy = "songSet", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Song> songs;

	private Integer numOfSongs;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getPlayDate() {
		return playDate;
	}

	public void setPlayDate(Date playDate) {
		this.playDate = playDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Integer creatorId) {
		this.creatorId = creatorId;
	}

	public Integer getNumOfSongs() {
		return numOfSongs;
	}

	public void setNumOfSongs(Integer numOfSongs) {
		this.numOfSongs = numOfSongs;
	}

	public List<Song> getSongs() {
		return songs;
	}

	public void setSongs(List<Song> songs) {
		this.songs = songs;
	}

	public Integer getBandId() {
		return bandId;
	}

	public void setBandId(Integer bandId) {
		this.bandId = bandId;
	}

}
