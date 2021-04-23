package hl.quizonline.controller;

import java.security.Principal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

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
import hl.quizonline.entity.ExamQuestion;
import hl.quizonline.entity.Examination;
import hl.quizonline.entity.Question;
import hl.quizonline.entity.QuestionPackage;
import hl.quizonline.model.CategoryModel;
import hl.quizonline.model.QuestionPackageModel;
import hl.quizonline.service.AccountService;
import hl.quizonline.service.CategoryService;
import hl.quizonline.service.ExamPackageService;
import hl.quizonline.service.ExamQuestionService;
import hl.quizonline.service.ExaminationService;
import hl.quizonline.service.QuestionPackageService;
import hl.quizonline.service.QuestionService;
import javassist.NotFoundException;

// TODO: Auto-generated Javadoc
/**
 * The Class ExamPackageController.
 */
@Controller
@RequestMapping("/manage/exam")
public class ExamPackageController {
	
	/** The exam package service. */
	@Autowired
	ExamPackageService examPackageService;
	
	/** The account service. */
	@Autowired
	AccountService accountService;
	
	/** The examination service. */
	@Autowired
	ExaminationService examinationService;
	
	/** The question service. */
	@Autowired
	QuestionService questionService;
	
	/** The question package service. */
	@Autowired
	QuestionPackageService questionPackageService;
	
	/** The category service. */
	@Autowired
	CategoryService categoryService;
	
	/** The exam question service. */
	@Autowired
	ExamQuestionService examQuestionService;
	
