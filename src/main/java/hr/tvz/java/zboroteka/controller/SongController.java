package hr.tvz.java.zboroteka.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import hr.tvz.java.zboroteka.JsonResponse;
import hr.tvz.java.zboroteka.forms.SongForm;
import hr.tvz.java.zboroteka.model.ChordDetails;
import hr.tvz.java.zboroteka.model.Song;
import hr.tvz.java.zboroteka.model.enums.SongGenre;
import hr.tvz.java.zboroteka.service.ISongKeyService;
import hr.tvz.java.zboroteka.service.ISongService;
import hr.tvz.java.zboroteka.util.SongParser;
import hr.tvz.java.zboroteka.validator.SongValidator;

@Controller
@RequestMapping("/song")
public class SongController {

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
		model.addAttribute("songHeading", "Kreiranje pjesme");

		return "song/newSong";
	}

	@GetMapping(value = "details")
	public ModelAndView showSongDetails(@RequestParam(value = "id", required = false) Integer songId, Model model) {
		initSongScreen(model);

		Song song = iSongService.findSong(songId);
		if (song != null) {
			SongForm songForm = iSongService.getSongFormDetails(song);

			// promijeniti naslov iz kreiranje pjesme u detalji pjesme
			model.addAttribute("songHeading", "Detalji pjesme");
			model.addAttribute("rawSongText", songForm.getRawSongText());
			model.addAttribute("createSongForm", songForm);
			model.addAttribute("songExists", true);
		}

		return new ModelAndView("/song/songDetails");
	}

	@PostMapping("/createUpdateSong")
	public Object createUpdateSong(Model model, @ModelAttribute("createSongForm") SongForm songForm) {

		JsonResponse jsonResponse = new JsonResponse();
		iSongService.saveSong(songForm, jsonResponse);

		return ResponseEntity.ok(jsonResponse);
	}

	@GetMapping("/mySongs")
	public String showMySongs(Model model) {
		model.addAttribute("songs", iSongService.findSongsByCreator());

		return "song/mySongs";
	}

	@PostMapping("/searchSong")
	public Object searchSong(Model model, @RequestParam(value = "query", required = false) String query) {

		JsonResponse jsonResponse = new JsonResponse();
		List<Song> resultSongs = iSongService.searchSongByQueryAndUser(query);
		// TODO bendsongs

		if (resultSongs.isEmpty()) {
			jsonResponse.setStatus("notFound");
		}

		jsonResponse.setStatus("ok");

		model.addAttribute("resultSongs", resultSongs);

		return ResponseEntity.ok(jsonResponse);
	}

	@RequestMapping(method = RequestMethod.POST, value = "transposeChords", headers = "Accept=application/json", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Object transposeChords(Model model, @ModelAttribute SongForm songForm,
			@RequestParam(value = "rawSongText", required = false) String rawSongText,
			@RequestParam(value = "transposeValue", required = false) Integer transposeValue,
			@RequestParam(value = "currentKey", required = false) Integer currentKey) {

		JsonResponse jsonResponse = new JsonResponse();
		// validations
		if (songValidator.chordsNotFoundInEditor(rawSongText)) {
			jsonResponse.setStatus("chordsNotFound");
			return ResponseEntity.ok(jsonResponse);
		}

		List<String> unrecognizedChords = songValidator.findChordsAndCheckInvalid(rawSongText);

		if (!unrecognizedChords.isEmpty()) {
			jsonResponse.setStatus("invalidChords");
			jsonResponse.setResult(unrecognizedChords);
			return ResponseEntity.ok(jsonResponse);
		}

		List<ChordDetails> foundChords = songParser.createChordsWithMatchIndex(rawSongText);

		// Napravi transpose i vrati tekst s transponiranim akordima
		String newText = songParser.transposeChordsInSongText(foundChords, rawSongText, transposeValue);
		Integer newKey = currentKey + transposeValue;

		Map<String, Object> hmap = songParser.updateKeyInRawText(newText, newKey);
		// postavi promijenjeni rawSongText u rezultat
		// i novi tonalitet nakon transposea
		jsonResponse.setResult(hmap);

		jsonResponse.setStatus("ok");

		return ResponseEntity.ok(jsonResponse);
	}

	@PostMapping(value = "showTextChordsRadio")
	public Object showTextChordsRadio(Model model,
			@RequestParam(value = "rawSongText", required = false) String rawSongText,
			@RequestParam(value = "option", required = false) Integer option) {

		JsonResponse jsonResponse = new JsonResponse();

		Map<String, Object> hmap = new HashMap<String, Object>();
		switch (option) {
		case 1:
			String onlyText = songParser.removeChordsFromRawSongText(rawSongText);
			System.out.println("onlyText " + onlyText);
			if (onlyText != "")
				jsonResponse.setStatus("okText");
			else
				jsonResponse.setStatus("noText");

			hmap.put("onlyText", onlyText);
			break;
		case 2:
			String onlyChords = songParser.removeSongTextFromRawSongText(rawSongText);
			if (onlyChords != "")
				jsonResponse.setStatus("okChords");

			else
				jsonResponse.setStatus("invalidChords");
			hmap.put("onlyChords", onlyChords);

			System.out.println(onlyChords);
			break;
		case 3:
			if (rawSongText != "")
				jsonResponse.setStatus("okBoth");
			else
				jsonResponse.setStatus("noRawText");

			rawSongText = rawSongText.replace("[", "").replace("]", "");
			hmap.put("textAndChords", rawSongText);
			break;
		}

		jsonResponse.setResult(hmap);

		return ResponseEntity.ok(jsonResponse);
	}

	@PostMapping(value = "checkChords", headers = "Accept=application/json", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Object checkChords(Model model, @RequestParam(value = "rawSongText", required = false) String rawSongText) {

		JsonResponse jsonResponse = new JsonResponse();
		if (songValidator.chordsNotFoundInEditor(rawSongText)) {
			jsonResponse.setStatus("chordsNotFound");
			return ResponseEntity.ok(jsonResponse);
		}

		List<String> unrecognizedChords = songValidator.findChordsAndCheckInvalid(rawSongText);

		if (!unrecognizedChords.isEmpty()) {
			jsonResponse.setStatus("invalidChords");
			jsonResponse.setResult(unrecognizedChords);
			return ResponseEntity.ok(jsonResponse);
		} else
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
