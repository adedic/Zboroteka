package hr.tvz.java.zboroteka.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import hr.tvz.java.zboroteka.model.Chord;
import hr.tvz.java.zboroteka.model.Song;
import hr.tvz.java.zboroteka.model.SongKey;
import hr.tvz.java.zboroteka.service.IChordService;
import hr.tvz.java.zboroteka.service.ISongKeyService;

@Component
public class SongParser {

	@Autowired
	public final IChordService iChordService;

	@Autowired
	public final ISongKeyService iSongKeyService;

	SongParser(IChordService iChordService, ISongKeyService iSongKeyService) {
		this.iChordService = iChordService;
		this.iSongKeyService = iSongKeyService;
	}

	// TODO dodati postavljanje naslova i tonaliteta u rawSongText na gumb NASTAVI
	// UNOS PJESME

	public String transposeChordsInSongText(String rawSongText, Integer transposeAmount) {
		String newText = rawSongText;
		String textAndChords = parseTextAndChords(rawSongText);
		// parsiraj samo akorde iz raw teksta
		String[] chords = parseChordsStr(textAndChords);
		// maknuti duplikati
		String[] chordsSet = new HashSet<String>(Arrays.asList(chords)).toArray(new String[0]);
		// prođi kroz listu parsiranih akorda
		for (int i = 0; i < chordsSet.length; i++) {

			String transposedChord = transposeChord(chordsSet[i], transposeAmount);

			System.out.println("trenutni akord: " + chordsSet[i]);
			System.out.println("transposedChord akord: " + transposedChord);

			// postaviti u rawSongText transponirani akord
			newText = newText.replace(chordsSet[i], transposedChord);
		}
		return newText;

	}

	private String transposeChord(String chord, Integer transposeAmount) {
		return findMatchInScale(chord, transposeAmount);

	}

	private String findMatchInScale(String chord, Integer transposeAmount) {
		List<SongKey> keys = iSongKeyService.getAllKeys();
		int scaleLen = keys.size();

		int matchIndex = 0;

		boolean isStandardName = true;
		for (SongKey key : keys) {
			if (key.getName().equals(chord)) {
				System.out.println("match akord1: " + key.getName());
				matchIndex = key.getId();
				isStandardName = true;
			} else if (key.getOtherName().equals(chord)) {
				System.out.println("match akord: " + key.getOtherName());
				matchIndex = key.getId();
				isStandardName = false;
			}
		}

		int i = (matchIndex + transposeAmount - 1) % scaleLen;

		SongKey resultKey;
		if (i < 0) {

			System.out.println(" manje od 0 : " + keys.get(i + scaleLen));
			resultKey = keys.get(i + scaleLen);
		} else {
			System.out.println(" vece od 0 : " + keys.get(i));
			resultKey = keys.get(i);

		}

		if (isStandardName) {
			return resultKey.getName();
		} else {
			return resultKey.getOtherName();
		}
	}

	public void parseSongTextAndChords(Song song) {
		String rawText = song.getRawSongText();
		// song.setName(parseHeading1(lines));
		String textAndChords = parseTextAndChords(rawText);
		String[] chords = parseChordsStr(textAndChords);

		// System.out.println("raw tekst " + song.getRawSongText());

		String text = textAndChords;
		if (chords != null && chords.length != 0) {
			text = parseText(textAndChords, chords);

			song.setSongText(text);
			// System.out.println(" tekst " + song.getSongText());
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

}
