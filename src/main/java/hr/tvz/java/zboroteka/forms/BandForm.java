package hr.tvz.java.zboroteka.forms;

import java.io.Serializable;
import java.util.List;

import hr.tvz.java.zboroteka.model.Song;
import hr.tvz.java.zboroteka.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
public class BandForm implements Serializable {

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

}
