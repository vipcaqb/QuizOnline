package hl.quizonline.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import hl.quizonline.entity.Account;
import hl.quizonline.enumrable.Gender;
import hl.quizonline.service.AccountService;

@Controller
public class AccountController {
	@Autowired
	AccountService accountService;
	
	@GetMapping("/login")
	public String loginPage() {
		return "login";
	}
	
	@GetMapping("/register")
	public String registerForm(Model model) {
		return "register";
	}
	
	@PostMapping("/register")
	public String register(@ModelAttribute Account acc,Model model) {
		System.out.println("post register");
		acc.setRole("ROLE_STUDENT");
		acc.setEnable(true);
		accountService.registerAccount(acc);
		
		return "redirect:/profile";
	}
	
	@GetMapping("/profile")
	public String showProfile(Model model){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			
		    String currentUserName = authentication.getName();
		    Account account = accountService.getAccountByUsername(currentUserName).get();
		    
		    model.addAttribute("account", account);
		    return "manage/profile";
		}
		
		
		
		return "redirect:/login";
	}
	
	@GetMapping("/history")
	public String showHistory(Model model) {
		return "404";
	}
}
