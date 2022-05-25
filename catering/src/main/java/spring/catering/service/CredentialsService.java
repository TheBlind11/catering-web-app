package spring.catering.service;

import java.util.Optional;

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
	public Credentials save(Credentials c) {
		c.setRole(Credentials.DEFAULT_ROLE);
		c.setPassword(this.pe.encode(c.getPassword()));
		return cr.save(c);
	}
	
    public Credentials getCredentials(Long id) {
        Optional<Credentials> result = this.cr.findById(id);
        return result.orElse(null);
    }

    public Credentials getCredentials(String username) {
        Optional<Credentials> result = this.cr.findByUsername(username);
        return result.orElse(null);
    }

	public boolean alreadyExists(Credentials target) {
		return cr.existsByUsernameAndUtente(target.getUsername(), target.getUtente());
	}
	
}
