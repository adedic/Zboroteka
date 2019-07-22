package hr.tvz.java.zboroteka.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import hr.tvz.java.zboroteka.forms.SongForm;
import hr.tvz.java.zboroteka.model.JsonResponse;
import hr.tvz.java.zboroteka.model.enums.SongGenre;
import hr.tvz.java.zboroteka.service.ISongKeyService;
import hr.tvz.java.zboroteka.service.ISongService;
import hr.tvz.java.zboroteka.util.SongParser;
import hr.tvz.java.zboroteka.validator.SongValidator;

@Controller
@RequestMapping("/song")
public class SongController {

	private static final String NEW_SONG_VIEW_NAME = "redirect:/song/newSong/";

	private static final String SONG_DETAILS_VIEW_NAME = "redirect:/song/details/";

	@Autowired
	public SongParser songParser;

	@Autowired
	ISongService iSongService;

	@Autowired
	ISongKeyService iSongKeyService;

	@Autowired
	SongValidator songValidator;

	private void initSongScreen(Model model) {
		model.addAttribute("songGenres", Arrays.asList(SongGenre.values()));
		model.addAttribute("songKeys", iSongKeyService.getAllKeys());
	}

	@GetMapping("/newSong")
	public String showNewSong(Model model) {
		model.addAttribute("createSongForm", new SongForm());
		initSongScreen(model);

		return "song/newSong";
	}

	@PostMapping("/createSong")
	public Object createNewSong(@Valid @ModelAttribute("createSongForm") SongForm songForm,
			RedirectAttributes redirectAttributes, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			initSongScreen(model);
			return NEW_SONG_VIEW_NAME;
		}

		JsonResponse jsonResponse = new JsonResponse();
		iSongService.saveSong(songForm, jsonResponse);

		// redirect na details page
		// return SONG_DETAILS_VIEW_NAME + song.getId();

		// return NEW_SONG_VIEW_NAME;

		return ResponseEntity.ok(jsonResponse);
	}

	@PostMapping(value = "transposeChords", headers = "Accept=application/json", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Object transposeChords(Model model, @ModelAttribute SongForm songForm,
			@RequestParam(value = "rawSongText", required = false) String rawSongText,
			@RequestParam(value = "transposeValue", required = false) Integer transposeValue,
			@RequestParam(value = "currentKey", required = false) Integer currentKey) {

		JsonResponse jsonResponse = new JsonResponse();

		// Napravi transpose i vrati tekst s transponiranim akordima
		String newText = songParser.transposeChordsInSongText(rawSongText, transposeValue);

		jsonResponse.setStatus("ok");
		Integer newKey = currentKey + transposeValue;

		Map<String, Object> hmap = new HashMap<>();
		hmap.put("newKey", newKey);
		hmap.put("rawSongText", newText);

		// postavi promijenjeni rawSongText u rezultat
		// i novi tonalitet nakon transposea
		jsonResponse.setResult(hmap);

		return ResponseEntity.ok(jsonResponse);
	}

	@PostMapping(value = "checkChordsExist", headers = "Accept=application/json", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Object checkChordsExist(Model model,
			@RequestParam(value = "foundChords", required = false) String[] foundChords) {

		JsonResponse jsonResponse = new JsonResponse();
		List<String> unrecognizedChords = songValidator.checkInvalidChords(foundChords);
		jsonResponse.setResult(unrecognizedChords);

		if (unrecognizedChords.isEmpty())
			jsonResponse.setStatus("ok");
		else
			jsonResponse.setStatus("error");

		return ResponseEntity.ok(jsonResponse);
	}

	@PostMapping(value = "updateKey", headers = "Accept=application/json", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Object updateKey(Model model, @ModelAttribute SongForm songForm,
			@RequestParam(value = "rawSongText", required = false) String rawSongText,
			@RequestParam(value = "transposeValue", required = false) Integer transposeValue,
			@RequestParam(value = "currentKey", required = false) Integer currentKey) {

		JsonResponse jsonResponse = new JsonResponse();

		Map<String, Object> hmap = songParser.updateKeyInRawText(rawSongText, transposeValue, currentKey);

		// postavi promijenjeni rawSongText u rezultat
		// i novi tonalitet nakon transposea
		jsonResponse.setResult(hmap);

		jsonResponse.setStatus("ok");

		return ResponseEntity.ok(jsonResponse);
	}

	@PostMapping(value = "setHeadingAuthorKeyToEditor", headers = "Accept=application/json", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Object setHeadingAuthorKeyToEditor(Model model, @ModelAttribute SongForm songForm) {

		JsonResponse jsonResponse = new JsonResponse();

		String newText = songParser.setHeadingAuthorKeyToEditor(songForm);

		jsonResponse.setStatus("ok");
		jsonResponse.setResult(newText);

		return ResponseEntity.ok(jsonResponse);
	}

	@GetMapping("/songs")
	public String showAllSongs(Model model) {

		// ako trenutni korisnik nema nijednu pjesmu
		return "song/noSong";

		// a ako ima pjesme prikazati mu popis svih pjesama

	}
}
