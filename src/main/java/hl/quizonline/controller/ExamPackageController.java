package hl.quizonline.controller;

import java.security.Principal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


import hl.quizonline.entity.Account;
import hl.quizonline.entity.Category;
import hl.quizonline.entity.ExamPackage;
import hl.quizonline.entity.Examination;
import hl.quizonline.entity.Question;
import hl.quizonline.entity.QuestionPackage;
import hl.quizonline.service.AccountService;
import hl.quizonline.service.CategoryService;
import hl.quizonline.service.ExamPackageService;
import hl.quizonline.service.ExaminationService;
import hl.quizonline.service.QuestionPackageService;
import hl.quizonline.service.QuestionService;
import javassist.NotFoundException;

@Controller
@RequestMapping("/manage/exam")
public class ExamPackageController {
	@Autowired
	ExamPackageService examPackageService;
	
	@Autowired
	AccountService accountService;
	
	@Autowired
	ExaminationService examinationService;
	
	@Autowired
	QuestionService questionService;
	
	@Autowired
	QuestionPackageService questionPackageService;
	
	@Autowired
	CategoryService categoryService;
	
	@GetMapping(value = {"/{packageid}",""})
	public String editExam(@PathVariable(name="packageid",required = false) Integer packageid,Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
		    String currentUserName = authentication.getName();
		    List<ExamPackage> listExamPackage;
			try {
				//get exam package list
				listExamPackage = examPackageService.getList(currentUserName);
				//get exam list selected
				
				model.addAttribute("examPackageList", listExamPackage);
				if(packageid==null) {
					if(listExamPackage.size()>0) {
						packageid=listExamPackage.get(0).getExamPackageID();
						
						List<Examination> examList = examinationService.getAll(packageid);
						model.addAttribute("examList", examList);
						model.addAttribute("examPackageID", packageid);
					}
				}
			} catch (NotFoundException e) {
				e.printStackTrace();
			}
		}
		return "manage/manage-exam";
	}
	
	@GetMapping("/addpackage")
	public String addExamPackageForm(Model model){
		//get all category
		List<Category> categoryList = categoryService.getAll();
		
		
		
		model.addAttribute("categoryList", categoryList);
		return "manage/manage-exampackage-add";
	}
	
	@PostMapping("/addpackage")
	public String doAddExamPackage(@RequestParam(name = "examPackageTitle") String examPackageTitle,
			 @RequestParam(name = "isExerciseExam",required = false) Integer isExerciseExam,
			 @RequestParam(name="startDatetime",required = false) String startDatetime,
			 @RequestParam(name="doExamTime",required = false) Integer doExamTime,
			 @RequestParam(name = "isMix",required = false) Integer isMix,
			 @RequestParam(name="usePass",required = false) Integer userPass,
			 @RequestParam(name="pass",required = false) String pass,
			 @RequestParam(name="categoryID") List<Integer> categoryIDList
			 ) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
		    String currentUserName = authentication.getName();
		    Account acc = accountService.getAccountByUsername(currentUserName).get();	    
		    ExamPackage ep = new ExamPackage(examPackageTitle,acc);

		   //if the exam is exercise
		    if(isExerciseExam!=null) {
		    	DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm");
		    	Date date;
				try {
					date = (Date)formatter.parse(startDatetime);
					ep.setExerciseExam(true);
			    	ep.setStartDatetime(date);
			    	ep.setDoExamTime(doExamTime);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
		    	//time start handle
		    }
		    else {
		    	ep.setExerciseExam(false);
		    }
		    //if mix
		    if(isMix!=null) {
		    	ep.setMixQuestion(true);
		    }
		    else {
		    	ep.setMixQuestion(false);
		    }
		    
		    //if use password
		    if(userPass!=null) {
		    	ep.setUsePassword(true);
		    	ep.setPassword(pass);
		    }
		    else {
		    	ep.setUsePassword(false);
		    }
		    
		    List<Category> categoryList = new ArrayList<Category>();
		    //find category
		    for(int i =0 ; i<categoryIDList.size();i++) {
		    	Category c = categoryService.findByID(categoryIDList.get(i));
		    	categoryList.add(c);
		    }
		    ep.setCategories(categoryList);
		    examPackageService.create(ep);
		    System.out.println("Tạo Exampackage thành công");
		}
		return "redirect:/manage/exam";
	}
	
	@GetMapping("/addexam/{examPackageID}")
	public String addExamForm(@PathVariable(name = "examPackageID") Integer examPackageID, Model model) {
		ExamPackage examPackage = examPackageService.getExamPackage(examPackageID);
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
		    String currentUserName = authentication.getName();
		    try {
				List<ExamPackage> examPackageList = examPackageService.getList(currentUserName);
				List<QuestionPackage> questionPackageList  = questionPackageService.getList(currentUserName);
				model.addAttribute("examPackageList", examPackageList);
				model.addAttribute("examPackageListSize", examPackageList.size());
				model.addAttribute("questionPackageList", questionPackageList);
			} catch (NotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		model.addAttribute("examPackage", examPackage);
		return "manage/manage-exam-add";
	}
	
	@PostMapping("/addexam")
	public String addExam() {
		
		return "redirect:/manage/exam";
	}
}
