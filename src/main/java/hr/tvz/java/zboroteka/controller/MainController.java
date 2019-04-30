package hr.tvz.java.zboroteka.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller("/")
public class MainController {

	@GetMapping("index")
	public ModelAndView show() {
		return new ModelAndView("index");
	}

	@GetMapping("musicGroup")
	public ModelAndView showNoGroup() {
		// ako ne postoji grupa
		return new ModelAndView("musicGroup/noMusicGroup");
	}

}
