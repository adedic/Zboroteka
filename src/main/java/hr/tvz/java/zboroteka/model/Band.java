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
import javax.persistence.OneToOne;
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
	private Long id;

	@Column(name = "creation_date", nullable = false)
	private Date creationDate;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "description", nullable = false)
	private String description;

	// TODO napraviti listu predefiniranih vrsta i dadati kao tablicu baze
	@Column(name = "group_type", nullable = false)
	private String groupType;

	private Integer numOfMembers;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
	private User creator;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "band_song", joinColumns = {
			@JoinColumn(name = "song_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "band_id", referencedColumnName = "id") })
	private List<Song> bandSongs;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "band_member", joinColumns = {
			@JoinColumn(name = "member_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "band_id", referencedColumnName = "id") })
	private List<User> members;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getGroupType() {
		return groupType;
	}

	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getNumOfMembers() {
		return numOfMembers;
	}

	public void setNumOfMembers(Integer numOfMembers) {
		this.numOfMembers = numOfMembers;
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
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
