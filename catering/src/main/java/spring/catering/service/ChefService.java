package spring.catering.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import spring.catering.model.Chef;
import spring.catering.repository.ChefRepository;

@Service
public class ChefService {

	@Autowired
	private ChefRepository cr;
	
	@Transactional
	public Chef save(Chef chef) {
		return cr.save(chef);
	}
	
	@Transactional
	public void delete(Long id) {
		this.cr.deleteById(id);
	}

	public boolean alreadyExists(Chef target) {
		return this.cr.existsByNomeAndCognomeAndNazionalita(target.getNome(), target.getCognome(), target.getNazionalita());
	}

	public Optional<Chef> findById(Long id) {
		return this.cr.findById(id);
	}
	
	public List<Chef> findAllChef() {
		List<Chef> tuttiChef = new ArrayList<Chef>();
		
		for(Chef c : cr.findAll()) {
			tuttiChef.add(c);
		}
		
		return tuttiChef;
	}

	@Transactional
	public void update(Chef chef, Chef newChef) {
		chef.setNome(newChef.getNome());
		chef.setCognome(newChef.getCognome());
		chef.setNazionalita(newChef.getNazionalita());
		this.cr.save(chef);
	}
	
}
