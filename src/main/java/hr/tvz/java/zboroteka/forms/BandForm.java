package hr.tvz.java.zboroteka.forms;

import java.io.Serializable;
import java.util.List;

import hr.tvz.java.zboroteka.model.Song;
import hr.tvz.java.zboroteka.model.User;

public class BandForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4579767843807741693L;

	Integer id;
	String name;
	String creationDate;
	String description;
	Integer groupType;
	Integer numOfMembers;
	Integer creatorId;

	private List<Song> bandSongs;
	private List<User> members;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getGroupType() {
		return groupType;
	}

	public void setGroupType(Integer groupType) {
		this.groupType = groupType;
	}

	public Integer getNumOfMembers() {
		return numOfMembers;
	}

	public void setNumOfMembers(Integer numOfMembers) {
		this.numOfMembers = numOfMembers;
	}

	public Integer getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Integer creatorId) {
		this.creatorId = creatorId;
	}

	public List<Song> getBandSongs() {
		return bandSongs;
	}

	public void setBandSongs(List<Song> bandSongs) {
		this.bandSongs = bandSongs;
	}

	public List<User> getMembers() {
		return members;
	}

	public void setMembers(List<User> members) {
		this.members = members;
	}

}
