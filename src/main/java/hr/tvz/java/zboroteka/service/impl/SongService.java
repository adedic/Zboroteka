package hr.tvz.java.zboroteka.service.impl;

import java.util.List;

import hr.tvz.java.zboroteka.JsonResponse;
import hr.tvz.java.zboroteka.forms.SongForm;
import hr.tvz.java.zboroteka.model.Song;

public interface SongService {

	public void saveSong(SongForm songForm, JsonResponse jsonResponse);

	public List<Song> searchSongByQueryAndUser(String query);

}
