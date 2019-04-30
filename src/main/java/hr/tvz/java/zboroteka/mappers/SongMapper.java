package hr.tvz.java.zboroteka.mappers;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import hr.tvz.java.zboroteka.forms.SongForm;
import hr.tvz.java.zboroteka.model.Song;
import hr.tvz.java.zboroteka.util.SongParser;

@Component
public class SongMapper {

	@Autowired
	public static final SongParser SONG_PARSER = new SongParser();

	public static String testStr = "# Krivo je more\r\n" + "\r\n" + "Autor: **Divlje jagode**\r\n" + "\r\n"
			+ "Tonalitet: **G**\r\n" + "\r\n" + "```\r\n" + "1. KITICA\r\n" + "    \r\n" + "    [G]           [Am] \r\n"
			+ "    \r\n" + "    Ti, ti si ga upoznala\r\n" + "    [C]           [G] \r\n" + "      \r\n"
			+ "    jedne ljetnje veceri\r\n" + "    \r\n" + "    [G]         [Am]\r\n" + "    \r\n"
			+ "    On, on te poljubio\r\n" + "```";

	public static Song mapSongFormToSong(SongForm songForm) {
		Song song = new Song();
		// if exists
		song.setId(songForm.getId());

		song.setAuthor(songForm.getAuthor());

		song.setCreatorId(songForm.getCreatorId());
		song.setBandId(songForm.getBandId());

		song.setDescription(songForm.getDescription());
		song.setGenre(songForm.getGenre());
		song.setKey(songForm.getKey());
		song.setMeasure(songForm.getMeasure());
		song.setName(songForm.getName());
		song.setUsage(songForm.getUsage());

		// set service - metodfa za dohvat seta po id-u pjesme?
		// song.setSet(songForm.getSetId());
		song.setRawSongText(songForm.getRawSongText());

		SONG_PARSER.parseSongTextAndChords(song);

		song.setCreationDate(new Date());

		return song;

	}
}
