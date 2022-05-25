package spring.catering.repository;

import org.springframework.data.repository.CrudRepository;

import spring.catering.model.Chef;

public interface ChefRepository extends CrudRepository<Chef, Long>{

	public boolean existsByNomeAndCognomeAndNazionalita(String nome, String cognome, String nazionalita);

}
