package hr.tvz.java.zboroteka.service.impl;

import hr.tvz.java.zboroteka.forms.SongForm;
import hr.tvz.java.zboroteka.model.JsonResponse;

public interface SongService {

	public void saveSong(SongForm songForm, JsonResponse jsonResponse);

}
