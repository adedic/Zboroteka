package hr.tvz.java.zboroteka.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import hr.tvz.java.zboroteka.forms.SongForm;
import hr.tvz.java.zboroteka.model.enums.SongGenre;
import hr.tvz.java.zboroteka.model.enums.SongKey;
import hr.tvz.java.zboroteka.service.ISongService;
import hr.tvz.java.zboroteka.util.SongParser;

@Controller("/song")
public class CreateSongController {

	@Autowired
	SongParser songParser;

	@Autowired
	ISongService iSongService;

	@GetMapping("newSong")
	public ModelAndView showNewSong(Model model) {
		model.addAttribute("songForm", new SongForm());
		model.addAttribute("songGenres", Arrays.asList(SongGenre.values()));
		model.addAttribute("songKeys", Arrays.asList(SongKey.values()));

		// todo postaviti na formu ID trenutnog usera iz Session session
		// todo postaviti na formu ID benda tog usera - poziv u service bend

		return new ModelAndView("song/newSong");
	}

	@PostMapping("createSong")
	public ModelAndView createNewSong(@ModelAttribute("songForm") SongForm songForm) {

		// TODO validacija forme backend
		Integer success = iSongService.saveSong(songForm);

		// todo vratiti poruku o uspjesnom spremanju
		return new ModelAndView("/");
	}
}
