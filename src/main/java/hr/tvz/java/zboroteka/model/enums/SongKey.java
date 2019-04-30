package hr.tvz.java.zboroteka.model.enums;

// A, A#/Bb, B, C, C#/Db, D, D#/Eb, E, F, F#/Gb, G, and G#/Ab.
public enum SongKey {
	
	C("C"),
	C_SHARP("C#"),
	D_B("Db"),
	D("D"),
	D_SHARP("D#"),
	E_B("Eb"),
	E("E"),
	F("F"),
	F_SHARP("F#"),
	G_B("Gb"),
	G("G"),
	G_SHARP("G#"),
	A_B("Ab"),
	A("A"),
	A_SHARP("A#"),
	B_B("Bb"), //or B in croatian
	B("B"); //or H in croatian
	
	private String name;
	
	private SongKey(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}


}
