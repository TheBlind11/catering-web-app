package spring.catering.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import spring.catering.model.Buffet;
import spring.catering.repository.BuffetRepository;

@Service
public class BuffetService {

	@Autowired
	private BuffetRepository br;

	public boolean alreadyExists(Buffet target) {
		return this.br.existsByNomeAndDescrizione(target.getNome(), target.getDescrizione());
	}

	public Optional<Buffet> findById(Long id) {
		return this.br.findById(id);
	}

	@Transactional
	public void update(Buffet buffet, Buffet newBuffet) {
		buffet.setNome(newBuffet.getNome());
		buffet.setDescrizione(newBuffet.getDescrizione());
		this.br.save(buffet);
	}
	
	@Transactional
	public void delete(Buffet buffet) {
		this.br.delete(buffet);
	}
	
	public List<Buffet> findAll() {
		return (List<Buffet>) this.br.findAll();
	}
	
}
