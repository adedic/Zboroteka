package hr.tvz.java.zboroteka.service.impl;

import java.text.ParseException;
import java.util.Optional;

import hr.tvz.java.zboroteka.forms.SongSetForm;
import hr.tvz.java.zboroteka.model.SongSet;

public interface SongSetService {

	public SongSet saveSongSet(SongSetForm songSetForm) throws ParseException;

	public Optional<SongSet> findSongSetBySongId(Integer id);
}
