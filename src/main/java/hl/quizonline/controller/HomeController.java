package hl.quizonline.controller;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	@GetMapping(value = {"/","/home"})
	public String showMyHome(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
		    String currentUserName = authentication.getName();
		    System.out.println(currentUserName);
		}
		return "index";
	}
	
	@GetMapping(value = "/listexam")
	public String showListExamination() {
		return "examlist";
	}
	
	@GetMapping(value = "/examdetail/{examPackageID}")
	public String examDetailShow(Model model) {
		return "exam-detail";
	}
	
}
