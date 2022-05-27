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
	
	
	//visualizza un buffet di uno specifico chef
	@GetMapping("/chef/{idChef}/buffet/{idBuffet}")
	public String getBuffet(@PathVariable("id") Long idChef, @PathVariable("id") Long idBuffet, Model model) {
		Chef c = this.cs.findById(idChef).get();
		Buffet b = this.bs.findById(idBuffet).get();
		model.addAttribute("chef", c);
		model.addAttribute("buffet", b);
		return "buffet/buffet.html";
	}
	
	//visualizza l'elenco dei buffet di uno schef
	@GetMapping("/chef/{id}/elencoBuffet")
	public String showBuffetList(@PathVariable("id") Long id, Model model) {
		Chef chef = this.cs.findById(id).get();
		model.addAttribute("chef", chef);
		model.addAttribute("buffetList", chef.getBuffet());
		return "buffet/elencoBuffet.html";
	}
	
	//vai alla pagine newBuffet per aggiungere un nuovo buffet
	@GetMapping("/chef/{id}/aggiungiBuffet") 
	public String newBuffet(@PathVariable("id") Long id, Model model) {
		Chef chef = this.cs.findById(id).get();
		model.addAttribute("chef", chef);
		model.addAttribute("buffet", new Buffet());
		return "buffet/newBuffet.html";
	}
	
	//configurazione form di pagina newBuffet per aggiungere un nuovo buffet
	@PostMapping("/chef/{id}/aggiungiBuffet")
	public String newBuffet(@Valid @ModelAttribute Buffet buffet, BindingResult buffetBindingResult, @PathVariable Long id, Model model) {
		Chef chef = cs.findById(id).get();
		bv.validate(buffet, buffetBindingResult);
		
		if(!buffetBindingResult.hasErrors()) {
			buffet.setChef(chef);
			chef.getBuffet().add(buffet);
			cs.save(chef);
			return "buffet/elencoBuffet.html";
		}
		return "buffet/newBuffet.html";
	}
	
}
