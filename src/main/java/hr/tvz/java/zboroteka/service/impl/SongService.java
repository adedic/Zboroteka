package hr.tvz.java.zboroteka.service.impl;

import hr.tvz.java.zboroteka.JsonResponse;
import hr.tvz.java.zboroteka.forms.SongForm;

public interface SongService {

	public void saveSong(SongForm songForm, JsonResponse jsonResponse);

}
