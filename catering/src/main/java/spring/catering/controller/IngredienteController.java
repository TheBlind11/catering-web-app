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

import spring.catering.model.Ingrediente;
import spring.catering.model.Piatto;
import spring.catering.service.IngredienteService;
import spring.catering.service.PiattoService;

@Controller
public class IngredienteController {
	
	@Autowired
	private IngredienteService is;
	
	@Autowired
	private PiattoService ps;
	
	
	@GetMapping("/piatto/{id}/elencoIngredienti")
	public String getIngredienti(@PathVariable("id") Long id, Model model) {
		Piatto piatto = this.ps.findById(id).get();
		model.addAttribute("piatto", piatto);
		model.addAttribute("ingredienti", piatto.getIngredienti());
		
		return "ingrediente/elencoIngredientiDelPiatto.html";
	}
	
	@GetMapping("/admin/piatto/{idPiatto}/aggiungiIngrediente")
	public String addIngrediente(@PathVariable("idPiatto") Long idPiatto, Model model) {
		Piatto piatto = this.ps.findById(idPiatto).get();
		model.addAttribute("piatto", piatto);
		model.addAttribute("ingrediente", new Ingrediente());
		
		return "admin/newIngrediente.html";
	}
	
	@PostMapping("/admin/piatto/{idPiatto}/aggiungiIngrediente")
	public String addIngrediente(@PathVariable("idPiatto") Long idPiatto, @Valid @ModelAttribute("ingrediente") Ingrediente ingrediente, BindingResult bindingResult, Model model) {
		Piatto piatto = this.ps.findById(idPiatto).get();
		
		if(!bindingResult.hasErrors()) {
			List<Ingrediente> ingredienti = piatto.getIngredienti();
			ingredienti.add(ingrediente);
			this.ps.save(piatto);
			model.addAttribute("piatto", piatto);
			model.addAttribute("ingredienti", ingredienti);
			
			return "piatto/piatto.html";
		}
		
		return "admin/newIngrediente.html";
	}
	
	@GetMapping("/admin/piatto/{idPiatto}/eliminaIngrediente/{idIngrediente}")
	public String deleteIngrediente(@PathVariable("idPiatto") Long idPiatto, @PathVariable("idIngrediente") Long idIngrediente, Model model) {
		Piatto piatto = this.ps.findById(idPiatto).get();
		Ingrediente ingrediente = this.is.findById(idIngrediente).get();
		this.is.delete(ingrediente);
		
		model.addAttribute("piatto", piatto);
		model.addAttribute("ingredienti", piatto.getIngredienti());
		
		return "piatto/piatto.html";
	}
	
	@GetMapping("/admin/piatto/{idPiatto}/modificaIngrediente/{idIngrediente}")
	public String modificaIngrediente(@PathVariable("idPiatto") Long idPiatto, @PathVariable("idIngrediente") Long idIngrediente, Model model) {
		Piatto piatto = this.ps.findById(idPiatto).get();
		Ingrediente ingrediente = this.is.findById(idIngrediente).get();
		model.addAttribute("piatto", piatto);
		model.addAttribute("ingrediente", ingrediente);
		
		return "admin/modificaIngrediente.html";
	}
	
	@PostMapping("/admin/piatto/{idPiatto}/modificaIngrediente/{idIngrediente}")
	public String modificaIngrediente(@PathVariable("idPiatto") Long idPiatto, @PathVariable("idIngrediente") Long idIngrediente, 
									@Valid @ModelAttribute("newIngrediente") Ingrediente newIngrediente, BindingResult bindingResult, Model model) {
		Piatto piatto = this.ps.findById(idPiatto).get();
		Ingrediente ingrediente = this.is.findById(idIngrediente).get();
		
		if(!bindingResult.hasErrors()) {
			this.is.update(ingrediente, newIngrediente);
			model.addAttribute("piatto", piatto);
			
			return "piatto/piatto.html";
		}
		
		return "admin/modificaIngrediente.html";
	}
}
