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

import spring.catering.controller.validator.PiattoValidator;
import spring.catering.model.Buffet;
import spring.catering.model.Piatto;
import spring.catering.service.BuffetService;
import spring.catering.service.PiattoService;

@Controller
public class PiattoController {
	
	@Autowired
	private PiattoService ps;
	
	/* @Autowired
	private PiattoValidator pv; */
	
	@Autowired
	private BuffetService bs;
	
	
	//visualizza un piatto di uno specifico buffet
	@GetMapping("/piatto/{idPiatto}")
	public String getPiatto(@PathVariable("idPiatto") Long idPiatto, Model model) {
		Piatto piatto = this.ps.findById(idPiatto).get();
		model.addAttribute("piatto", piatto);
		
		return "piatto/piatto.html";
	}
	
	//visualizza l'elenco dei piatto di un buffet
	@GetMapping("/buffet/{id}/elencoPiatti")
	public String getPiatti(@PathVariable("id") Long id, Model model) {
		Buffet buffet = this.bs.findById(id).get();
		model.addAttribute("buffet", buffet);
		model.addAttribute("piatti", buffet.getPiatti());
		
		return "buffet/buffet.html";
	}
	
	//vai alla pagina newPiatto per aggiungere un nuovo piatto ad uno specifico buffet
	@GetMapping("/admin/buffet/{id}/aggiungiPiatto")
	public String addPiatto(@PathVariable("id") Long id, Model model) {
		Buffet buffet = this.bs.findById(id).get();
		model.addAttribute("buffet", buffet);
		model.addAttribute("piatto", new Piatto());
		
		return "admin/newPiatto.html";
	}
	
	//configurazione form di pagina newPiatto per aggiungere un nuovo piatto ad un buffet
	@PostMapping("/admin/buffet/{id}/aggiungiPiatto")
	public String addPiattoForm(@PathVariable("id") Long id, @Valid @ModelAttribute("piatto") Piatto piatto, BindingResult bindingResult, Model model) {
		Buffet buffet = this.bs.findById(id).get();
		//this.pv.validate(piatto, bindingResult);
		
		if(!bindingResult.hasErrors()) {
			buffet.getPiatti().add(piatto);
			
			this.bs.save(buffet);
			model.addAttribute("buffet", buffet);
			
			return "buffet/buffet.html";
		}
		
		return "admin/newPiatto.html";
	}
	
	//elimina il piatto di un buffet
	@GetMapping("/admin/buffet/{idBuffet}/eliminaPiatto/{idPiatto}")
	public String deletePiatto(@PathVariable("idBuffet") Long idBuffet, @PathVariable("idPiatto") Long idPiatto, Model model) {
		Buffet buffet = this.bs.findById(idBuffet).get();
		Piatto piatto = this.ps.findById(idPiatto).get();
		
		this.ps.delete(piatto);
		model.addAttribute("buffet", buffet);
		
		return "buffet/buffet.html";
	}
	
	//vai alla pagina di modifica di un piatto di un buffet
	@GetMapping("/admin/buffet/{idBuffet}/modificaPiatto/{idPiatto}")
	public String modificaPiatto(@PathVariable("idBuffet") Long idBuffet, @PathVariable("idPiatto") Long idPiatto, Model model) {
		Buffet buffet = this.bs.findById(idBuffet).get();
		Piatto piatto = this.ps.findById(idPiatto).get();
		
		model.addAttribute("buffet", buffet);
		model.addAttribute("piatto", piatto);
		
		return "admin/modificaPiatto.html";
	}
	
	//configurazione form della pagina di modifica di un piatto di un buffet
	@PostMapping("/admin/buffet/{idBuffet}/modificaPiatto/{idPiatto}")
	public String modificaPiattoForm(@PathVariable("idBuffet") Long idBuffet, @PathVariable("idPiatto") Long idPiatto, 
									@Valid @ModelAttribute("newPiatto") Piatto newPiatto, BindingResult bindingResult, Model model) {
		Buffet buffet = this.bs.findById(idBuffet).get();
		Piatto piatto = this.ps.findById(idPiatto).get();
		
		//this.pv.validate(newPiatto, bindingResult);
		
		if(!bindingResult.hasErrors()) {
			this.ps.update(piatto, newPiatto);
			this.bs.save(buffet);
			model.addAttribute("buffet", buffet);
			return "buffet/buffet.html";
		}
		
		return "admin/modificaPiatto.html";
	}
	
}
