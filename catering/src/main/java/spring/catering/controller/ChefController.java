package spring.catering.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import spring.catering.controller.validator.ChefValidator;
import spring.catering.model.Chef;
import spring.catering.service.ChefService;

@Controller
public class ChefController {
	
	@Autowired
	private ChefService cs;
	
	@Autowired
	private ChefValidator cv;
	
	
	//vai a pagina newChef.html
	@GetMapping("/aggiungiChef") 
	public String newChef(Model model) {
		model.addAttribute("chef", new Chef());
		return "newChef.html";
	}
	
	//configurazione form per addChef in pagina newChef.html
	@PostMapping("/aggiungiChef")
	public String newChef(@Valid @ModelAttribute Chef chef, BindingResult bindingResult, Model model) {
		cv.validate(chef, bindingResult);
		
		if(!bindingResult.hasErrors()) {
			cs.save(chef);
			return "chef.html";
		}
			
		return "newChef.html";
	}
	
	//visualizza info chef 
	@GetMapping("/chef/{id}")
	public String getChef(@PathVariable("id") Long id, Model model) {
		Chef chef = cs.findById(id).get();
		model.addAttribute("chef", chef);
		return "chef.html";
	}
	
	//elimina uno chef
	@GetMapping("/deleteChef/{id}")
	public String deleteChef(@PathVariable("id") Long id, Model model) {
		this.cs.delete(id);
		return "elencoChef.html";
	}
	
	//vai alla pagina di mofifica di uno chef
	@GetMapping("/modificaChef/{id}")
	public String modificaChef(@PathVariable("id") Long id, Model model) {
		Chef chef = cs.findById(id).get();
		model.addAttribute("chef", chef);
		model.addAttribute("newChef", new Chef());
		return "modificaChef.html";
	}
	
	//configurazione form di modifica alla pagina di modificaChef
	@PostMapping("/modificaChef/{id}")
	public String modificaChefForm(@PathVariable("id") Long id, @Valid @ModelAttribute Chef newChef, BindingResult chefBindingResult, Model model) {
		Chef chef = this.cs.findById(id).get();
		this.cv.validate(newChef, chefBindingResult);
		
		if(!chefBindingResult.hasErrors()) {
			chef.setNome(newChef.getNome());
			chef.setCognome(newChef.getCognome());
			chef.setNazionalita(newChef.getNazionalita());
			this.cs.save(chef);
			return "elencoChef.html";
		}
		return "modificaChef.html";
	}
	
	@GetMapping("/elencoChef")
	public String getAllChef(Model model) {
		List<Chef> chefs = this.cs.findAllChef();
		model.addAttribute("chefs", chefs);
		
		return "elencoChef.html";
	}
}

