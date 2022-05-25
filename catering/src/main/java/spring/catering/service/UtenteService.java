package spring.catering.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public Utente save(Utente utente) {
        return this.ur.save(utente);
    }
    
    public Utente getUser(Long id) {
        Optional<Utente> result = this.ur.findById(id);
        return result.orElse(null);
    }
	
    public List<Utente> getAllUsers() {
        List<Utente> result = new ArrayList<>();
        Iterable<Utente> iterable = this.ur.findAll();
        for(Utente user : iterable)
            result.add(user);
        return result;
    }
    
	public boolean alreadyExists(Utente u) {
		return ur.existsByNomeAndCognome(u.getNome(), u.getCognome());
	}
}
