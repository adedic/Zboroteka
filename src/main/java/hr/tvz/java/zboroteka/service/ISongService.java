package hr.tvz.java.zboroteka.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.tvz.java.zboroteka.JsonResponse;
import hr.tvz.java.zboroteka.forms.SongForm;
import hr.tvz.java.zboroteka.mappers.SongMapper;
import hr.tvz.java.zboroteka.model.Song;
import hr.tvz.java.zboroteka.repository.SongRepository;
import hr.tvz.java.zboroteka.service.impl.SongService;
import hr.tvz.java.zboroteka.validator.SongValidator;

@Service
public class ISongService implements SongService {

	@Autowired
	private SongRepository songRepository;

	@Autowired
	HttpSession session;

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

		HashMap<String, Object> hmap = new HashMap<>();

		// chord validation
		if (song.getChordsStr() != null && song.getChordsStr().length != 0) {
			List<String> unrecognizedChords = songValidator.checkInvalidChords(song.getChordsStr());

			if (unrecognizedChords.isEmpty()) {

				// map song text and song chords to song
				songMapper.mapRawSongTextToSong(song);
				Song savedSong = songRepository.save(song);
				hmap.put("songId", savedSong.getId());
				jsonResponse.setStatus("ok");
				
				if (songForm.getId() == null)
					hmap.put("msg", "spremanje");
				else
					hmap.put("msg", "ažuriranje");

			} else if (!unrecognizedChords.isEmpty()) {
				jsonResponse.setStatus("error");
				hmap.put("unrecognizedChords", unrecognizedChords);
			}
		}
		if (song.getId() != null)
			hmap.put("songId", song.getId());

		jsonResponse.setResult(hmap);

	}

	@Override
	public List<Song> searchSongByQueryAndUser(String query) {
		// TODO ZASAD HARDKODIRANO JER NIJE IMPLENETIRANA PRIJAVA
		// Integer creatorId = (Integer) session.getAttribute("userId");
		Integer creatorId = 1;
		Optional<List<Song>> userSongs = songRepository.findAllByQueryAndCreator(query.toUpperCase(), creatorId);

		return userSongs.isPresent() ? userSongs.get() : new ArrayList<>();
	}

	@Override
	public List<Song> findSongsByCreator() {
		// Integer creatorId = (Integer) session.getAttribute("userId");
		Integer creatorId = 1;
		Optional<List<Song>> userSongs = songRepository.findAllByCreatorId(creatorId);

		return userSongs.isPresent() ? userSongs.get() : new ArrayList<>();

	}

	@Override
	public Song findSong(Integer songId) {
		Optional<Song> song = songRepository.findById(songId);

		return song.isPresent() ? song.get() : null;
	}

	public SongForm getSongFormDetails(Song song) {
		return songMapper.mapSongToSongForm(song);
	}

}
