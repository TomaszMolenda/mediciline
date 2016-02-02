package pl.tomo.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.tomo.entity.Medicament;
import pl.tomo.entity.User;
import pl.tomo.repository.MedicamentRepository;

@Service
public class MedicamentService {

	
	@Autowired
	private MedicamentRepository medicamentRepository;
	
	public List<Medicament> findAll()
	{
		return medicamentRepository.findAll();
	}

	public void save(Medicament medicament) {
		medicamentRepository.save(medicament);
		
	}

	public void delete(int id) {
		medicamentRepository.delete(id);
		System.out.println(id);
		
	}

	public List<Medicament> findByUser(User user) {
		
		return medicamentRepository.findByUser(user);
	}

	public Medicament findById(int id) {
		return medicamentRepository.findById(id);
	}

	public void update(Medicament medicament) {
		System.out.println(medicament.getName() + ", " + medicament.getDateExpiration());
		
	}

	public void update(int id, Date dateExpiration) {
		medicamentRepository.update(id, dateExpiration);
		
	}

	
	
	
}
