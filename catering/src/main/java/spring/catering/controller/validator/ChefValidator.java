package spring.catering.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import spring.catering.model.Chef;
import spring.catering.service.ChefService;

@Component
public class ChefValidator implements Validator {

	@Autowired
	private ChefService cs;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Chef.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		if(this.cs.alreadyExists((Chef)target))
			errors.reject("chef.duplicato");
	}

}
