package hl.quizonline.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import hl.quizonline.config.MyConstances;
import hl.quizonline.entity.Account;
import hl.quizonline.service.AccountService;

@Controller
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping("/manage/account")
public class AdminController {
	@Autowired
	AccountService accountService;
	
	//Manage account form
	@GetMapping(value = {"","/{pageNo}"})
	public String manageForm(@PathVariable(name ="pageNo",required = false) Integer pageNo,
			Model model) {
		if(pageNo==null) pageNo = 1;
		Page<Account> page = accountService.getAllAccount(pageNo, MyConstances.PAGE_SIZE);
		List<Account> accountList = page.getContent();
		
		System.out.println("gioitinh "+ accountList.get(0).getGender());
		
		model.addAttribute("accountList", accountList);
		model.addAttribute("page", page);
		return "/manage/manage-account";
	}
}
