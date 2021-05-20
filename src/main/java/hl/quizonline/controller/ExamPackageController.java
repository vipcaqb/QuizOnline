package hl.quizonline.controller;

import java.security.Principal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

import hl.quizonline.config.MyConstances;
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
import hl.quizonline.service.MailboxService;
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
	
	@Autowired
	MailboxService mailBoxService;
	
	/**
	 * manage the exam.
	 *
	 * @param packageid the packageid
	 * @param pageNo the page no
	 * @param key the key
	 * @param packPageNo the pack page no
	 * @param model the model
	 * @return the string
	 */
	@GetMapping(value = {"/{packageid}",""})
	public String editExam(@PathVariable(name="packageid",required = false) Integer packageid,
			@RequestParam(name = "page",required = false) Integer pageNo,
			@RequestParam(name = "key",required = false) String key,
			@RequestParam(name = "packPage",required = false) Integer packPageNo,
			@RequestParam(name = "err", required = false) String errMsg,
			Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			
			if(pageNo==null) pageNo=1;
			if(key == null) key= "";
			if(packPageNo == null) packPageNo = 1;
		    String currentUserName = authentication.getName();
		    List<ExamPackage> listExamPackage;
			//get exam package list
		    Page<ExamPackage> pageExamPackage = examPackageService.getPageByUsername(currentUserName, packPageNo, MyConstances.PAGE_SIZE);
			listExamPackage = pageExamPackage.getContent();
			//get exam list selected
			
			model.addAttribute("examPackageList", listExamPackage);
			if(packageid==null) {
				if(listExamPackage!=null &&listExamPackage.size()>0) {
					packageid=listExamPackage.get(0).getExamPackageID();
				}
			}
			if(packageid!=null) {
				//get page
				Page<Examination> page = examinationService.getPageByExamPackage(packageid, pageNo, MyConstances.PAGE_SIZE);
				List<Examination> examList = examinationService.getAll(packageid);
				model.addAttribute("examList", examList);
				ExamPackage currentPackage = examPackageService.getExamPackage(packageid);
				model.addAttribute("currentPackage", currentPackage);
				model.addAttribute("page", page);
			}
			
			model.addAttribute("errMsg", errMsg);
			model.addAttribute("packPage", pageExamPackage);
			model.addAttribute("examPackageID", packageid);
			model.addAttribute("key", key);
			return "manage/manage-exam";
		}
		return "redirect:/login";
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
	 * @param endDatetime the end datetime
	 * @param description the description
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
			 @RequestParam(name="categoryID",required = false) List<Integer> categoryIDList,
			 @RequestParam(name="showResult",required =  false) Integer showResult ,
			 @RequestParam(name="numberOfQuestion",required = false) Integer numberOfQuestion,
			 @RequestParam(name="endDatetime",required = false) String endDatetime,
			 @RequestParam(name="description",required = false) String description,
			 Model model
			 ) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
		    String currentUserName = authentication.getName();
		  //get all category
			List<Category> allCategory = categoryService.getAll();
			model.addAttribute("categoryList", allCategory);
			boolean hasError = false;
		    //validate
		    if(examPackageTitle==null||examPackageTitle.isEmpty()) {
		    	model.addAttribute("err1", 1);
		    	hasError=true;
		    }
		    if(categoryIDList==null) {
		    	model.addAttribute("err2", 1);
		    	hasError=true;
		    }
		    if (numberOfQuestion == null) {
		    	model.addAttribute("err3", 1);
		    	hasError=true;
		    }
		    else if (numberOfQuestion==0) {
		    	model.addAttribute("err4", 1);
		    	hasError=true;
		    }
		    if (isExerciseExam==null && (startDatetime == null||startDatetime.isBlank())) {
		    	model.addAttribute("err5", 1);
		    	hasError=true;
		    }
		    if(isExerciseExam==null && (endDatetime == null||endDatetime.isBlank())) {
		    	model.addAttribute("err6", 1);
		    	hasError=true;
		    }
		    if(endDatetime!=null && startDatetime!=null) {
		    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm");
		    	try {
					if(sdf.parse(endDatetime).before(sdf.parse(startDatetime))){
						model.addAttribute("err7", 1);
						hasError=true;
					}
				} catch (ParseException e) {
					model.addAttribute("err8", 1);
					hasError=true;
				}
		    }
		    if(hasError) {
		    	return "manage/manage-exampackage-add";
		    }
		    Account acc = accountService.getAccountByUsername(currentUserName).get();	    
		    ExamPackage ep = new ExamPackage(examPackageTitle,acc);
		    ep.setCreateDatetime(new Date(System.currentTimeMillis()));
		    ep.setDescription(description);

		   //if the exam is exercise
		    if(isExerciseExam==null) {
		    	DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm");
		    	Date date;
		    	Date endDate;
				try {
					date = (Date)formatter.parse(startDatetime);
					endDate = (Date)formatter.parse(endDatetime);
					ep.setExerciseExam(false);
			    	ep.setStartDatetime(date);
			    	ep.setEndDatetime(endDate);
			    	ep.setDoExamTime(0);
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
	 * @param numberOfQuestion the number of question
	 * @param endDatetime the end datetime
	 * @param description the description
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
			@RequestParam(name="numberOfQuestion",required = false) Integer numberOfQuestion,
			@RequestParam(name="endDatetime",required = false) String endDatetime,
			@RequestParam(name="description",required = false) String description
			) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
		    ExamPackage ep = examPackageService.getExamPackage(examPackageID);
		    
		    ep.setExamPackageTitle(examPackageTitle);
		    
		    ep.setDescription(description);
		    
		   //if the exam is exercise
		    if(isExerciseExam==null) {
		    	DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm");
		    	Date date,date2;
				try {
					date = (Date)formatter.parse(startDatetime);
					date2 = (Date)formatter.parse(endDatetime);
					ep.setExerciseExam(false);
			    	ep.setStartDatetime(date);
			    	ep.setEndDatetime(date2);
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
			List<ExamPackage> examPackageList = examPackageService.getList(currentUserName);
			List<QuestionPackage> questionPackageList  = questionPackageService.getList(currentUserName);
			model.addAttribute("examPackageList", examPackageList);
			model.addAttribute("examPackageListSize", examPackageList.size());
			model.addAttribute("questionPackageList", questionPackageList);
			model.addAttribute("questionPackageListSize", questionPackageList.size());
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
		exam.setExamPackage(new ExamPackage(examPackageID));
		
		Examination eCreated = examinationService.create(exam);
		
		//create connect between examination and questionPackage
		
		for(int i =0;i<questionPackageIDList.size();i++) {
			QuestionPackage questionpackage = questionPackageService.findByID(questionPackageIDList.get(i)); 
			System.out.println(questionPackageIDList.get(i));
			ExamQuestion eq = new ExamQuestion();
			eq.setExamination(eCreated);
			eq.setQuestionPackage(questionpackage);
			System.out.println(eq);
			examQuestionService.create(eq);
		}
		
		return "redirect:/manage/exam";
	}

	/**
	 * Edits the exam form.
	 *
	 * @param examID the exam ID
	 * @param model the model
	 * @return the string
	 */
	@GetMapping("/editexam/{examID}")
	public String editExamForm(
			@PathVariable(name="examID") Integer examID,
			Model model) {
		
		Examination exam = examinationService.getExam(examID);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(!(authentication instanceof AnonymousAuthenticationToken)) {
			
			String currentUserName = authentication.getName();
			List<QuestionPackage> questionPackageList = questionPackageService.getList(currentUserName);
			List<QuestionPackageModel> questionPackageModelList = new ArrayList<QuestionPackageModel>();
			for(int i =0;i<questionPackageList.size();i++) {
				boolean checked = false;
				for(int j=0;j<exam.getExamQuestions().size();j++) {
					if(questionPackageList.get(i).getQuestionPackageID() == exam.getExamQuestions().get(j).getQuestionPackage().getQuestionPackageID()) {
						checked = true;
						break;
					}
				}
				int size =0;
				if(questionPackageList.get(i).getExamQuestions().size()>0) {
					for(ExamQuestion eq : questionPackageList.get(i).getExamQuestions()) {
						size +=eq.getQuestionPackage().getQuestions().size();
					}
				}
				questionPackageModelList.add(new QuestionPackageModel(questionPackageList.get(i).getQuestionPackageID(),
						questionPackageList.get(i).getName(),
						checked,
						size
						));
			}
			
			//get exampackageList
			List<ExamPackage> examPackageList = examPackageService.getList(currentUserName);
			//Đếm số câu hỏi đang có trang đề
			long count = examinationService.countQuestionAmounts(examID);
			
			model.addAttribute("numberOfQuestions", count);
			model.addAttribute("examPackageList", examPackageList);
			model.addAttribute("examPackageModelList", questionPackageModelList);
			model.addAttribute("exam", exam);
			return "manage/manage-exam-edit";
		}
		
		return "redirect:/login";
	}

	/**
	 * Edits the exam.
	 *
	 * @param examinationID the examination ID
	 * @param examinationTitle the examination title
	 * @param questionPackageIDList the question package ID list
	 * @param examPackageID the exam package ID
	 * @return the string
	 */
	@PostMapping("/editexam/{examinationID}")
	@Transactional
	public String editExam(
			@PathVariable(name = "examinationID") Integer examinationID,
			@RequestParam(name="examinationTitle") String examinationTitle,
			@RequestParam(name = "questionPackageID") List<Integer> questionPackageIDList,
			@RequestParam(name ="examPackageID") Integer examPackageID
			) {
		//get exam
		Examination exam = examinationService.getExam(examinationID);
		//delete connection
		examQuestionService.delete(exam);
		//set value
		exam.setExaminationTitle(examinationTitle);
		exam.setExamPackage(new ExamPackage(examPackageID));
		//set new connection
		for(int i =0;i<questionPackageIDList.size();i++) {
			QuestionPackage questionpackage = questionPackageService.findByID(questionPackageIDList.get(i)); 
			ExamQuestion eq = new ExamQuestion();
			eq.setExamination(exam);
			eq.setQuestionPackage(questionpackage);
			examQuestionService.create(eq);
		}
		
		//do edit
		
		return "redirect:/manage/exam";
	}
	
	/**
	 * Delete exam package.
	 *
	 * @param examPackageID the exam package ID
	 * @return the string
	 */
	@GetMapping("/deletePackage/{examPackageID}")
	@Transactional
	public String deleteExamPackage(@PathVariable("examPackageID") Integer examPackageID,
			@RequestParam(name = "reasonID",required =  false) Integer reasonID,
			@RequestParam(name = "reason", required = false) String reason
			) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			String currentUserName = authentication.getName();
			Optional<Account> opAccount = accountService.getAccountByUsername(currentUserName);
			Account currentAccount = opAccount.get();
			ExamPackage examPackage = examPackageService.getExamPackage(examPackageID);
			//Kiểm tra xem người dùng hiện tại có đủ quyền xóa hay không
			if(!currentUserName.equals(examPackage.getAccount().getUsername())) {
				if(!currentAccount.getRole().equals("ROLE_ADMIN")) {
					return "redirect:/login";
				}
			}
			//xóa exampackage
			examPackageService.delete(examPackageID);
			//Sau khi xóa, kiểm tra xem người xóa là ai, nếu người xóa là admin và không phải
			//chủ sở hữu thì tiến hành gửi thư thông báo rằng ExamPackage đã bị admin xóa
			
			if(!currentUserName.equals(examPackage.getAccount().getUsername())) {
				String reasonMessage = "";
				if(reasonID!=null) {
					if(reasonID==0) {
						reasonMessage = "Spam";
					}
					else if (reasonID==1){
						reasonMessage = "Nội dung không phù hợp";
					}
					else if (reasonID == 2) {
						reasonMessage = reason;
					}
				}
				//Tiến hành gửi thông báo
				mailBoxService.noticeUserWhenAdminDeleteExamPackage(examPackage.getAccount(), examPackage, reasonMessage);
				return "redirect:/manage/account";
			}
			return "redirect:/manage/exam";
		}
		return "redirect:/login";
	}
	
	/**
	 * Delete exam.
	 *
	 * @param examinationID the examination ID
	 * @return the string
	 */
	@GetMapping("/deleteExam/{examinationID}")
	public String deleteExam(@PathVariable("examinationID") Integer examinationID) {
		examinationService.delete(examinationID);
		return "redirect:/manage/exam";
	}

	/**
	 * Tiến hành công khai một bài thi, sau khi công khai mọi người mới có thể tìm thấy bài thi.
	 *
	 * @param examPackageID the exam package ID
	 * @return the string
	 */
	@GetMapping("/public/{examPackageID}")
	public String publicAExamPackage(@PathVariable("examPackageID") Integer examPackageID) {
		ExamPackage examPackage = examPackageService.getExamPackage(examPackageID);
		
		if(examPackage.isPublic() == false) {
			boolean readyToPublic = true;
			for(Examination exam : examPackage.getExaminations()) {
				if(examinationService.countQuestionAmounts(exam.getExaminationID())<examPackage.getNumberOfQuestion()) {
					readyToPublic = false;
					
					return "redirect:/manage/exam/"+examPackageID+"?err=e1";
				}
			}
			if(readyToPublic) {
				examPackage.setPublic(true);
			}
		}
		else {
			examPackage.setPublic(false);
		}
		
		examPackageService.update(examPackage);
		return "redirect:/manage/exam/"+examPackageID;
	}

}
