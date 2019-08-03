package hr.tvz.java.zboroteka.mappers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import hr.tvz.java.zboroteka.forms.SongForm;
import hr.tvz.java.zboroteka.model.Band;
import hr.tvz.java.zboroteka.model.Song;
import hr.tvz.java.zboroteka.model.SongKey;
import hr.tvz.java.zboroteka.model.SongSet;
import hr.tvz.java.zboroteka.service.impl.BandService;
import hr.tvz.java.zboroteka.service.impl.SongKeyService;
import hr.tvz.java.zboroteka.service.impl.SongSetService;
import hr.tvz.java.zboroteka.service.impl.UserService;
import hr.tvz.java.zboroteka.util.SongParser;

@Component
public class SongMapper {

	SongMapper() {
		// default
	}

	@Autowired
	SongParser songParser;

	@Autowired
	SongSetService setService;

	@Autowired
	BandService bandService;

	@Autowired
	UserService userService;

	@Autowired
	SongKeyService songKeyService;

	@Autowired
	HttpSession session;

	private static final String DATE_FORMAT = "dd.MM.yyyy";

	public SongForm mapSongToSongForm(Song song) {
		SongForm songForm = new SongForm();
		songForm.setId(song.getId());
		songForm.setAuthor(song.getAuthor());
		songForm.setBandId(song.getBandId());

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
		songForm.setCreationDate(simpleDateFormat.format(song.getCreationDate()).toString());
		songForm.setDescription(song.getDescription());
		songForm.setGenre(song.getGenre());
		songForm.setKey(song.getSongKey().getId());
		songForm.setMeasure(song.getMeasure());
		songForm.setName(song.getName());
		songForm.setRawSongText(song.getRawSongText());
		songForm.setUsage(song.getUsage());
		// songForm.setChordsText(song.getChordsStr());

		songForm.setCreatorId(song.getCreatorId());
		songForm.setCreator(userService.findById(song.getCreatorId()));

		Optional<Band> band = bandService.findBandByUserId(song.getCreatorId());
		if (band.isPresent())
			songForm.setBand(band.get());
		return songForm;
	}

	public void mapSongFormToSong(Song song, SongForm songForm) {
		// if exists
		if (songForm.getId() != null) {

			song.setId(songForm.getId());
			Optional<SongSet> songSet = setService.findSongSetBySongId(songForm.getId());
			if (songSet.isPresent())
				song.setSongSet(songSet.get());
		}

		// TODO ZASAD HARDKODIRANO JER NIJE IMPLENETIRANA PRIJAVA
		Integer userId = 1;
		// = (Integer) session.getAttribute("userId");
		song.setCreatorId(userId);

		Optional<Band> band = bandService.findBandByUserId(userId);
		if (band.isPresent()) {
			song.setBandId(band.get().getId());
		}

		song.setAuthor(songForm.getAuthor());
		song.setDescription(songForm.getDescription());
		song.setGenre(songForm.getGenre());
		Optional<SongKey> songKey = songKeyService.findOne(songForm.getKey());
		if (songKey.isPresent())
			song.setSongKey(songKey.get());
		song.setMeasure(songForm.getMeasure());
		song.setName(songForm.getName());
		song.setUsage(songForm.getUsage());

		song.setRawSongText(songForm.getRawSongText());
		song.setCreationDate(new Date());

	}

	public void mapRawSongTextToChordsStr(Song song) {
		String[] chordsStr = songParser.parseChordsStrFromRawSongText(song.getRawSongText());
		song.setChordsStr(chordsStr);

	}

	public void mapRawSongTextToSong(Song song) {
		songParser.parseSongTextAndChords(song);

	}
}
