package spring.catering.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import spring.catering.model.Credentials;
import spring.catering.repository.CredentialsRepository;

@Service
public class CredentialsService {
	
	@Autowired
	private CredentialsRepository cr;
	
	@Autowired
	private PasswordEncoder pe;
	
	@Transactional
	public void save(Credentials c) {
		c.setRole(Credentials.DEFAULT_ROLE);
		c.setPassword(this.pe.encode(c.getPassword()));
		cr.save(c);
	}

	public boolean alreadyExists(Credentials target) {
		return cr.existsByUsurnameAndUtente(target.getUsurname(), target.getUtente());
	}
	
}
