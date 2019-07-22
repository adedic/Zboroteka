package hr.tvz.java.zboroteka.forms;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;

import hr.tvz.java.zboroteka.model.Song;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@Entity
public class SongSetForm implements Serializable {

	private static final long serialVersionUID = -6765072929809195225L;

	private Integer id;

	private String playDate;
	private String name;
	private Integer numOfSongs;

	private List<Song> songs;

}
