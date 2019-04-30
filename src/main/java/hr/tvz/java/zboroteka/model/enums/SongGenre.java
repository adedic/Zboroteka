package hr.tvz.java.zboroteka.model.enums;

//https://www.allmusic.com/genres
/**
 * Enum for song genre.
 * @author apandzic
 *
 */
public enum SongGenre {
	
	AVANT_GARDE(1, "Avant-Garde"),
	BLUES(2, "Blues"),
	CHILDRENS(3, "Children's"),
	CLASSICAL(4, "Classical"),
	COMEDY(5, "Comedy/Spoken"),
	COUNTRY(6, "Country"),
	EASY_LISTENING(7, "Easy Listening"),
	ELECTRONIC(8, "Electronic"),
	FOLK(9, "Folk"),
	HOLIDAY(10, "Holiday"),
	INTERNATIONAL(11, "International"),
	JAZZ(12, "Jazz"),
	LATIN(13, "Latin"),
	NEW_AGE(14, "New age"),
	POP_ROCK(15, "Pop/Rock"),
	RNB(16, "R&B"),
	RAP(17, "Rap"),
	RAGGAE(18, "Raggae"),
	RELIGIOUS(19, "Religious"),
	STAGE_SCREEN(20, "Stage & Screen"),
	VOCAL(21, "Vocal");

	
	private Integer value;
	private String name;
	
	private SongGenre(Integer value, String name) {
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
