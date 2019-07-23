package hr.tvz.java.zboroteka.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/*FOR TRANSPONSE*/

@Data
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
public class ChordDetails implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5881566506838955325L;
	Integer id;
	Integer index;
	String name;
	Integer len;

}
