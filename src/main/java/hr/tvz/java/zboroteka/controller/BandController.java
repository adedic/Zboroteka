package hr.tvz.java.zboroteka.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import hr.tvz.java.zboroteka.JsonResponse;
import hr.tvz.java.zboroteka.forms.BandForm;
import hr.tvz.java.zboroteka.model.Band;
import hr.tvz.java.zboroteka.model.Song;
import hr.tvz.java.zboroteka.model.User;
import hr.tvz.java.zboroteka.model.enums.GroupType;
import hr.tvz.java.zboroteka.service.impl.IBandService;
import hr.tvz.java.zboroteka.service.impl.ISongService;
import hr.tvz.java.zboroteka.service.impl.IUserService;

@Controller
@RequestMapping("/band")
public class BandController {

	@Autowired
	ISongService iSongService;

	@Autowired
	IBandService iBandService;
	@Autowired
	IUserService iUserService;

	@GetMapping("/songs")
	public String showBandSongs(Model model) {
		List<Song> songs = iSongService.findSongsByBand();
		model.addAttribute("songs", songs);
		model.addAttribute("songsExists", true);

		if (songs.isEmpty())
			model.addAttribute("songsExists", false);

		// a ako ima pjesme prikazati mu popis svih pjesama
		return "band/bandSongs"; // ili mySongs
	}

	@GetMapping("/create")
	public String showCreateBand(Model model) {
		model.addAttribute("bandForm", new BandForm());
		model.addAttribute("groupTypes", Arrays.asList(GroupType.values()));
		return "band/newBand";
	}

	@PostMapping("/createUpdateBand")
	public Object createUpdateBand(Model model, @ModelAttribute("bandForm") BandForm bandForm) {

		JsonResponse jsonResponse = new JsonResponse();
		
		iBandService.saveBand(bandForm);

		jsonResponse.setStatus("ok");
		if (bandForm.getId() == null)
			jsonResponse.setResult("spremanje");
		else
			jsonResponse.setResult("ažuriranje");

		return ResponseEntity.ok(jsonResponse);
	}

	@GetMapping("/info")
	public String showBand(Model model) {

		// TODO HARDKODIRAN USER
		Optional<Band> band = iBandService.findBandByUserId(1);

		if (band.isPresent()) {
			model.addAttribute("band", band.get());
			model.addAttribute("bandExists", true);

			User creator = iUserService.findById(band.get().getCreatorId());
			model.addAttribute("creator", creator);
			model.addAttribute("groupType",
					Arrays.asList(GroupType.values()).get(band.get().getGroupType()).getName().toUpperCase());

		} else
			model.addAttribute("bandExists", false);

		return "band/bandInfo";
	}

}
