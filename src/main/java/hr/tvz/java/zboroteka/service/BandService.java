package hr.tvz.java.zboroteka.service;

import java.util.Optional;

import hr.tvz.java.zboroteka.forms.BandForm;
import hr.tvz.java.zboroteka.model.Band;

public interface BandService {

	// currently user can be member of only one band
	Optional<Band> findBandByUserId(Integer userId);

	Band saveBand(BandForm bandForm);

}