	/**
	 * manage the exam.
	 *
	 * @param packageid the packageid
	 * @param model the model
	 * @return the string
	 */
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
						
						
					}
				}
				if(packageid!=null) {
					List<Examination> examList = examinationService.getAll(packageid);
					model.addAttribute("examList", examList);
				}
				ExamPackage currentPackage = examPackageService.getExamPackage(packageid);
				model.addAttribute("currentPackage", currentPackage);
				model.addAttribute("examPackageID", packageid);
			} catch (NotFoundException e) {
				e.printStackTrace();
			}
		}
		return "manage/manage-exam";
	}
	
	/**
	 * Adds the exam package form.
	 *
	 * @param model the model
	 * @return the string
	 */
	@GetMapping("/addpackage")
	public String addExamPackageForm(Model model){
		//get all category
		List<Category> categoryList = categoryService.getAll();
		
		
		
		model.addAttribute("categoryList", categoryList);
		return "manage/manage-exampackage-add";
	}
	
	/**
	 * Do add exam package.
	 *
	 * @param examPackageTitle the exam package title
	 * @param isExerciseExam the is exercise exam
	 * @param startDatetime the start datetime
	 * @param doExamTime the do exam time
	 * @param isMix the is mix
	 * @param userPass the user pass
	 * @param pass the pass
	 * @param categoryIDList the category ID list
	 * @param showResult the show result
	 * @param numberOfQuestion the number of question
	 * @return the string
	 */
	@PostMapping("/addpackage")
	public String doAddExamPackage(@RequestParam(name = "examPackageTitle") String examPackageTitle,
			 @RequestParam(name = "isExerciseExam",required = false) Integer isExerciseExam,
			 @RequestParam(name="startDatetime",required = false) String startDatetime,
			 @RequestParam(name="doExamTime",required = false) Integer doExamTime,
			 @RequestParam(name = "isMix",required = false) Integer isMix,
			 @RequestParam(name="usePass",required = false) Integer userPass,
			 @RequestParam(name="pass",required = false) String pass,
			 @RequestParam(name="categoryID") List<Integer> categoryIDList,
			 @RequestParam(name="showResult",required =  false) Integer showResult ,
			 @RequestParam(name="numberOfQuestion",required = false) Integer numberOfQuestion
			 ) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
		    String currentUserName = authentication.getName();
		    Account acc = accountService.getAccountByUsername(currentUserName).get();	    
		    ExamPackage ep = new ExamPackage(examPackageTitle,acc);

		   //if the exam is exercise
		    if(isExerciseExam==null) {
		    	DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm");
		    	Date date;
				try {
					date = (Date)formatter.parse(startDatetime);
					ep.setExerciseExam(false);
			    	ep.setStartDatetime(date);
			    	ep.setDoExamTime(doExamTime);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
		    	//time start handle
		    }
		    else {
		    	ep.setExerciseExam(true);
		    }
		    //if mix
		    if(isMix!=null) {
		    	ep.setMixQuestion(true);
		    }
		    else {
		    	ep.setMixQuestion(false);
		    }
		    
		    //number of question
		    ep.setNumberOfQuestion(numberOfQuestion);
		    
		    //show result 
		    if(showResult!=null) {
		    	ep.setShowResults(true);
		    }
		    else {
		    	ep.setShowResults(false);
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
		    System.out.println(ep);
		    System.out.println("Tạo Exampackage thành công");
		}
		return "redirect:/manage/exam";
	}
	
	/**
	 * Edits the exam package form.
	 *
	 * @param examPackageID the exam package ID
	 * @param model the model
	 * @return the string
	 */
	@GetMapping("/editpackage/{examPackageID}")
	public String editExamPackageForm(@PathVariable(name = "examPackageID") Integer examPackageID,
			Model model) {
		//get all category
		ExamPackage ep = examPackageService.getExamPackage(examPackageID);
		List<Category> categoryList = categoryService.getAll();
		
		List<CategoryModel> categoryModelList = new ArrayList<CategoryModel>();
		for(int i =0;i<categoryList.size();i++) {
			boolean selected = false;
			for(int j = 0;j<ep.getCategories().size();j++) {
				if(categoryList.get(i).getCategoryID()==ep.getCategories().get(j).getCategoryID()) {
					selected=true;
					break;
				}
			}
			
			categoryModelList.add(new CategoryModel(categoryList.get(i).getCategoryID(),
					categoryList.get(i).getCategoryName(), 
					selected));
		}
		model.addAttribute("categoryModelList", categoryModelList);
		model.addAttribute("examPackage", ep);
		model.addAttribute("examPackageID", examPackageID);
		return "/manage/manage-exampackage-edit";
	}
	
	/**
	 * Edits the exam package.
	 *
	 * @param examPackageID the exam package ID
	 * @param examPackageTitle the exam package title
	 * @param categoryIDList the category ID list
	 * @param isExerciseExam the is exercise exam
	 * @param startDatetime the start datetime
	 * @param doExamTime the do exam time
	 * @param isMix the is mix
	 * @param showResult the show result
	 * @param usePassword the use password
	 * @param password the password
	 * @return the string
	 */
	@PostMapping("/editpackage/{examPackageID}")
	public String editExamPackage(@PathVariable(name = "examPackageID") Integer examPackageID,
			@RequestParam(name="examPackageTitle") String examPackageTitle,
			@RequestParam(name="categoryID") List<Integer> categoryIDList,
			@RequestParam(name="isExerciseExam",required = false) Integer isExerciseExam,
			@RequestParam(name="startDatetime",required = false) String startDatetime,
			@RequestParam(name="doExamTime",required = false) Integer doExamTime,
			@RequestParam(name="isMix",required = false) Integer isMix,
			@RequestParam(name="showResult",required = false) Integer showResult,
			@RequestParam(name="usePassword",required = false) Integer usePassword,
			@RequestParam(name="password",required = false) String password,
			@RequestParam(name="numberOfQuestion",required = false) Integer numberOfQuestion
			) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
		    ExamPackage ep = examPackageService.getExamPackage(examPackageID);
		    
		    ep.setExamPackageTitle(examPackageTitle);
		    
		   //if the exam is exercise
		    if(isExerciseExam==null) {
		    	DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm");
		    	Date date;
				try {
					date = (Date)formatter.parse(startDatetime);
					ep.setExerciseExam(false);
			    	ep.setStartDatetime(date);
			    	ep.setDoExamTime(doExamTime);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }
		    else {
		    	ep.setExerciseExam(true);
		    }
		    //if mix
		    if(isMix!=null) {
		    	ep.setMixQuestion(true);
		    }
		    else {
		    	ep.setMixQuestion(false);
		    }
		    
		    //show result 
		    if(showResult!=null) {
		    	ep.setShowResults(true);
		    }
		    else {
		    	ep.setShowResults(false);
		    }
		    //
		    ep.setNumberOfQuestion(numberOfQuestion);
		    
		    //if use password
		    if(usePassword!=null) {
		    	ep.setUsePassword(true);
		    	ep.setPassword(password);
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
		    examPackageService.update(ep);;
		    System.out.println(ep);
		    System.out.println("Sửa Exampackage thành công");
		}
		return "redirect:/manage/exam";
	}
	
	/**
	 * Adds the exam form.
	 *
	 * @param examPackageID the exam package ID
	 * @param model the model
	 * @return the string
	 */
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
				model.addAttribute("questionPackageListSize", questionPackageList.size());
			} catch (NotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		model.addAttribute("examPackage", examPackage);
		return "manage/manage-exam-add";
	}
	
	/**
	 * Adds the exam.
	 *
	 * @param examPackageID the exam package ID
	 * @param title the title
	 * @param questionPackageIDList the question package ID list
	 * @return the string
	 */
	@PostMapping("/addexam/{examPackageID}")
	@Transactional
	public String addExam(
			@PathVariable(name="examPackageID") Integer examPackageID,
			@RequestParam("title") String title,
			@RequestParam("questionPackageID") List<Integer> questionPackageIDList
			) {
		
		
		//create exam object
		Examination exam = new Examination();
		exam.setExaminationTitle(title);
		
		Examination eCreated = examinationService.create(exam);
		
		//create connect between examination and questionPackage
		
		for(int i =0;i<questionPackageIDList.size();i++) {
			QuestionPackage questionpackage = questionPackageService.findByID(questionPackageIDList.get(i)); 
			ExamQuestion eq = new ExamQuestion();
			eq.setExamination(eCreated);
			eq.setQuestionPackage(questionpackage);
			examQuestionService.create(eq);
		}
		
		return "redirect:/manage/exam";
	}

	@GetMapping("/editexam/{examID}")
	public String editExamForm(
			@PathVariable(name="examID") Integer examID,
			Model model) {
		
		Examination exam = examinationService.getExam(examID);
		System.out.println(exam.getExamPackage().getNumberOfQuestion());
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(!(authentication instanceof AnonymousAuthenticationToken)) {
			String currentUserName = authentication.getName();
			List<QuestionPackage> questionPackageList = questionPackageService.getList(currentUserName);
			List<QuestionPackageModel> questionPackageModelList = new ArrayList<QuestionPackageModel>();
			for(int i =0;i<questionPackageList.size();i++) {
				boolean checked = false;
				for(int j=0;j<exam.getExamQuestions().size();j++) {
					if(questionPackageList.get(i).getQuestionPackageID() == exam.getExamQuestions().get(j).getExamQuestionID()) {
						checked = true;
						break;
					}
				}
				int size =0;
				if(questionPackageList.get(i).getExamQuestions().size()>0) {
					size = questionPackageList.get(i).getExamQuestions().size();
				}
				questionPackageModelList.add(new QuestionPackageModel(questionPackageList.get(i).getQuestionPackageID(),
						questionPackageList.get(i).getName(),
						checked,
						size
						));
			}
			model.addAttribute("examPackageModelList", questionPackageModelList);
			model.addAttribute("exam", exam);
			return "manage/manage-exam-edit";
		}
		
		return "redirect:/login";
	}
}
