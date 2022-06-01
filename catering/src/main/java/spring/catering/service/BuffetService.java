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

	@Transactional
	public Buffet save(Buffet buffet) {
		return br.save(buffet);
	}

	public boolean alreadyExists(Buffet target) {
		return this.br.existsByNome(target.getNome());
	}

	public Optional<Buffet> findById(Long id) {
		return this.br.findById(id);
	}

	public void update(Buffet buffet, Buffet newBuffet) {
		buffet.setNome(newBuffet.getNome());
		buffet.setDescrizione(newBuffet.getDescrizione());
	}
	
	public void delete(Buffet buffet) {
		this.br.delete(buffet);
	}
	
	public List<Buffet> findAll() {
		return (List<Buffet>) this.br.findAll();
	}
	
}
