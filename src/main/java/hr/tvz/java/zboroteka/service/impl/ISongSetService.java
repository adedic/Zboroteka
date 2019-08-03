package hr.tvz.java.zboroteka.service.impl;

import java.text.ParseException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.tvz.java.zboroteka.forms.SongSetForm;
import hr.tvz.java.zboroteka.mappers.SongSetMapper;
import hr.tvz.java.zboroteka.model.SongSet;
import hr.tvz.java.zboroteka.repository.SongSetRepository;
import hr.tvz.java.zboroteka.service.SongSetService;

@Service
public class ISongSetService implements SongSetService {

	@Autowired
	private SongSetRepository songSetRepository;

	@Autowired
	SongSetMapper songSetMapper;

	@Override
	public SongSet saveSongSet(SongSetForm songSetForm) throws ParseException {

		return songSetRepository.save(songSetMapper.mapSongSetFormToSongSet(songSetForm));
	}

	@Override
	public Optional<SongSet> findSongSetBySongId(Integer id) {
		return songSetRepository.findById(id);
	}
}
