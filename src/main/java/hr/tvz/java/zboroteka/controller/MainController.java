package hr.tvz.java.zboroteka.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import hr.tvz.java.zboroteka.JsonResponse;
import hr.tvz.java.zboroteka.model.Song;
import hr.tvz.java.zboroteka.service.impl.ISongService;

@Controller("/")
public class MainController {
	
	@Autowired
	ISongService iSongService;

	@GetMapping("index")
	public ModelAndView show() {
		return new ModelAndView("redirect:/song/mySongs");
	}

	@GetMapping("musicGroup")
	public ModelAndView showNoGroup() {
		// ako ne postoji grupa
		return new ModelAndView("musicGroup/noMusicGroup");
	}

	@PostMapping("searchSong")
	public Object searchSong(Model model, @RequestParam(value = "query", required = false) String query,
			JsonResponse jsonResponse, RedirectAttributes redirectAttributes) {

		List<Song> songs = iSongService.searchSongByQueryAndUser(query);
		Integer[] songsIDs = new Integer[songs.size()];

		// TODO bendsongs
		for (int i = 0; i < songs.size(); i++) {
			System.out.println("pjesma " + songs.get(i).getName());
			songsIDs[i] = songs.get(i).getId();
		}
		jsonResponse.setResult(songsIDs);
		redirectAttributes.addAttribute("songsIDs", songsIDs);


		return ResponseEntity.ok(jsonResponse);
	}

}
