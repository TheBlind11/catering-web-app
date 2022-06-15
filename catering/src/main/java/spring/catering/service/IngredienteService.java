package spring.catering.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import spring.catering.model.Ingrediente;
import spring.catering.repository.IngredienteRepository;

@Service
public class IngredienteService {
	
	@Autowired
	private IngredienteRepository ir;
	
	@Transactional
	public void save(Ingrediente i) {
		this.ir.save(i);
	}
	
	@Transactional
	public void delete(Ingrediente i) {
		this.ir.delete(i);
	}
	
	@Transactional
	public void update(Ingrediente i, Ingrediente newIngrediente) {
		i.setNome(newIngrediente.getNome());
		i.setDescrizione(newIngrediente.getDescrizione());
		i.setOrigine(newIngrediente.getOrigine());
		this.ir.save(i);
	}

	public Optional<Ingrediente> findById(Long idIngrediente) {
		return this.ir.findById(idIngrediente);
	}

	public boolean alreadyExists(Ingrediente target) {
		return this.ir.findByNomeAndOrigine(target.getNome(), target.getOrigine());
	}
}
