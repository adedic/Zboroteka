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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
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

}
