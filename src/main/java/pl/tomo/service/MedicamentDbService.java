package pl.tomo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.tomo.entity.MedicamentDb;
import pl.tomo.repository.MedicamentDbRepository;

@Service
public class MedicamentDbService {

	
	@Autowired
	private MedicamentDbRepository medicamentDbRepository;
	
	public List<MedicamentDb> findAll()
	{
		return medicamentDbRepository.findAll();
	}
	
	public MedicamentDb findOne(int id)
	{
		return medicamentDbRepository.findOne(id);
	}

	
	
	
}
