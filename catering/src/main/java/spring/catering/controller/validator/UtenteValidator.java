package spring.catering.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import spring.catering.model.Utente;
import spring.catering.service.UtenteService;

@Component
public class UtenteValidator implements Validator {

	@Autowired
	private UtenteService us;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Utente.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		if (this.us.alreadyExists((Utente)target)) {
			errors.reject("utente.duplicato");
		}
	}

}
