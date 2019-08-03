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
import javax.persistence.OneToOne;
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

	@Column(name = "song_text", columnDefinition = "text")
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
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "song_key", referencedColumnName = "id")
	private SongKey songKey;

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

	// @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private Integer creatorId;

	@Column(name = "preview_option")
	private Integer previewOption;

	@ManyToMany(cascade = CascadeType.ALL) // song can be only with text, no chords
	@JoinTable(name = "song_chord", joinColumns = {
			@JoinColumn(name = "song_id", nullable = true) }, inverseJoinColumns = {
					@JoinColumn(name = "chord_id", insertable = false, updatable = false, nullable = true) })
	private List<Chord> chords;

	private String[] chordsStr;

}
