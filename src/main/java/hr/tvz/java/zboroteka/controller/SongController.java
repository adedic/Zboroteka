package hr.tvz.java.zboroteka.controller;

import java.util.Arrays;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import hr.tvz.java.zboroteka.forms.SongForm;
import hr.tvz.java.zboroteka.model.Song;
import hr.tvz.java.zboroteka.model.enums.SongGenre;
import hr.tvz.java.zboroteka.model.enums.SongKey;
import hr.tvz.java.zboroteka.service.ISongService;

@Controller
@RequestMapping("/song")
public class SongController {

	private static final String NEW_SONG_VIEW_NAME = "redirect:/song/newSong/";

	private static final String SONG_DETAILS_VIEW_NAME = "redirect:/song/details/";

	@Autowired
	ISongService iSongService;

	@GetMapping("/newSong")
	public String showNewSong(Model model) {
		model.addAttribute("createSongForm", new SongForm());
		model.addAttribute("songGenres", Arrays.asList(SongGenre.values()));
		model.addAttribute("songKeys", Arrays.asList(SongKey.values()));

		return "song/newSong";
	}

	@PostMapping("/createSong")
	public String createNewSong(@Valid @ModelAttribute("createSongForm") SongForm songForm,
			RedirectAttributes redirectAttributes, BindingResult bindingResult, Model model) {
		System.out.println("raw text " + songForm.getRawSongText());
		if (bindingResult.hasErrors()) {
			model.addAttribute("songGenres", Arrays.asList(SongGenre.values()));
			model.addAttribute("songKeys", Arrays.asList(SongKey.values()));
			// TODO popuniti sifarnike u modelu
			return NEW_SONG_VIEW_NAME;
		}
		// TODO validacija forme backend
		Song song = iSongService.saveSong(songForm);

		// todo prikazati poruku o uspjesnom spremanju
		redirectAttributes.addFlashAttribute("createSongSuccess", "Uspješno dodavanje nove pjesme!");

		// redirect na details page
		//return SONG_DETAILS_VIEW_NAME + song.getId();
		return NEW_SONG_VIEW_NAME;
	}

	@GetMapping("/songs")
	public String showAllSongs(Model model) {

		// ako trenutni korisnik nema nijednu pjesmu
		return "song/noSong";

		// a ako ima pjesme prikazati mu popis svih pjesama

	}
}
