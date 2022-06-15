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
	
	
	@GetMapping("/elencoBuffet")
	public String getBuffetList(Model model) {
		List<Buffet> list = this.bs.findAll();
		model.addAttribute("buffetList", list);
		
		return "buffet/allBuffet.html";
	}
	
	@GetMapping("/buffet/{id}")
	public String getBuffet(@PathVariable("id") Long id, Model model) {
		Buffet buffet = this.bs.findById(id).get();
		model.addAttribute("buffet", buffet);
		
		return "buffet/buffet.html";
	}
	
	//vai alla pagine newBuffet per aggiungere un nuovo buffet ad uno chef
	@GetMapping("/admin/chef/{id}/aggiungiBuffet") 
	public String newBuffet(@PathVariable("id") Long id, Model model) {
		Chef chef = this.cs.findById(id).get();
		
		model.addAttribute("chef", chef);
		model.addAttribute("buffet", new Buffet());
		
		return "admin/newBuffet.html";
	}
	
	//configurazione form di pagina newBuffet per aggiungere un nuovo buffet ad uno chef
	@PostMapping("/admin/chef/{id}/aggiungiBuffet")
	public String newBuffet(@Valid @ModelAttribute("buffet") Buffet buffet, BindingResult buffetBindingResult, @PathVariable("id") Long id, Model model) {
		
		Chef chef = cs.findById(id).get();
		bv.validate(buffet, buffetBindingResult);
		
		if(!buffetBindingResult.hasErrors()) {
			buffet.setChef(chef);
			chef.getBuffet().add(buffet);
			
			cs.save(chef);
			
			model.addAttribute("chef", chef);
			
			return "chef/chef.html";
		}
		return "admin/newBuffet.html";
	}
	
	//elimina un buffet di uno chef
	@GetMapping("/admin/eliminaBuffet/{idBuffet}")
	public String deleteBuffet(@PathVariable("idBuffet") Long idBuffet, Model model) {
		
		Buffet buffet = this.bs.findById(idBuffet).get();
		
		this.bs.delete(buffet);
		
		model.addAttribute("chef", buffet.getChef());
		
		return "chef/chef.html";
	}
	
	//vai alla pagina di modifica di un buffet di uno chef
	@GetMapping("/admin/modificaBuffet/{idBuffet}")
	public String editBuffet(@PathVariable("idBuffet") Long idBuffet, Model model) {
		Buffet buffet = this.bs.findById(idBuffet).get();
		model.addAttribute("buffet", buffet);
		
		return "admin/modificaBuffet.html";
	}
	
	//configurazione form della pagina di modifica di un buffet di uno chef
	@PostMapping("/admin/modificaBuffet/{idBuffet}")
	public String editBuffet(@PathVariable("idBuffet") Long idBuffet, 
							@Valid @ModelAttribute("newBuffet") Buffet newBuffet, BindingResult buffetBindingResult, Model model) {
		Buffet buffet = this.bs.findById(idBuffet).get();
		
		this.bv.validate(newBuffet, buffetBindingResult);
		
		if(!buffetBindingResult.hasErrors()) {
			this.bs.update(buffet, newBuffet);
			model.addAttribute("chef", buffet.getChef());
			
			return "chef/chef.html";
		}
		model.addAttribute("buffet", buffet);
		return "admin/modificaBuffet.html";
	}
}
