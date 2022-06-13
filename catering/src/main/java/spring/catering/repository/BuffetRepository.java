package spring.catering.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import spring.catering.model.Buffet;
import spring.catering.model.Chef;

public interface BuffetRepository extends CrudRepository<Buffet, Long> {
	
	public boolean existsByNomeAndDescrizione(String nome, String descrizione);

	public Optional<Buffet> findByNome(String Nome);
	
	public List<Buffet> findAllByChef(Chef chef);
	
}
