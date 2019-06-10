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
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "band")
public class Band implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7913178233955358139L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "creation_date", nullable = false)
	private Date creationDate;

	@Column(name = "description", nullable = false)
	private String description;

	@Column(name = "group_type", nullable = false)
	private Integer groupType;

	// @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private Integer creatorId;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "band_song", joinColumns = {
			@JoinColumn(name = "song_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "band_id", referencedColumnName = "id") })
	private List<Song> bandSongs;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "band_member", joinColumns = {
			@JoinColumn(name = "band_id") }, inverseJoinColumns = {
					@JoinColumn(name = "member_id") })
	private List<User> members;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getGroupType() {
		return groupType;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setGroupType(Integer groupType) {
		this.groupType = groupType;
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
