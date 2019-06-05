package hr.tvz.java.zboroteka.forms;

import java.io.Serializable;
import java.util.List;

import hr.tvz.java.zboroteka.model.Song;

public class SongSetForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6765072929809195225L;

	private Integer id;

	private String playDate;
	private String name;
	private Integer numOfSongs;

	private List<Song> songs;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPlayDate() {
		return playDate;
	}

	public void setPlayDate(String playDate) {
		this.playDate = playDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

}
