package spring.catering.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import spring.catering.service.BuffetService;
import spring.catering.service.ChefService;

@Controller
public class GeneralController {
	
	@Autowired
	private ChefService cs;
	
	@Autowired
	private BuffetService bs;
	
	@GetMapping("/")
	public String goHome(Model model) {
		model.addAttribute("chefs", this.cs.findAllChef());
		model.addAttribute("buffetList", this.bs.findAll());
		return "index.html";
	}
}
