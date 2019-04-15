package hr.tvz.java.zboroteka.model;

import java.io.Serializable;

public class ChordDetails implements Serializable {

	// https://en.wikipedia.org/wiki/Chord_names_and_symbols_(popular_music)
	// https://music.stackexchange.com/questions/11659/what-determines-a-chords-name
	// http://alijamieson.co.uk/chord-naming-conventions/
	/**
	 * 
	 */
	private static final long serialVersionUID = -3715031574516911764L;

	private String rootNote;

	// (e.g., minor or lowercase m, or the symbols ° or + for diminished and
	// augmented chords; quality is usually omitted for major chords).

	private String quality;

	// 7, maj7, or M7
	private String intervalNum;

	// #5
	private String alteredFifth;

	// add2 , aug7
	private String addIntervalNum;

	// before bass note
	private String slash;

	// after slash
	private String bassNote;

	public String getRootNote() {
		return rootNote;
	}

	public void setRootNote(String rootNote) {
		this.rootNote = rootNote;
	}

	public String getQuality() {
		return quality;
	}

	public void setQuality(String quality) {
		this.quality = quality;
	}

	public String getIntervalNum() {
		return intervalNum;
	}

	public void setIntervalNum(String intervalNum) {
		this.intervalNum = intervalNum;
	}

	public String getAlteredFifth() {
		return alteredFifth;
	}

	public void setAlteredFifth(String alteredFifth) {
		this.alteredFifth = alteredFifth;
	}

	public String getAddIntervalNum() {
		return addIntervalNum;
	}

	public void setAddIntervalNum(String addIntervalNum) {
		this.addIntervalNum = addIntervalNum;
	}

	public String getSlash() {
		return slash;
	}

	public void setSlash(String slash) {
		this.slash = slash;
	}

	public String getBassNote() {
		return bassNote;
	}

	public void setBassNote(String bassNote) {
		this.bassNote = bassNote;
	}

}
