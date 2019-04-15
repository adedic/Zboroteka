package hr.tvz.java.zboroteka.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import hr.tvz.java.zboroteka.util.SongParser;

@Controller("/song")
public class CreateSongController {

	@Autowired
	SongParser songParser;

	@GetMapping("newSong")
	public ModelAndView showNewSong() {
		return new ModelAndView("song/newSong");
	}

	@PostMapping("createSong")
	public ModelAndView createNewSong() {
		return new ModelAndView("/");
	}
}
