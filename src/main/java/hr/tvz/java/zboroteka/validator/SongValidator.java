package hr.tvz.java.zboroteka.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import hr.tvz.java.zboroteka.model.Chord;
import hr.tvz.java.zboroteka.service.IChordService;

@Component
public class SongValidator {

	@Autowired
	IChordService iChordService;

	/**
	 * Checks if unrecognized chords are entered in chord list
	 * @param chords
	 * @return
	 */
	public List<String> checkInvalidChords(String[] chords) {
		List<String> unrecognizedChords = new ArrayList<>();

		for (String chordStr : chords) {
			Optional<Chord> chord = iChordService.getChordByName(chordStr);
			if (!chord.isPresent()) {
				System.out.println("chord not found");
				unrecognizedChords.add(chordStr);
			}
		}
		return unrecognizedChords;
	}

}
