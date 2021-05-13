package hl.quizonline.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;

import hl.quizonline.config.MyConstances;
import hl.quizonline.entity.Account;
import hl.quizonline.entity.ExamPackage;
import hl.quizonline.entity.QuestionPackage;
import hl.quizonline.model.ExamTable;
import hl.quizonline.model.QuestionTable;
import hl.quizonline.service.AccountService;
import hl.quizonline.service.ExamPackageService;
import hl.quizonline.service.QuestionPackageService;

@Controller
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping("/manage")
public class AdminController {
	@Autowired
	AccountService accountService;
	
	@Autowired 
	ExamPackageService examPackageService;
	
	@Autowired
	QuestionPackageService questionPackageService;
	
	//Manage account form
	@GetMapping(value = {"/account","/account/{pageNo}"})
	public String manageForm(@PathVariable(name ="pageNo",required = false) Integer pageNo,
			@RequestParam(name = "key", required = false) String key,
			Model model) {
		if(pageNo==null) pageNo = 1;
		Page<Account> page = null;
		List<Account> accountList = null;
		
		if(key == null || key.isBlank()) {
			page = accountService.getAllAccount(pageNo, MyConstances.PAGE_SIZE);
			accountList= page.getContent();
		}
		else {
			page = accountService.searchAllAccount(pageNo, MyConstances.PAGE_SIZE, key);
			accountList = page.getContent();
		}
		
		System.out.println("gioitinh "+ accountList.get(0).getGender());
		
		model.addAttribute("accountList", accountList);
		model.addAttribute("page", page);
		return "/manage/manage-account";
	}
	
	@GetMapping(value ="/account/lockaccount/{username}")
	public String lockOrUnlockAccount(@PathVariable(name = "username") String username) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
		    Account account = accountService.getAccountByUsername(username).get();
			accountService.lockAccount(account);
			System.out.println("Đã khóa tài khoản có username = " + username);
			return "redirect:/manage/account";
		}
		
		return "redirect:/login";
	}
	
	@GetMapping("/account/getExamList/{username}")
	@ResponseBody
	public ResponseEntity<List<ExamTable>> getExamTableList(@PathVariable("username") String username, @RequestParam("pageNo") Integer pageNo) {
		if(pageNo == null) pageNo = 1;
		System.out.println("Lấu danh sách đề thi");
		Page<ExamPackage> page = examPackageService.getPageByUsername(username, pageNo, MyConstances.PAGE_SIZE);
		List<ExamPackage> examPackageList = page.getContent();
		List<ExamTable> examTableList = new ArrayList<ExamTable>();
		for(int i =0;i<examPackageList.size();i++) {
			examTableList.add(new ExamTable(examPackageList.get(i)));
		}
		
		return ResponseEntity.ok().body(examTableList);
	}
	
	@GetMapping("/account/getQuestionPackageList/{username}")
	@ResponseBody
	public ResponseEntity<List<QuestionTable>> getQuestionTableList(@PathVariable("username") String username) {
		System.out.println("Lấu danh sách đề thi");
		
		List<QuestionPackage> questionPackageList = questionPackageService.getList(username);
		
		List<QuestionTable> questionTableList = new ArrayList<QuestionTable>();
		for(int i = 0; i< questionPackageList.size();i++) {
			questionTableList.add(new QuestionTable(questionPackageList.get(i)));
		}
		
		return ResponseEntity.ok().body(questionTableList);
	}

	@GetMapping("/overview")
	public String showOverview() {
		return "manage/overview";
	}
}
