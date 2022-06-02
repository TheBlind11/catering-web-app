package spring.catering.repository;

import org.springframework.data.repository.CrudRepository;

import spring.catering.model.Piatto;

public interface PiattoRepository extends CrudRepository<Piatto, Long>{

	public boolean existsByNomeAndDescrizione(String nome, String descrizione);

}
