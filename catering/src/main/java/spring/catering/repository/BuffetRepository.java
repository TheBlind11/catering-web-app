package spring.catering.repository;

import org.springframework.data.repository.CrudRepository;

import spring.catering.model.Buffet;

public interface BuffetRepository extends CrudRepository<Buffet, Long> {
	
}
