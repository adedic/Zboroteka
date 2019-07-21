package hr.tvz.java.zboroteka.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import hr.tvz.java.zboroteka.forms.SongForm;
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

	public HashMap<String, Object> updateKeyInRawText(String rawSongText, Integer transposeValue, Integer currentKey) {

		String textAndChords = parseTextAndChords(rawSongText);
		String restBefore = StringUtils.substringBefore(rawSongText, "```" + textAndChords);
		String restAfter = StringUtils.substringAfter(rawSongText, textAndChords + "```");

		// new Key
		Integer newKey = currentKey + transposeValue;

		if (restBefore.contains("Tonalitet:")) {
			String keyName = StringUtils.substringBetween(restBefore, "Tonalitet: ", "\n");
			Optional<SongKey> songKey = iSongKeyService.findOne(newKey);
			if (songKey.isPresent()) {
				restBefore = restBefore.replace(keyName, songKey.get().getName());
			}
		}
		String newText = restBefore + "```" + textAndChords + "```" + restAfter;

		HashMap<String, Object> hmap = new HashMap<>();
		hmap.put("newKey", newKey);
		hmap.put("rawSongText", newText);

		return hmap;

	}

	public String setHeadingAuthorKeyToEditor(SongForm songForm) {
		String songChordsText = "";

		// SONG TEXT AND CHORDS
		// already exists
		if (songForm.getRawSongText() != null && songForm.getRawSongText() != "") {
			String textAndChords = parseTextAndChords(songForm.getRawSongText());
			// EXISTING TEXT AND CHORDS
			if (textAndChords != null)
				songChordsText = "```" + textAndChords + "```";
			System.out.println("songChordsText 1: " + songChordsText);
		} else {
			// INIT SETTINGS
			songChordsText = "\n\n```\n\n[C]\t\t[Am]\n\nTekst i akordi pjesme\n\n```\n\n";
			System.out.println("songChordsText 2: " + songChordsText);
		}
		String textAndChords = parseTextAndChords(songForm.getRawSongText());
		String restBefore = StringUtils.substringBefore(songForm.getRawSongText(), "```" + textAndChords);
		String restAfter = StringUtils.substringAfter(songForm.getRawSongText(), textAndChords + "```");

		// SONG NAME
		if (restBefore.contains("#")) {
			String currName = StringUtils.substringBetween(restBefore, "#", "\n");
			restBefore = restBefore.replace(currName, songForm.getName());
		} else
			restBefore += "#" + songForm.getName();

		// SONG AUTHOR
		String author = "";
		if (songForm.getAuthor() != null && songForm.getAuthor() != "") {
			author = "\n\nAutor: " + songForm.getAuthor() + "\n";
		}
		if (restBefore.contains("Autor: ")) {
			String currAuthor = StringUtils.substringBetween(restBefore, "Autor: ", "\n");
			restBefore = restBefore.replace(currAuthor, songForm.getAuthor());
		} else
			restBefore += author;

		// SONG KEY
		String key = "";
		Optional<SongKey> songKey = null;
		if (songForm.getKey() != null) {
			songKey = iSongKeyService.findOne(songForm.getKey());
			if (songKey.isPresent())
				key = "\n\nTonalitet: " + songKey.get().getName() + "\n";
		}

		if (restBefore.contains("Tonalitet: ")) {
			String currKey = StringUtils.substringBetween(restBefore, "Tonalitet: ", "\n");
			if (songKey.isPresent())
				restBefore = restBefore.replace(currKey, songKey.get().getName());
		} else
			restBefore += key;

		// ALL TEXT
		return restBefore + songChordsText + restAfter;
	}

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

	public List<String> parseSongTextAndChords(Song song) {
		String rawText = song.getRawSongText();
		String textAndChords = parseTextAndChords(rawText);
		String[] chords = parseChordsStr(textAndChords);

		// System.out.println("raw tekst " + song.getRawSongText());

		List<String> unrecognizedChords = new ArrayList<>();

		String text = textAndChords;
		if (chords != null && chords.length != 0) {
			text = parseText(textAndChords, chords);
			song.setSongText(text);

			song.setChords(parseChords(chords, unrecognizedChords));

		}

		return unrecognizedChords;
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

	public List<Chord> parseChords(String[] chords, List<String> unrecognizedChords) {
		List<Chord> chordList = new ArrayList<>();

		for (String chordStr : chords) {
			System.err.println("parsirani tekst akord " + chordStr);
			Optional<Chord> chord = iChordService.getChordByName(chordStr);
			if (chord.isPresent()) {
				chordList.add(chord.get());
			} else {
				unrecognizedChords.add(chordStr);
			}
		}
		return chordList;
	}

	private String parseTextAndChords(String text) {
		return StringUtils.substringBetween(text, "```", "```");
	}
}
