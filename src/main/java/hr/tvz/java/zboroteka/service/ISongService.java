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

@Service
public class ISongService implements SongService {

	@Autowired
	private SongRepository songRepository;

	@Autowired
	SongMapper songMapper;

	@Override
	public void saveSong(SongForm songForm, JsonResponse jsonResponse) {
		Song song = new Song();

		List<String> unrecognizedChords = songMapper.mapSongFormToSong(song, songForm);

		//validacija akorda
		HashMap<String, Object> hmap = new HashMap<>();

		if (unrecognizedChords.isEmpty()) {
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
