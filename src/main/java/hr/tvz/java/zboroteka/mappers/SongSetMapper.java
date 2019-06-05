package hr.tvz.java.zboroteka.mappers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import hr.tvz.java.zboroteka.forms.SongSetForm;
import hr.tvz.java.zboroteka.model.Band;
import hr.tvz.java.zboroteka.model.SongSet;
import hr.tvz.java.zboroteka.service.impl.BandService;

@Component
public class SongSetMapper {

	@Autowired
	BandService bandService;

	@Autowired
	HttpSession session;

	SongSetMapper() {
		// default
	}

	private static final String DATE_FORMAT = "dd.MM.yyyy";

	public SongSet mapSongSetFormToSongSet(SongSetForm songSetForm) throws ParseException {
		SongSet songSet = new SongSet();

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);

		// TODO ZASAD HARDKODIRANO JER NIJE IMPLENETIRANA PRIJAVA
		Integer userId = 1;
		// = (Integer) session.getAttribute("userId");
		songSet.setCreatorId(userId);

		// dohvat banda po userid iz servisa
		Optional<Band> band = bandService.findBandByUserId(userId);
		if (band.isPresent()) {
			songSet.setBandId(band.get().getId());
		}
		// if exists
		songSet.setId(songSetForm.getId());

		songSet.setCreationDate(new Date());
		songSet.setName(songSetForm.getName());
		songSet.setPlayDate(simpleDateFormat.parse(songSetForm.getPlayDate()));
		songSet.setSongs(songSetForm.getSongs());
		songSet.setNumOfSongs(songSetForm.getSongs().size());

		return songSet;
	}
}
