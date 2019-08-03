package hr.tvz.java.zboroteka.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.tvz.java.zboroteka.model.Chord;
import hr.tvz.java.zboroteka.repository.ChordRepository;
import hr.tvz.java.zboroteka.service.ChordService;

@Service
public class IChordService implements ChordService {

	@Autowired
	private ChordRepository chordRepository;

	@Override
	public Optional<Chord> getChordByName(String name) {
		return chordRepository.findOneByName(name);
	}
}
