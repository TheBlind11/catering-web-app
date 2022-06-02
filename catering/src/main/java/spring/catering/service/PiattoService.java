package spring.catering.service;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.catering.model.Piatto;
import spring.catering.repository.PiattoRepository;

@Service
public class PiattoService {

	@Autowired
	private PiattoRepository pr;
	
	public void save(@Valid Piatto piatto) {
		this.pr.save(piatto);
	}
	
	public Optional<Piatto> findById(Long id) {
		return this.pr.findById(id);
	}

	public boolean alreadyExists(Piatto target) {
		return this.pr.existsByNomeAndDescrizione(target.getNome(), target.getDescrizione());
	}

	public void delete(Piatto piatto) {
		this.pr.delete(piatto);
	}
	
	public void update(Piatto piatto, Piatto newPiatto) {
		piatto.setNome(newPiatto.getNome());
		piatto.setDescrizione(newPiatto.getDescrizione());
	}

}
