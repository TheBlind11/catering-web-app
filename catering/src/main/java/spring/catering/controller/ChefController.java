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
	
	//visualizza info chef 
	@GetMapping("/chef/{id}")
	public String getChef(@PathVariable("id") Long id, Model model) {
		Chef chef = cs.findById(id).get();
		model.addAttribute("chef", chef);

		return "chef/chef.html";
	}
	
	//visualizza elenco chef nel db
	@GetMapping("/elencoChef")
	public String getAllChef(Model model) {
		List<Chef> chefs = this.cs.findAllChef();
		model.addAttribute("chefs", chefs);
		
		return "chef/elencoChef.html";
	}
	
	//vai a pagina newChef.html
	@GetMapping("/aggiungiChef") 
	public String newChef(Model model) {
		model.addAttribute("chef", new Chef());
		return "chef/newChef.html";
	}
	
	//configurazione form per addChef in pagina newChef.html
	@PostMapping("/aggiungiChef")
	public String newChef(@Valid @ModelAttribute Chef chef, BindingResult bindingResult, Model model) {
		cv.validate(chef, bindingResult);
		
		if(!bindingResult.hasErrors()) {
			cs.save(chef);
			return "chef/chef.html";
		}
			
		return "chef/newChef.html";
	}
	
	//elimina uno chef
	@GetMapping("/deleteChef/{id}")
	public String deleteChef(@PathVariable("id") Long id, Model model) {
		this.cs.delete(id);
		model.addAttribute("chefs", this.cs.findAllChef());
		return "chef/elencoChef.html";
	}
	
	//vai alla pagina di mofifica di uno chef
	@GetMapping("/modificaChef/{id}")
	public String modificaChef(@PathVariable("id") Long id, Model model) {
		Chef chef = cs.findById(id).get();
		model.addAttribute("chef", chef);
		return "chef/modificaChef.html";
	}
	
	//configurazione form di modifica alla pagina di modificaChef
	@PostMapping("/modificaChef/{id}")
	public String modificaChefForm(@PathVariable("id") Long id, @Valid @ModelAttribute Chef newChef, BindingResult chefBindingResult, Model model) {
		this.cv.validate(newChef, chefBindingResult);
		
		if(!chefBindingResult.hasErrors()) {
			this.cs.update(this.cs.findById(id).get(), newChef);
			List<Chef> chefs = this.cs.findAllChef();
			model.addAttribute("chefs", chefs);
			return "chef/elencoChef.html";
		}
		return "chef/modificaChef.html";
	}
	
}

