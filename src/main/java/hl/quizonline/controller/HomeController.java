package hl.quizonline.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	@GetMapping(value = {"/","/home"})
	public String showMyHome(Model model) {
		model.addAttribute("message", "Here is my home :))");
		return "index";
	}
}
