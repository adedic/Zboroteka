package hr.tvz.java.zboroteka.model.enums;
/**
 * Enum for group type.
 * @author apandzic
 *
 */
public enum GroupType {
	
	BAND(0, "Bend"),
	CHOIR(1, "Zbor"),
	MENS_CHORUS(2, "Muški zbor"),
	WOMENS_CHORUS(3, "Ženski zbor"),
	CHURCH_CHOIR(4, "Crkveni zbor"),
	INSTRUMENTAL_GROUP(5, "Instrumentalna grupa"),
	VOCAL_GROUP(6, "Vokalna grupa"),
	TRIO(7, "Trio"),
	QUARTET(8, "Kvartet"),
	QUINTET(9, "Kvintet"),
	A_CAPPELLA(10, "A cappella grupa"),
	ORCHESTRA(11, "Orkestar"),
	WORSHIP_MINISTRY(12, "Slavljenička služna");
	
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
