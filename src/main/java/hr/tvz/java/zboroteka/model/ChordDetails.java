package hr.tvz.java.zboroteka.model;

import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/*FOR TRANSPONSE*/

@Data
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@Entity
public class ChordDetails {

	Integer id;
	Integer index;
	String name;
	String len;

}
