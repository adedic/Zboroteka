package hr.tvz.java.zboroteka.forms;

import java.io.Serializable;

import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@Entity
public class SongForm implements Serializable {

	private static final long serialVersionUID = -7054243497044038859L;

	private Integer id;

	private String creationDate;

	private String rawSongText;

	private String chordsText;

	private String name;

	private String measure;

	private Integer key;

	private Integer genre;

	private String usage;

	private String author;

	private String description;

	private Integer bandId;

}
