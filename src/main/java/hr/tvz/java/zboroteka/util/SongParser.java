package hr.tvz.java.zboroteka.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import hr.tvz.java.zboroteka.model.Chord;
import hr.tvz.java.zboroteka.model.Song;
import hr.tvz.java.zboroteka.service.IChordService;

@Component
public class SongParser {

	@Autowired
	public final IChordService iChordService;

	SongParser(IChordService iChordService) {
		this.iChordService = iChordService;
	}

	public void parseSongTextAndChords(Song song) {
		String rawText = song.getRawSongText();

		String[] lines = rawText.split("\\r?\\n");
		// song.setName(parseHeading1(lines));
		String textAndChords = parseTextAndChords(rawText);
		String[] chords = parseChordsStr(textAndChords);

		System.out.println("raw tekst " + song.getRawSongText());

		String text = textAndChords;
		if (chords != null && chords.length != 0) {
			text = parseText(textAndChords, chords);

			song.setSongText(text);
			System.out.println(" tekst " + song.getSongText());
			song.setChords(parseChords(chords));
		}
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
			System.err.println("parsirani tekst akord " + chordStr);
			Optional<Chord> chord = iChordService.getChordByName(chordStr);
			if (chord.isPresent()) {
				chordList.add(chord.get());
			}
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
