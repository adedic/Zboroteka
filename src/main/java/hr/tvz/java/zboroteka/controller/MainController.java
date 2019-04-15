package hr.tvz.java.zboroteka.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import hr.tvz.java.zboroteka.util.SongParser;

@Controller("/")
public class MainController {

	@Autowired
	SongParser songParser;

	@GetMapping("index")
	public ModelAndView show() {
		songParser.parseSongFormToSong();
		return new ModelAndView("index");
	}

	@GetMapping("musicGroup")
	public ModelAndView showNoGroup() {
		return new ModelAndView("musicGroup/noMusicGroup");
	}

}
