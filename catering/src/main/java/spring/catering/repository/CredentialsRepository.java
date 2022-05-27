package spring.catering.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import spring.catering.model.Credentials;

public interface CredentialsRepository extends CrudRepository<Credentials, Long>{

	public boolean existsByUsername(String username);

	public Optional<Credentials> findByUsername(String username);
	
}
