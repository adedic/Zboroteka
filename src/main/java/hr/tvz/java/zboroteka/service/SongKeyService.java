package hr.tvz.java.zboroteka.service;

import java.util.List;
import java.util.Optional;

import hr.tvz.java.zboroteka.model.SongKey;

public interface SongKeyService {

	public List<SongKey> getAllKeys();

	public Optional<SongKey> findOne(Integer id);

	public Optional<SongKey> findOneByNameOrOtherName(String keyName);
}
