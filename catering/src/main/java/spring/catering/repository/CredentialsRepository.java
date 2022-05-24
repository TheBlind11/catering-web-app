package spring.catering.repository;

import org.springframework.data.repository.CrudRepository;

import spring.catering.model.Credentials;
import spring.catering.model.Utente;

public interface CredentialsRepository extends CrudRepository<Credentials, Long>{

	public boolean existsByUsurnameAndUtente(String usurname, Utente utente);
	
}
