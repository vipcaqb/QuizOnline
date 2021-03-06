package hl.quizonline.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import hl.quizonline.config.MyConstances;
import hl.quizonline.entity.Account;
import hl.quizonline.entity.Answer;
import hl.quizonline.entity.Category;
import hl.quizonline.entity.ExamPackage;
import hl.quizonline.entity.Examination;
import hl.quizonline.entity.JoinExamination;
import hl.quizonline.entity.Question;
import hl.quizonline.model.DoExamResultModel;
import hl.quizonline.model.ExamDonation;
import hl.quizonline.model.LeaderboardModel;
import hl.quizonline.model.QuestionModel;
import hl.quizonline.repository.CategoryRepository;
import hl.quizonline.service.AccountService;
import hl.quizonline.service.AnswerService;
import hl.quizonline.service.CategoryService;
import hl.quizonline.service.ExamPackageService;
import hl.quizonline.service.JoinExamService;
import hl.quizonline.service.QuestionService;

// TODO: Auto-generated Javadoc
/**
 * The Class HomeController.
 */
@Controller
public class HomeController {
	
	/** The exam package service. */
	@Autowired
	ExamPackageService examPackageService;
	
	/** The question service. */
	@Autowired 
	QuestionService questionService;
	
	/** The join exam service. */
	@Autowired
	JoinExamService joinExamService;
	
	/** The answer service. */
	@Autowired
	AnswerService answerService;
	
	/** The account service. */
	@Autowired
	AccountService accountService;

	/** The category service. */
	@Autowired
	CategoryService categoryService;
	
