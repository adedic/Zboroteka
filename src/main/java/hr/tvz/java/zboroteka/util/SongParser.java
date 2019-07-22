package hr.tvz.java.zboroteka.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import hr.tvz.java.zboroteka.forms.SongForm;
import hr.tvz.java.zboroteka.model.Chord;
import hr.tvz.java.zboroteka.model.ChordDetails;
import hr.tvz.java.zboroteka.model.Song;
import hr.tvz.java.zboroteka.model.SongKey;
import hr.tvz.java.zboroteka.service.IChordService;
import hr.tvz.java.zboroteka.service.ISongKeyService;
import hr.tvz.java.zboroteka.validator.SongValidator;

@Component
public class SongParser {

	@Autowired
	public final IChordService iChordService;

	@Autowired
	public final ISongKeyService iSongKeyService;

	@Autowired
	SongValidator songValidator;

	SongParser(IChordService iChordService, ISongKeyService iSongKeyService) {
		this.iChordService = iChordService;
		this.iSongKeyService = iSongKeyService;
	}

	public Map<String, Object> updateKeyInRawText(String rawSongText, Integer newKey) {

		String textAndChords = parseTextAndChords(rawSongText);
		String restBefore = StringUtils.substringBefore(rawSongText, "```" + textAndChords);
		String restAfter = StringUtils.substringAfter(rawSongText, textAndChords + "```");

		if (restBefore.contains("Tonalitet:")) {
			String keyName = StringUtils.substringBetween(restBefore, "Tonalitet: ", "\n");
			Optional<SongKey> songKey = iSongKeyService.findOne(newKey);
			if (songKey.isPresent()) {
				restBefore = restBefore.replace(keyName, songKey.get().getName());
			}
		}
		String newText = restBefore + "```" + textAndChords + "```" + restAfter;

		Map<String, Object> hmap = new HashMap<>();
		hmap.put("newKey", newKey);
		hmap.put("newText", newText);

		return hmap;

	}

	// TODO PROVJERITI RADI LI DOBRO
	public String setHeadingAuthorKeyToEditor(SongForm songForm) {
		String songChordsText = "";

		// SONG TEXT AND CHORDS
		songChordsText = setSongTextAndChords(songForm, songChordsText);
		String textAndChords = parseTextAndChords(songForm.getRawSongText());
		String restBefore = StringUtils.substringBefore(songForm.getRawSongText(), "```" + textAndChords);
		String restAfter = StringUtils.substringAfter(songForm.getRawSongText(), textAndChords + "```");

		// SONG NAME
		restBefore = setSongName(songForm, restBefore);

		// SONG AUTHOR
		restBefore = setSongAuthor(songForm, restBefore);

		// SONG KEY
		restBefore = setSongKey(songForm, restBefore);

		// ALL TEXT
		return restBefore + songChordsText + restAfter;
	}

	private String setSongKey(SongForm songForm, String restBefore) {
		String key = "";
		Optional<SongKey> songKey = null;
		if (songForm.getKey() != null) {
			songKey = iSongKeyService.findOne(songForm.getKey());
			if (songKey.isPresent())
				key = "\n\nTonalitet: " + songKey.get().getName() + "\n";
		}

		if (restBefore.contains("Tonalitet: ")) {
			String currKey = StringUtils.substringBetween(restBefore, "Tonalitet: ", "\n");
			songKey = iSongKeyService.findOne(songForm.getKey());
			if (songKey.isPresent())
				restBefore = restBefore.replace(currKey, songKey.get().getName());
		} else
			restBefore += key;
		return restBefore;
	}

	private String setSongAuthor(SongForm songForm, String restBefore) {
		String author = "";
		if (songForm.getAuthor() != null && songForm.getAuthor() != "") {
			author = "\n\nAutor: " + songForm.getAuthor() + "\n";
		}
		if (restBefore.contains("Autor: ")) {
			String currAuthor = StringUtils.substringBetween(restBefore, "Autor: ", "\n");
			restBefore = restBefore.replace(currAuthor, songForm.getAuthor());
		} else
			restBefore += author;
		return restBefore;
	}

	private String setSongName(SongForm songForm, String restBefore) {
		if (restBefore.contains("#")) {
			String currName = StringUtils.substringBetween(restBefore, "#", "\n");
			restBefore = restBefore.replace(currName, songForm.getName());
		} else
			restBefore += "#" + songForm.getName();
		return restBefore;
	}

