package hr.tvz.java.zboroteka.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.tvz.java.zboroteka.forms.SongForm;
import hr.tvz.java.zboroteka.mappers.SongMapper;
import hr.tvz.java.zboroteka.model.JsonResponse;
import hr.tvz.java.zboroteka.model.Song;
import hr.tvz.java.zboroteka.repository.SongRepository;
import hr.tvz.java.zboroteka.service.impl.SongService;
import hr.tvz.java.zboroteka.validator.SongValidator;

@Service
public class ISongService implements SongService {

	@Autowired
	private SongRepository songRepository;

	@Autowired
	SongMapper songMapper;

	@Autowired
	SongValidator songValidator;

	@Override
	public void saveSong(SongForm songForm, JsonResponse jsonResponse) {
		Song song = new Song();

		// map basic song data to song
		songMapper.mapSongFormToSong(song, songForm);

		// map chordsStr from raw song text to song
		songMapper.mapRawSongTextToChordsStr(song);

		// chord validation
		List<String> unrecognizedChords = songValidator.checkInvalidChords(song.getChordsStr());
		HashMap<String, Object> hmap = new HashMap<>();

		if (unrecognizedChords.isEmpty()) {

			// map song text and song chords to song
			songMapper.mapRawSongTextToSong(song);
			songRepository.save(song);
			jsonResponse.setStatus("ok");
			hmap.put("songId", song.getId());

		} else if (!unrecognizedChords.isEmpty()) {
			jsonResponse.setStatus("error");
			hmap.put("unrecognizedChords", unrecognizedChords);
		}

		jsonResponse.setResult(hmap);

	}

}
