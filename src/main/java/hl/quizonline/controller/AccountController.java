package hl.quizonline.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	@PostMapping("/login")
	@ResponseBody
	public String login(@RequestParam String username, @RequestParam String password) {
		Boolean loginSuccess = accountService.checkLogin(username, password);
		if(loginSuccess) {
			return "thanh cong";
		}
		else {
			return "that bai";
		}
	}
	
	@GetMapping("/register")
	public String registerForm(Model model) {
		model.addAttribute("message","trang dang ky");
		return "index";
	}
	
	@PostMapping("/register")
	public String register(@ModelAttribute Account account,Model model) {
		Account acc = new Account();
		acc.setUsername("vipcaqb");
		acc.setPassword("1234");
		acc.setFullname("Hoang Loc");
		acc.setGender(Gender.MALE);
		accountService.registerAccount(acc);
		model.addAttribute("message","dang ky thanh cong");
		return "index";
	}
	
}
