package hr.tvz.java.zboroteka.model.enums;
/**
 * Enum for group type.
 * @author apandzic
 *
 */
public enum GroupType {
	
	BAND(1, "Band"),
	CHOIR(2, "Choir"),
	MENS_CHORUS(3, "Mens's chorus"),
	WOMENS_CHORUS(4, "Women's chorus"),
	CHURCH_CHOIR(5, "Church choir"),
	INSTRUMENTAL_GROUP(6, "Instrumental group"),
	VOCAL_GROUP(7, "Vocal group"),
	TRIO(8, "Trio"),
	QUARTET(9, "Quartet"),
	QUINTET(10, "Quintet"),
	A_CAPPELLA(11, "A cappella group"),
	ORCHESTRA(12, "Orchestra"),
	WORSHIP_MINISTRY(13, "Worship ministry");
	
	private Integer value;
	private String name;
	
	private GroupType(Integer value, String name) {
		this.value = value;
		this.name = name;
	}
	
	public Integer getValue() {
		return value;
	}
	
	public String getName() {
		return name;
	}
}
