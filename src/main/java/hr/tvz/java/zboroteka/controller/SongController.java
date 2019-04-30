package hr.tvz.java.zboroteka.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import hr.tvz.java.zboroteka.forms.SongForm;
import hr.tvz.java.zboroteka.model.enums.SongGenre;
import hr.tvz.java.zboroteka.model.enums.SongKey;
import hr.tvz.java.zboroteka.service.ISongService;

@Controller
@RequestMapping("/song")
public class SongController {

	@Autowired
	ISongService iSongService;

	@GetMapping("/newSong")
	public String showNewSong(Model model) {
		model.addAttribute("createSongForm", new SongForm());
		model.addAttribute("songGenres", Arrays.asList(SongGenre.values()));
		model.addAttribute("songKeys", Arrays.asList(SongKey.values()));

		// todo postaviti na formu ID trenutnog usera iz Session session
		// todo postaviti na formu ID benda tog usera - poziv u service bend
		return "song/newSong";
	}

	@PostMapping("/createSong")
	public ModelAndView createNewSong(@ModelAttribute("createSongForm") SongForm songForm) {
		System.out.println("raw text " + songForm.getRawSongText());

		// TODO validacija forme backend
		Integer success = iSongService.saveSong(songForm);

		// todo vratiti poruku o uspjesnom spremanju
		return new ModelAndView("/");
	}
	

	@GetMapping("/songs")
	public String showAllSongs(Model model) {
		
		//ako trenutni korisnik nema nijednu pjesmu
		return "song/noSong";
		
		//a ako ima pjesme prikazati mu popis svih pjesama
		
	}
}
