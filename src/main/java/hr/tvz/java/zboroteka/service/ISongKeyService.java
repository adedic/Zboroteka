package hr.tvz.java.zboroteka.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.tvz.java.zboroteka.model.SongKey;
import hr.tvz.java.zboroteka.repository.SongKeyRepository;
import hr.tvz.java.zboroteka.service.impl.SongKeyService;

@Service
public class ISongKeyService implements SongKeyService {

	@Autowired
	private SongKeyRepository songKeyRepository;

	@Override
	public List<SongKey> getAllKeys() {
		return songKeyRepository.findAll();
	}
}
