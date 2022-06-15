package spring.catering.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import spring.catering.model.Ingrediente;
import spring.catering.service.IngredienteService;

@Component
public class IngredienteValidator implements Validator {

	@Autowired
	private IngredienteService is;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Ingrediente.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		if(is.alreadyExists((Ingrediente)target))
			errors.reject("ingrediente.duplicato");
	}

}