	private String setSongTextAndChords(SongForm songForm, String songChordsText) {
		// already exists
		if (songForm.getRawSongText() != null && songForm.getRawSongText() != "") {
			String textAndChords = parseTextAndChords(songForm.getRawSongText());
			// EXISTING TEXT AND CHORDS
			if (textAndChords != null)
				songChordsText = "```" + textAndChords + "```";
		} else {
			// INIT SETTINGS
			songChordsText = "\n\n```\n\n[C]\t\t[Am]\n\nTekst i akordi pjesme\n\n```\n\n";
		}
		return songChordsText;
	}

	public String transposeChordsInSongText(List<ChordDetails> foundChords, String rawSongText,
			Integer transposeValue) {
		String newText = rawSongText;
		Integer diff = 0;

		// prođi kroz listu parsiranih akorda
		for (int i = 0; i < foundChords.size(); i++) {
			String chordToTrans = foundChords.get(i).getName();
			chordToTrans = chordToTrans.replaceAll("[", "");
			chordToTrans = chordToTrans.replaceAll("]", "");

			// Transponirani akord
			String transposedChord = transposeChord(chordToTrans, transposeValue);

			newText = replaceChordWithTransposed(foundChords.get(i), transposedChord, newText);

			// AZURIRANJE, TJ POVECAVANJE INDEKSA SLJEDECEG ZA dodani TEKST
			// razlika duljine chordToTrans i transposedChord koji se dodaje uz zagrade =
			// micanje indeksa sljedeceg akorda unazad
			diff = updateNextChordIndex(transposedChord, foundChords, diff, i);
		}

		return newText;

	}

	private Integer updateNextChordIndex(String transposedChord, List<ChordDetails> foundChords, Integer diff, int i) {

		ChordDetails currChord = foundChords.get(i);
		ChordDetails nextChord = foundChords.get(i + 1);
		Integer chordsArrLen = foundChords.size();

		Integer startLen = currChord.getName().length();
		Integer transLen = transposedChord.length() + 2; // +2 je zbog zagrada
		diff += startLen - transLen;
		
		//azuriraj matchIndeks sljedeceg akorda u tekstu s obzirom na promjene prethodnog
        //dok ima akorda
        if (i + 1 <= chordsArrLen - 1) {
            Integer matchNextIndex = nextChord.getIndex() - diff;
            nextChord.setIndex(matchNextIndex);
           System.out.println("azurirani indeks sljedeceg " + nextChord.getIndex());
        }

		return diff;
	}

	private String replaceChordWithTransposed(ChordDetails currChord, String transposedChord, String newText) {

		// Tekst od pocetka do indeksa na kojem je pronađen akord
		String textBefore = newText.substring(0, currChord.getIndex() - 1);

		String textAfter = newText.substring(currChord.getIndex() + currChord.getName().length());
		// TODO ILI samo getLen()

		newText = textBefore + " " + "[" + transposedChord + "]" + textAfter;

		return newText;
	}

	private String transposeChord(String chord, Integer transposeValue) {
		return findMatchInScale(chord, transposeValue);

	}

	private String findMatchInScale(String chord, Integer transposeValue) {
		List<SongKey> keys = iSongKeyService.getAllKeys();
		int scaleLen = keys.size();

		int matchIndex = 0;

		boolean isStandardName = true;
		for (SongKey key : keys) {
			if (key.getName().equals(chord)) {
				matchIndex = key.getId();
				isStandardName = true;
			} else if (key.getOtherName().equals(chord)) {
				matchIndex = key.getId();
				isStandardName = false;
			}
		}

		int i = (matchIndex + transposeValue - 1) % scaleLen;

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
		String textAndChords = parseTextAndChords(rawText);
		String[] chords = parseChordsStr(textAndChords);

		if (chords != null && chords.length != 0) {
			song.setSongText(parseText(textAndChords, chords));

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

	public List<Chord> parseChords(String[] chords) {
		List<Chord> chordList = new ArrayList<>();

		for (String chordStr : chords) {
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

	public String[] parseChordsStrFromRawSongText(String rawSongText) {
		String textAndChords = parseTextAndChords(rawSongText);
		return parseChordsStr(textAndChords);

	}
}
