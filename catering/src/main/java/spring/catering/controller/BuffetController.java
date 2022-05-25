package spring.catering.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import spring.catering.controller.validator.BuffetValidator;
import spring.catering.model.Buffet;
import spring.catering.model.Chef;
import spring.catering.service.BuffetService;
import spring.catering.service.ChefService;

@Controller
public class BuffetController {
	
	@Autowired
	private BuffetService bs;
	
	@Autowired
	private BuffetValidator bv;
	
	@Autowired
	private ChefService cs;
	
	@PostMapping("/chef/{id}/aggiungiBuffet")
	public String newBuffet(@Valid @ModelAttribute Buffet buffet, BindingResult buffetBindingResult, @PathVariable Long id, BindingResult chefBindingResult, Model model) {
		Chef chef = this.cs.findById(id).get();
		bv.validate(buffet, buffetBindingResult);
		
		if(!buffetBindingResult.hasErrors()) {
			buffet.setChef(chef);
			bs.save(buffet);
			return "elencoBuffet.html";
		}
		return "newBuffet.html";
	}
	
	@GetMapping("/aggiungiBuffet") 
	public String newBuffet(Model model) {
		model.addAttribute("buffet", new Buffet());
		return "newBuffet.html";
	}
	
}
