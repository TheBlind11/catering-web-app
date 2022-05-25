package spring.catering.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import spring.catering.model.Buffet;

public interface BuffetRepository extends CrudRepository<Buffet, Long> {
	
	public boolean existsByNome(String nome);

	public Optional<Buffet> findByNome(String Nome);
	
}
