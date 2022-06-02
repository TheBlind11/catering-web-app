package spring.catering.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import spring.catering.model.Piatto;
import spring.catering.service.PiattoService;

@Component
public class PiattoValidator implements Validator {

	@Autowired
	private PiattoService ps;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Piatto.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		if(ps.alreadyExists((Piatto)target))
			errors.reject("piatto.duplicato");
	}

}
