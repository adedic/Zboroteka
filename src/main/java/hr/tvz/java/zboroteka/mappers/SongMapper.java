package hr.tvz.java.zboroteka.mappers;

import java.util.Date;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import hr.tvz.java.zboroteka.forms.SongForm;
import hr.tvz.java.zboroteka.model.Band;
import hr.tvz.java.zboroteka.model.Song;
import hr.tvz.java.zboroteka.model.SongSet;
import hr.tvz.java.zboroteka.service.impl.BandService;
import hr.tvz.java.zboroteka.service.impl.SongSetService;
import hr.tvz.java.zboroteka.util.SongParser;

@Component
public class SongMapper {

	SongMapper() {
		// default
	}

	@Autowired
	public static final SongParser SONG_PARSER = new SongParser();

	@Autowired
	SongSetService setService;

	@Autowired
	BandService bandService;

	@Autowired
	HttpSession session;

	public static String testStr = "# Krivo je more\r\n" + "\r\n" + "Autor: **Divlje jagode**\r\n" + "\r\n"
			+ "Tonalitet: **G**\r\n" + "\r\n" + "```\r\n" + "1. KITICA\r\n" + "    \r\n" + "    [G]           [Am] \r\n"
			+ "    \r\n" + "    Ti, ti si ga upoznala\r\n" + "    [C]           [G] \r\n" + "      \r\n"
			+ "    jedne ljetnje veceri\r\n" + "    \r\n" + "    [G]         [Am]\r\n" + "    \r\n"
			+ "    On, on te poljubio\r\n" + "```";

	public Song mapSongFormToSong(SongForm songForm) {
		Song song = new Song();
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
		song.setSongKey(songForm.getKey());
		song.setMeasure(songForm.getMeasure());
		song.setName(songForm.getName());
		song.setUsage(songForm.getUsage());

		song.setRawSongText(songForm.getRawSongText());

		SONG_PARSER.parseSongTextAndChords(song);

		song.setCreationDate(new Date());

		return song;

	}
}
