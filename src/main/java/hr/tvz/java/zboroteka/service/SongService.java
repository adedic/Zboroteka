package hr.tvz.java.zboroteka.service;

import java.util.List;

import hr.tvz.java.zboroteka.JsonResponse;
import hr.tvz.java.zboroteka.forms.SongForm;
import hr.tvz.java.zboroteka.model.Song;

public interface SongService {

	public List<Song> searchSongByQueryAndUser(String query);

	List<Song> findSongsByCreator();

	Song findSong(Integer songId);

	void saveSong(SongForm songForm, JsonResponse jsonResponse);

}
