package spring.catering.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import spring.catering.model.Utente;
import spring.catering.repository.UtenteRepository;

@Service
public class UtenteService {
	
	@Autowired
	private UtenteRepository ur;
	
    @Transactional
    public void save(Utente utente) {
        this.ur.save(utente);
    }
	
	public boolean alreadyExists(Utente u) {
		return ur.existsByNomeAndCognome(u.getNome(), u.getCognome());
	}
}
