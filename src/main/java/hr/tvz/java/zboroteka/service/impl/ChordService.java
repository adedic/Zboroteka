package hr.tvz.java.zboroteka.service.impl;

import java.util.Optional;

import hr.tvz.java.zboroteka.model.Chord;

public interface ChordService {

	Optional<Chord> getChordByName(String name);
}
