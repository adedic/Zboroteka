package hr.tvz.java.zboroteka.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.tvz.java.zboroteka.forms.SongForm;
import hr.tvz.java.zboroteka.mappers.SongMapper;
import hr.tvz.java.zboroteka.model.Song;
import hr.tvz.java.zboroteka.repository.SongRepository;
import hr.tvz.java.zboroteka.service.impl.SongService;

@Service
public class ISongService implements SongService {

	@Autowired
	private SongRepository songRepository;

	@Override
	public Song saveSong(SongForm songForm) {

		return songRepository.save(SongMapper.mapSongFormToSong(songForm));
	}

}
