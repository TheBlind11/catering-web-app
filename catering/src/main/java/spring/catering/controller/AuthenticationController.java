package spring.catering.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import spring.catering.controller.validator.CredentialsValidator;
import spring.catering.controller.validator.UtenteValidator;
import spring.catering.model.Credentials;
import spring.catering.model.Utente;
import spring.catering.service.CredentialsService;

@Controller
public class AuthenticationController {
	
	@Autowired
	private CredentialsService cs;
	
	@Autowired
	private CredentialsValidator cv;
	
	@Autowired
	private UtenteValidator uv;
	
	@PostMapping("/register")
	public String addCredentials(@Valid @ModelAttribute ("utente") Utente utente, BindingResult utenteBindingResult, @Valid @ModelAttribute("credenziali") Credentials credenziali, BindingResult credenzialiBindingResult, Model model) {
		
		utente.setCognome(utente.getCognome().toLowerCase());
		utente.setNome(utente.getNome().toLowerCase());
		
		this.uv.validate(utente, utenteBindingResult);
		this.cv.validate(credenziali, credenzialiBindingResult);
		
		if(!credenzialiBindingResult.hasErrors() && !utenteBindingResult.hasErrors()) {
			credenziali.setUtente(utente);  
			cs.save(credenziali);  // this also stores the User, thanks to Cascade.ALL policy
			return "index.html";
		}
		
		return "register.html";
	}
	
	@GetMapping("/register")
	public String getCredentials(Model model) {
		model.addAttribute("credenziali", new Credentials());
		model.addAttribute("utente", new Utente());
		return "register.html";
	}
	
	
	@GetMapping("/login")
    public String showLoginForm(Model model) {
        return "loginForm.html";
    }
	
	@GetMapping("/default")
    public String defaultAfterLogin(Model model) {
        UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Credentials credentials = cs.getCredentials(userDetails.getUsername());
        
        if(credentials.getRole().equals(Credentials.ADMIN_ROLE)) {
            return "dashboard.html";
        }
        
        return "index.html";
    }
}
