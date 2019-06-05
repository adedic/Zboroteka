package hr.tvz.java.zboroteka.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.tvz.java.zboroteka.forms.BandForm;
import hr.tvz.java.zboroteka.mappers.BandMapper;
import hr.tvz.java.zboroteka.model.Band;
import hr.tvz.java.zboroteka.repository.BandRepository;
import hr.tvz.java.zboroteka.service.impl.BandService;

@Service
public class IBandService implements BandService {

	@Autowired
	BandRepository bandRepository;

	@Autowired
	BandMapper bandMapper;

	@Override
	public Optional<Band> findBandByUserId(Integer userId) {
		return bandRepository.findById(userId);

	}

	@Override
	public Band saveBand(BandForm bandForm) {
		return bandRepository.save(bandMapper.mapBandFormToBand(bandForm));
	}

}
