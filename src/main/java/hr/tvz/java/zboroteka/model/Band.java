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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@Entity
@Table(name = "band")
public class Band implements Serializable {

	private static final long serialVersionUID = -7913178233955358139L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, unique = true, updatable = true)
	private Integer id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "creation_date", nullable = false)
	private Date creationDate;

	@Column(name = "description", nullable = false)
	private String description;

	@Column(name = "group_type", nullable = false)
	private Integer groupType;

	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private Integer creatorId;

	/*@OneToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "band_song", joinColumns = {
			@JoinColumn(name = "band_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "song_id", referencedColumnName = "id") })
	private List<Song> bandSongs;*/

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "band_member", joinColumns = { @JoinColumn(name = "band_id") }, inverseJoinColumns = {
			@JoinColumn(name = "member_id") })
	private List<User> members;

	@OneToMany(mappedBy = "band", fetch = FetchType.LAZY)
	private List<Song> bandSongs;
}
