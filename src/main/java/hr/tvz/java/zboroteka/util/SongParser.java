package hr.tvz.java.zboroteka.util;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import hr.tvz.java.zboroteka.model.Song;

@Component
public class SongParser {

	public static String testStr = "# Krivo je more\r\n" + "\r\n" + "Autor: **Divlje jagode**\r\n" + "\r\n"
			+ "Tonalitet: **G**\r\n" + "\r\n" + "```\r\n" + "1. KITICA\r\n" + "    \r\n" + "    [G]           [Am] \r\n"
			+ "    \r\n" + "    Ti, ti si ga upoznala\r\n" + "    [C]           [G] \r\n" + "      \r\n"
			+ "    jedne ljetnje veceri\r\n" + "    \r\n" + "    [G]         [Am]\r\n" + "    \r\n"
			+ "    On, on te poljubio\r\n" + "```";

	public Song parseSongFormToSong() {
		Song song = new Song();

		String[] lines = testStr.split("\\r?\\n");
		String songName = parseHeading1(lines);
		String textAndChords = parseTextAndChords(testStr);
		String[] chords = parseChords(textAndChords);
		String text = parseText(textAndChords, chords);

		song.setName(songName);
		// System.out.println("NAZIV PJESME " + songName);
		// System.out.println("TEKST I AKORDI PJESME " + textAndChords);
		// System.out.println("TEKST PJESME " + text);
		// for (String c : chords)
		// System.out.println("AKORDI PJESME " + c);
		System.out.println("BEZ AKORDA tekst " + text);

		return song;
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

	private String[] parseChords(String textAndChords) {
		return StringUtils.substringsBetween(textAndChords, "[", "]");
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
