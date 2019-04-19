package hr.tvz.java.zboroteka.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import hr.tvz.java.zboroteka.model.Chord;
import hr.tvz.java.zboroteka.model.Song;

@Component
public class SongParser {

	public void parseSongTextAndChords(Song song) {
		String rawText = song.getRawSongText();

		String[] lines = rawText.split("\\r?\\n");
		String songName = parseHeading1(lines);
		String textAndChords = parseTextAndChords(rawText);
		String[] chords = parseChordsStr(textAndChords);
		String text = textAndChords;
		if (chords.length != 0) {
			text = parseText(textAndChords, chords);
			song.setChords(parseChords(chords));
			for (String c : chords)
				System.out.println("AKORDI PJESME " + c);
		}

		song.setSongText(text);
		// song.setName(songName);
		// System.out.println("NAZIV PJESME " + songName);
		// System.out.println("TEKST I AKORDI PJESME " + textAndChords);
		// System.out.println("TEKST PJESME " + text);
		// for (String c : chords)
		// System.out.println("BEZ AKORDA tekst " + text);
	}

	private String parseText(String textAndChords, String[] chords) {
		StringBuilder text = new StringBuilder();

		// makni prvi akord iz teksta
		String withoutChords = StringUtils.remove(textAndChords, "[" + chords[0] + "]");
		for (int i = 1; i <= chords.length - 1; i++) {
			// iz trenutno ociscenog teksta makni sljedeci akord i postavi to za trenutni
			// parsirani tekst
			withoutChords = StringUtils.remove(withoutChords, "[" + chords[i] + "]");
			// na kraju su maknuti svi akordi
		}
		text.append(withoutChords);
		return text.toString();
	}

	private String[] parseChordsStr(String textAndChords) {
		return StringUtils.substringsBetween(textAndChords, "[", "]");
	}

	private List<Chord> parseChords(String[] chords) {
		List<Chord> chordList = new ArrayList<>();

		for (String chordStr : chords) {
			Chord chord = new Chord();
			chord.setName(chordStr);
			chordList.add(chord);
		}
		return chordList;
	}

	private String parseTextAndChords(String text) {
		return StringUtils.substringBetween(text, "```", "```");
	}

	private String parseHeading1(String[] lines) {
		StringBuilder heading1 = new StringBuilder();
		for (int i = 0; i < lines.length; i++) {
			String currentLine = lines[i];

			for (int j = 0; j < currentLine.length(); j++) {
				char currentChar = currentLine.charAt(j);

				// Process char
				if (currentChar == '#') {
					currentLine = currentLine.replace("#", "").trim();

					heading1.append(currentLine);
					break;
				}
			}
		}

		return heading1.toString();

	}

}
