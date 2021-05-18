package hl.quizonline.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;

import hl.quizonline.config.MyConstances;
import hl.quizonline.entity.Account;
import hl.quizonline.entity.Category;
import hl.quizonline.entity.ExamPackage;
import hl.quizonline.entity.QuestionPackage;
import hl.quizonline.model.ExamTable;
import hl.quizonline.model.LineChartModel;
import hl.quizonline.model.QuestionTable;
import hl.quizonline.repository.CategoryRepository;
import hl.quizonline.service.AccountService;
import hl.quizonline.service.CategoryService;
import hl.quizonline.service.ExamPackageService;
import hl.quizonline.service.QuestionPackageService;
import hl.quizonline.service.impl.MyHelper;

// TODO: Auto-generated Javadoc
/**
 * The Class AdminController.
 */
@Controller
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping("/manage")
public class AdminController {
	
	/** The account service. */
	@Autowired
	AccountService accountService;
	
	/** The exam package service. */
	@Autowired 
	ExamPackageService examPackageService;
	
	/** The category service. */
	@Autowired
	CategoryService categoryService;
	
	/** The question package service. */
	@Autowired
	QuestionPackageService questionPackageService;
	
	//Manage account form
	
	/**
	 * Manage form.
	 *
	 * @param pageNo the page no
	 * @param key the key
	 * @param model the model
	 * @return the string
	 */
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
	
	/**
	 * Lock or unlock account.
	 *
	 * @param username the username
	 * @return the string
	 */
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
	
	/**
	 * Gets the exam table list.
	 *
	 * @param username the username
	 * @param pageNo the page no
	 * @return the exam table list
	 */
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
	
	/**
	 * Gets the question table list.
	 *
	 * @param username the username
	 * @return the question table list
	 */
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

	/**
	 * Show overview.
	 *
	 * @return the string
	 */
	@GetMapping("/overview")
	public String showOverview(@RequestParam(name= "year", required = false) Integer year,Model model) {
		if(year == null) year = Calendar.getInstance().get(Calendar.YEAR);
		int maxYear = Calendar.getInstance().get(Calendar.YEAR);
		long totalAccount = accountService.countAll();
		long totalViews = examPackageService.getTotalView();
		long totalExams = examPackageService.getTotalExamPackage();
		long totalDoExamTimes = examPackageService.getTotalDoExamTime();
		//De thi theo danh muc
		List<Category> categoryList = categoryService.getAll();
		
		//sap xep giam dan theo so luong de thi
		for(int i = 0; i< categoryList.size()-1;i++) {
			for(int j= i+1; j<categoryList.size();j++) {
				if(categoryList.get(i).getExamPackages().size()<categoryList.get(j).getExamPackages().size()) {
					Category temp = categoryList.get(i);
					categoryList.set(i, categoryList.get(j));
					categoryList.set(j, temp);
				}
			}
		}
		MyHelper myHelper = new MyHelper();
		
		//Line chart
		LineChartModel lineChart = examPackageService.getLineChartData(year);
		
		String dataLineChart = lineChart.getJSList();
		//Lấy năm nhỏ nhất có trong db
		long minYear = examPackageService.getMinYear()+1900;
		if(minYear>year) minYear = year;
		if(minYear>maxYear) minYear = maxYear;
		
		
		model.addAttribute("minYear", minYear);
		model.addAttribute("selectedYear", year);
		model.addAttribute("maxYear", maxYear);
		model.addAttribute("lineChart", lineChart);
		model.addAttribute("dataLineChart", dataLineChart);
		model.addAttribute("myHelper", myHelper);
		model.addAttribute("categoryList", categoryList);
		model.addAttribute("totalAccount", totalAccount);
		model.addAttribute("totalViews", totalViews);
		model.addAttribute("totalExams", totalExams);
		model.addAttribute("totalDoExamTimes", totalDoExamTimes);
		return "manage/overview";
	}

	/**
	 * Show category list.
	 *
	 * @param pageNo the page no
	 * @param key the key
	 * @param model the model
	 * @return the string
	 */
	@GetMapping(value = {"/category","/category/{pageNo}"})
	public String showCategoryList(@PathVariable(name = "pageNo", required =false) Integer pageNo,
			@RequestParam(name= "key",required = false) String key,
			Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			String currentUserName = authentication.getName();
			if(key==null) key = "";
			if(pageNo== null) pageNo=1;
			Page<Category> page = categoryService.searchByName(key, pageNo);
			List<Category> categoryList = page.getContent();
			
			model.addAttribute("page", page);
			model.addAttribute("categoryList", categoryList);
			return "admin/admin-category";
		}
		return "redirect:/login";
	}
	
	/**
	 * Creates the category.
	 *
	 * @param categoryName the category name
	 * @return the string
	 */
	@PostMapping("/category/create")
	public String createCategory(@RequestParam("categoryName") String categoryName) {
		
		Category category = new Category();
		category.setCategoryName(categoryName);
		categoryService.create(category);
		
		return "redirect:/manage/category";
	}
	
	/**
	 * Creates the category.
	 *
	 * @param categoryID the category ID
	 * @param categoryName the category name
	 * @return the string
	 */
	@PostMapping("/category/edit/{categoryID}")
	public String createCategory(@PathVariable(name="categoryID") Integer categoryID,
			@RequestParam("categoryName") String categoryName) {
		
		Category category = categoryService.findByID(categoryID);
		category.setCategoryName(categoryName);
		categoryService.edit(category);
		return "redirect:/manage/category";
	}
	
	/**
	 * Delete category.
	 *
	 * @param categoryID the category ID
	 * @return the string
	 */
	@PostMapping("/category/delete/{categoryID}")
	public String deleteCategory(@PathVariable(name = "categoryID") Integer categoryID) {
		
		categoryService.delete(categoryID);
		
		
		return "redirect:/manage/category";
	}
	
	@GetMapping("/allexam")
	public String manageAllExamPackageForm(
			@RequestParam(name="pageNo",required = false) Integer pageNo,
			@RequestParam(name="key",required = false) String key,
			Model model
			) {
		if(pageNo == null) pageNo = 1;
		if(key == null) key = "";
		Page<ExamPackage> page = examPackageService.searchAll(pageNo, key);
		List<ExamPackage> examPackageList = page.getContent();
		
		model.addAttribute("examPackageList", examPackageList);
		model.addAttribute("page", page);
		return "admin/admin-exampackage";
	}
	
	@GetMapping("/allquestion")
	public String manageAllQuestionForm(@RequestParam(name="pageNo",required = false) Integer pageNo,
			@RequestParam(name="key",required = false) String key,
			Model model) {
		if(pageNo == null) pageNo = 1;
		if(key == null) key = "";
		Page<QuestionPackage> page = questionPackageService.searchByName(key, pageNo);
		List<QuestionPackage> questionPackageList = page.getContent();
		
		model.addAttribute("questionPackageList", questionPackageList);
		model.addAttribute("page", page);
		return "admin/admin-question";
	}
}