	/**
	 * Show my home.
	 *
	 * @param model the model
	 * @return the string
	 */
	@GetMapping(value = {"/","/home"})
	public String showMyHome(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
		    return "redirect:/examlist";
		}
		return "index";
	}
	
	/**
	 * Show list examination.
	 *
	 * @param model the model
	 * @return the string
	 */
	@GetMapping(value = "/examlist")
	public String showListExamination(Model model) {
		//L???y danh s??ch t???t c??? danh m???c
		List<Category> categoryList = categoryService.getAll();
		//lay danh sach cuoc thi dang dien ra
		List<ExamPackage> examPackagePresentList = examPackageService.getListPresent();
		//danh sach de on tap
		List<ExamPackage> examPackageExerciseList = examPackageService.getListExercise();
		//de thi sap dien ra
		List<ExamPackage> examPackageIsCommingList = examPackageService.getListIsComing();
		//de thi da ket thuc
		List<ExamPackage> examPackageExpiredList = examPackageService.getListExpired();
		//danh s??ch x???p h???ng t???o ?????
		Page<Account> pageAccount = accountService.getTop10();
		List<Account> aList = pageAccount.getContent();
			//Chuy???n v??o model
		List<ExamDonation> accountList = new ArrayList<ExamDonation>();
		for(int i =0;i<aList.size();i++) {
			accountList.add(new ExamDonation(aList.get(i).getUsername(),
					aList.get(i).getFullname(), aList.get(i).getUrlAvatar(), aList.get(i).getExamPackages().size()));
		}
			//S???p x???p l???i theo th??? t???o gi???m d???n c???a s??? l?????ng ????? thi
		for(int i =0;i<accountList.size()-1;i++) {
			for(int j=i+1;j<accountList.size();j++) {
				if(accountList.get(i).getNumberOfExam()<accountList.get(j).getNumberOfExam()) {
					ExamDonation temp = accountList.get(i);
					accountList.set(i, accountList.get(j));
					accountList.set(j, temp);
				}
			}
		}
			
		
		
		System.out.println("------------------exam package present------------------");
		System.out.println(accountList.size());
		System.out.println("------------------end exam package present------------------");
		
		model.addAttribute("categoryList", categoryList);
		model.addAttribute("currentMiliSecond", System.currentTimeMillis());
		model.addAttribute("accountList", accountList);
		model.addAttribute("examPackagePresentList",examPackagePresentList);
		model.addAttribute("examPackageExerciseList", examPackageExerciseList);
		model.addAttribute("examPackageIsCommingList", examPackageIsCommingList);
		model.addAttribute("examPackageExpiredList", examPackageExpiredList);
		return "examlist";
	}
	
	/**
	 * Giao di???n t??m ki???m n??ng cao.
	 *
	 * @param key the key
	 * @param findBy the find by
	 * @param categoryID the category ID
	 * @param sortBy the sort by
	 * @param direction the direction
	 * @param pageNo the page no
	 * @param model the model
	 * @return the string
	 */
	@GetMapping(value="/examlist/search")
	public String searchExamList(
			@RequestParam(name = "key",required = false) String key,
			@RequestParam(name = "findBy",required = false) String findBy,
			@RequestParam(name = "categoryID", required = false) Integer categoryID,
			@RequestParam(name = "sortBy", required=false) String sortBy,
			@RequestParam(name = "direction", required = false) String direction,
			@RequestParam(name = "pageNo",required =  false) Integer pageNo,
			Model model
			) {
		//danh s??ch x???p h???ng t???o ?????
		Page<Account> pageAccount = accountService.getTop10();
		List<Account> aList = pageAccount.getContent();
			//Chuy???n v??o model
		List<ExamDonation> accountList = new ArrayList<ExamDonation>();
		for(int i =0;i<aList.size();i++) {
			accountList.add(new ExamDonation(aList.get(i).getUsername(),
					aList.get(i).getFullname(), aList.get(i).getUrlAvatar(), aList.get(i).getExamPackages().size()));
		}
			//S???p x???p l???i theo th??? t???o gi???m d???n c???a s??? l?????ng ????? thi
		for(int i =0;i<accountList.size()-1;i++) {
			for(int j=i+1;j<accountList.size();j++) {
				if(accountList.get(i).getNumberOfExam()<accountList.get(j).getNumberOfExam()) {
					ExamDonation temp = accountList.get(i);
					accountList.set(i, accountList.get(j));
					accountList.set(j, temp);
				}
			}
		}
		if(pageNo ==null) pageNo = 1;
		//de thi sap dien ra
		List<ExamPackage> examPackageIsCommingList = examPackageService.getListIsComing();
		//lay danh sach category
		List<Category> categoryList = categoryService.getAll();
		//lay danh sach de thi v?? s???p x???p theo th??? t??? gi???m d???n c???a th???i gian b???t ?????u
		List<ExamPackage> examPackageList = null;
		if(findBy == null && categoryID == null && sortBy == null && direction == null && key==null ) {
			Sort sort = Sort.by(Direction.DESC, "startDatetime");
			Page<ExamPackage> page = examPackageService.searchList(pageNo, MyConstances.PAGE_EXAM_SIZE, sort);
			examPackageList = page.getContent();
			
			
			model.addAttribute("examPackageIsCommingList", examPackageIsCommingList);
			model.addAttribute("page", page);
		}
		else {
			//?????t l???i d??? li???u n???u null
			if(findBy == null) findBy = "title";
			if(categoryID== null) categoryID = -1;
			if(sortBy == null) sortBy = "examPackageTitle";
			if(direction== null) direction = "DESC";
			if(key== null) key = "";
			Page<ExamPackage> page = null;
			Sort sort =null;
			if(direction.equals("ASC")) {
				sort = Sort.by(Direction.ASC, sortBy);
			}
			else {
				sort = Sort.by(Direction.DESC, sortBy);
			}
			
			if(findBy.equals("title")) {
				page = examPackageService.searchListByTitle(key, categoryID, pageNo, MyConstances.PAGE_EXAM_SIZE, sort);
			}
			else if(findBy.equals("fullname")) {
				page = examPackageService.searchListByFullnameAuthor(key, categoryID, pageNo, MyConstances.PAGE_EXAM_SIZE, sort);
			}
			else if (findBy.equals("username")){
				page = examPackageService.searchListByUsernameAuthor(key, categoryID, pageNo, MyConstances.PAGE_EXAM_SIZE, sort);
			}
			else {
				return "404";
			}
			examPackageList = page.getContent();
			model.addAttribute("page", page);
		}
		if(examPackageList!=null) {
			System.out.println(examPackageList.size());
		}
		else {
			System.out.println("List = null");
		}
		model.addAttribute("examPackageList", examPackageList);
		model.addAttribute("categoryList", categoryList);
		model.addAttribute("accountList", accountList);
		model.addAttribute("key", key);
		model.addAttribute("findBy", findBy);
		model.addAttribute("categoryID", categoryID);
		model.addAttribute("sortBy", sortBy);
		model.addAttribute("direction", direction);
		
		model.addAttribute("key", key);
		model.addAttribute("findBy", findBy);
		model.addAttribute("categoryID", categoryID);
		model.addAttribute("sortBy", sortBy);
		model.addAttribute("direction", direction);
		
		return "examlist-search";
	}
	
	/**
	 * Exam detail show.
	 *
	 * @param examPackageID the exam package ID
	 * @param model the model
	 * @return the string
	 */
	@GetMapping(value = "/examdetail/{examPackageID}")
	public String examDetailShow(@PathVariable(name="examPackageID",required = false) Integer examPackageID,
			@RequestParam(name="err",required = false) String err,Model model) {
		examPackageService.inscreaseView(examPackageID);
		//de thi sap dien ra
		List<ExamPackage> examPackageIsCommingList = examPackageService.getListIsComing();
		//danh s??ch x???p h???ng t???o ?????
		Page<Account> pageAccount = accountService.getTop10();
		List<Account> aList = pageAccount.getContent();
			//Chuy???n v??o model
		List<ExamDonation> accountList = new ArrayList<ExamDonation>();
		for(int i =0;i<aList.size();i++) {
			accountList.add(new ExamDonation(aList.get(i).getUsername(),
					aList.get(i).getFullname(), aList.get(i).getUrlAvatar(), aList.get(i).getExamPackages().size()));
		}
			//S???p x???p l???i theo th??? t???o gi???m d???n c???a s??? l?????ng ????? thi
		for(int i =0;i<accountList.size()-1;i++) {
			for(int j=i+1;j<accountList.size();j++) {
				if(accountList.get(i).getNumberOfExam()<accountList.get(j).getNumberOfExam()) {
					ExamDonation temp = accountList.get(i);
					accountList.set(i, accountList.get(j));
					accountList.set(j, temp);
				}
			}
		}
		if(examPackageID==null) {
			return "redirect:/examlist";
		}
		ExamPackage examPackage = examPackageService.getExamPackage(examPackageID);
		
		model.addAttribute("currentMiliSecond", System.currentTimeMillis());
		model.addAttribute("examPackageIsCommingList", examPackageIsCommingList);
		model.addAttribute("accountList", accountList);
		model.addAttribute("examPackage", examPackage);
		model.addAttribute("err", err);
		return "exam-detail";
	}
	
	/**
	 * T???i giao di???n thi.
	 *
	 * @param examPackageID the exam package ID
	 * @param model the model
	 * @return the string
	 */
	@GetMapping(value = "/doexam/{examPackageID}")
	public String doExamForm(@PathVariable(name="examPackageID") Integer examPackageID,
			@RequestParam(name="password",required =  false) String password,
			Model model) {
		ExamPackage examPackage = examPackageService.getExamPackage(examPackageID);
		//Ki???m tra m???t kh???u n???u c?? y??u c???u
		if(examPackage.isUsePassword()) {
			if(password== null || !password.equals(examPackage.getPassword())) {
				return "redirect:/examdetail/"+examPackageID+"?err=wrongpass";
			}
		}
		
		//danh s??ch x???p h???ng t???o ?????
		Page<Account> pageAccount = accountService.getTop10();
		List<Account> aList = pageAccount.getContent();
			//Chuy???n v??o model
		List<ExamDonation> accountList = new ArrayList<ExamDonation>();
		for(int i =0;i<aList.size();i++) {
			accountList.add(new ExamDonation(aList.get(i).getUsername(),
					aList.get(i).getFullname(), aList.get(i).getUrlAvatar() , aList.get(i).getExamPackages().size()));
		}
			//S???p x???p l???i theo th??? t???o gi???m d???n c???a s??? l?????ng ????? thi
		for(int i =0;i<accountList.size()-1;i++) {
			for(int j=i+1;j<accountList.size();j++) {
				if(accountList.get(i).getNumberOfExam()<accountList.get(j).getNumberOfExam()) {
					ExamDonation temp = accountList.get(i);
					accountList.set(i, accountList.get(j));
					accountList.set(j, temp);
				}
			}
		}
		Random generator = new Random();
		
		int max = examPackage.getExaminations().size()-1,min = 0;
		
		int value = generator.nextInt((max - min) + 1) + min;
		//Chon ngau nhien 1 de
		Examination exam = examPackage.getExaminations().get(value);
		//lay tat ca cau hoi trong de
		List<Question> questionList = questionService.getQuestionList(exam);
		
		//x??o tr???n t???t c??? c??u h???i v???a l???y
		Collections.shuffle(questionList);
		List<Question> afterSplitQuestionList = null;
		//c???t list v???a l???y ????? l???y ????ng s??? c??u h???i c???n d??ng
		if(examPackage.getNumberOfQuestion()<questionList.size()) {
			afterSplitQuestionList = questionList.subList(0, examPackage.getNumberOfQuestion()-1);
		}
		else {
			afterSplitQuestionList = questionList;
		}
		//T??nh gi???
		if(!examPackage.isExerciseExam())
		{
			long timeMiliSec = examPackage.getEndDatetime().getTime()- System.currentTimeMillis();
			model.addAttribute("timeSec",timeMiliSec/1000);
		}
		
		model.addAttribute("questionList", afterSplitQuestionList);
		model.addAttribute("examPackage",examPackage);
		return "do-exam";
	}
	
	/**
	 * X??? l?? sau khi ho??n th??nh b??i thi.
	 *
	 * @param examPackageID the exam package ID
	 * @param questionList the question list
	 * @return the response entity
	 */
	@PostMapping(value = "/doexam/{examPackageID}")
	@ResponseBody
	public ResponseEntity<DoExamResultModel> finishExam(@PathVariable(name = "examPackageID") Integer examPackageID,
			@RequestBody List<Question> questionList) {
		DoExamResultModel result = new DoExamResultModel();
		//check correct ?????m xem c?? bao nhi??u c??u ????ng
		int correctCounter = 0;
		for(int i =0;i<questionList.size();i++) {
			if(questionService.checkQuestionIsCorrect(questionList.get(i))) {
				correctCounter++;
			}
		}
		
		//set result value
		result.setTotalOfQuestion(questionList.size());
		result.setCorrectQuestion(correctCounter);
		float fscore = (float)correctCounter/(float)questionList.size();
		int score2 = (int) (fscore*100);
		result.setScore(score2);
		
		List<QuestionModel> questionModelList = new ArrayList<QuestionModel>();
		for(int i =0;i<questionList.size();i++) {
			Question currentQuestion = questionList.get(i);
			Question currentQuestionInDB = questionService.getByID(currentQuestion.getQuestionID());
			QuestionModel questionModel = new QuestionModel();
			//set content
			questionModel.setQuestionContent(currentQuestionInDB.getQuestionContent());
			
			Question questionInDB = questionService.getByID(questionList.get(i).getQuestionID());
			List<String> userAnswerList = new ArrayList<String>();
			//set userAnswerList
			
			for(int j=0;j<currentQuestion.getAnswers().size();j++) {
				if(currentQuestion.getAnswers().get(j).getIdCorrect()==false) continue;
				Answer answerInDB = answerService.getAnswer(currentQuestion.getAnswers().get(j).getAnswerID());
				
				userAnswerList.add(answerInDB.getAnswerContent());
				
			}
			questionModel.setUserAnswerList(userAnswerList);
			
			//set correct
			boolean isCorrect = questionService.checkQuestionIsCorrect(currentQuestion);
			questionModel.setCorrect(isCorrect);
			
			//set correct answer
			List<String> correctAnswerList = new ArrayList<String>();
			for(int j=0;j<questionInDB.getAnswers().size();j++) {
				if(questionInDB.getAnswers().get(j).getIdCorrect())
					correctAnswerList.add(questionInDB.getAnswers().get(j).getAnswerContent());
			}
			questionModel.setCorrectAnswerList(correctAnswerList);
			questionModelList.add(questionModel);
		}
		
		//set model list
		result.setQuestionModelList(questionModelList);
		//set in JoinExamination in DB
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
		    String currentUserName = authentication.getName();
		    Account account = accountService.getAccountByUsername(currentUserName).get();
		    ExamPackage examPackage = examPackageService.getExamPackage(examPackageID);
		    JoinExamination joinExamination = new JoinExamination();
		    joinExamination.setAccount(account);
		    joinExamination.setExamPackage(examPackage);
		    joinExamination.setCorrectQuestionNumber(result.getCorrectQuestion());
		    joinExamination.setScore(result.getScore());
		    joinExamination.setTimeFinish(new Date(System.currentTimeMillis()));
		    joinExamination.setExamTimes(joinExamService.getExamTimes(account, examPackage)+1);
		    
		    joinExamService.create(joinExamination);
		}
		//Sau khi thi xong, th??m 1 l?????t thi v??o ????? thi ????
		examPackageService.inscreaseDoExamTimes(examPackageID, 1);
		return ResponseEntity.ok(result);
	}
	
	/**
	 *T??i b???ng x???p h???ng c???a m???t ????? thi.
	 *
	 * @param examPackageID the exam package ID
	 * @param pageNo the page no
	 * @param model the model
	 * @return the string
	 */
	@GetMapping(value = {"examleaderboard/{examPackageID}/{pageNo}","examleaderboard/{examPackageID}"})
	public String examLeaderBoardForm(@PathVariable("examPackageID") Integer examPackageID,
			@PathVariable(name="pageNo",required = false) Integer pageNo,
			Model model) {
		if(examPackageID==null) {
			return "redirect:/examlist";
		}
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			if(pageNo==null) pageNo=1;
			
			ExamPackage examPackage = examPackageService.getExamPackage(examPackageID);
		    String currentUserName = authentication.getName();
		    Account account = accountService.getAccountByUsername(currentUserName).get();
		    
		    Page<JoinExamination> page = joinExamService.getByExamPackage(examPackage, pageNo, MyConstances.PAGE_SIZE, Sort.by("score").descending());
		    
		    List<JoinExamination> joinExamList = page.getContent();
		    //set leaderboard
		    List<LeaderboardModel> leaderboardList = new ArrayList<LeaderboardModel>();
		    
		    for(int i =0;i<joinExamList.size();i++) {
		    	LeaderboardModel leaderboardModel = new LeaderboardModel();
		    	leaderboardModel.setAccount(joinExamList.get(i).getAccount());
		    	
		    	leaderboardModel.setExamTimes(joinExamList.get(i).getExamTimes());
		    	leaderboardModel.setScore(joinExamList.get(i).getScore());
		    	leaderboardModel.setFinishDatetime(joinExamList.get(i).getTimeFinish());
		    	leaderboardList.add(leaderboardModel);
		    }
		    //de thi sap dien ra
			List<ExamPackage> examPackageIsCommingList = examPackageService.getListIsComing();
			//danh s??ch x???p h???ng t???o ?????
			Page<Account> pageAccount = accountService.getTop10();
			List<Account> aList = pageAccount.getContent();
				//Chuy???n v??o model
			List<ExamDonation> accountList = new ArrayList<ExamDonation>();
			for(int i =0;i<aList.size();i++) {
				accountList.add(new ExamDonation(aList.get(i).getUsername(),
						aList.get(i).getFullname(), aList.get(i).getUrlAvatar(), aList.get(i).getExamPackages().size()));
			}
				//S???p x???p l???i theo th??? t???o gi???m d???n c???a s??? l?????ng ????? thi
			for(int i =0;i<accountList.size()-1;i++) {
				for(int j=i+1;j<accountList.size();j++) {
					if(accountList.get(i).getNumberOfExam()<accountList.get(j).getNumberOfExam()) {
						ExamDonation temp = accountList.get(i);
						accountList.set(i, accountList.get(j));
						accountList.set(j, temp);
					}
				}
			}
			
		    model.addAttribute("page", page);
			model.addAttribute("leaderboardList", leaderboardList);
			model.addAttribute("examPackageIsCommingList", examPackageIsCommingList);
			model.addAttribute("examPackage", examPackage);
			model.addAttribute("accountList", accountList);
			return "exam-leaderboard";
		}
		
		return "redirect:/login";
	}

	/**
	 * Hi???n th??? th??ng tin c?? nh??n c???a m???t ng?????i.
	 *
	 * @param username the username
	 * @param model the model
	 * @return the string
	 */
	@GetMapping("/profile/{username}")
	public String showInfoSomeone(@PathVariable("username") String username, Model model) {
		
		Optional<Account> oa = accountService.getAccountByUsername(username);
		if(oa.isEmpty()) return "redirect:/login";
		
		Account account = oa.get();
		model.addAttribute("account", account);
		return "profile";
	}


}
