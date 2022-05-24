package spring.catering.repository;

import org.springframework.data.repository.CrudRepository;

import spring.catering.model.Utente;

public interface UtenteRepository extends CrudRepository<Utente, Long>{

	public boolean existsByNomeAndCognome(String nome, String cognome);
	
}
