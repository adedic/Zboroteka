package hr.tvz.java.zboroteka.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import hr.tvz.java.zboroteka.model.Chord;
import hr.tvz.java.zboroteka.service.impl.IChordService;
import hr.tvz.java.zboroteka.util.SongParser;

@Component
public class SongValidator {

	@Autowired
	IChordService iChordService;

	@Autowired
	SongParser songParser;

	/**
	 * Checks if unrecognized chords are entered in chord list
	 * 
	 * @param chords
	 * @return
	 */
	public List<String> findChordsAndCheckInvalid(String rawSongText) {

		String textAndChords = songParser.parseTextAndChords(rawSongText);
		String[] chords = songParser.parseChordsStr(textAndChords);

		return checkInvalidChords(chords);
	}

	public List<String> checkInvalidChords(String[] chords) {
		List<String> unrecognizedChords = new ArrayList<>();
		for (String chordStr : chords) {
			Optional<Chord> chord = iChordService.getChordByName(chordStr);
			if (!chord.isPresent()) {
				unrecognizedChords.add(chordStr);
			}
		}
		return unrecognizedChords;
	}

	public boolean chordsNotFoundInEditor(String rawSongText) {

		String textAndChords = songParser.parseTextAndChords(rawSongText);
		String[] chords = songParser.parseChordsStr(textAndChords);

		return chords == null || chords.length == 0;
	}

}
