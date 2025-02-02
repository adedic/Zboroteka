package hr.tvz.java.zboroteka.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.tvz.java.zboroteka.model.SongKey;
import hr.tvz.java.zboroteka.repository.SongKeyRepository;
import hr.tvz.java.zboroteka.service.SongKeyService;

@Service
public class ISongKeyService implements SongKeyService {

	@Autowired
	private SongKeyRepository songKeyRepository;

	@Override
	public List<SongKey> getAllKeys() {
		return songKeyRepository.findAll();
	}

	@Override
	public Optional<SongKey> findOne(Integer id) {
		return songKeyRepository.findById(id);

	}

	@Override
	public Optional<SongKey> findOneByNameOrOtherName(String keyName) {
		return songKeyRepository.findOneByNameOrOtherName(keyName, keyName);
	}
}
