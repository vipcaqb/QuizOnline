package hl.quizonline.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import hl.quizonline.entity.Account;
import hl.quizonline.entity.Answer;
import hl.quizonline.entity.ExamPackage;
import hl.quizonline.entity.Examination;
import hl.quizonline.entity.JoinExamination;
import hl.quizonline.entity.Question;
import hl.quizonline.model.DoExamResultModel;
import hl.quizonline.model.LeaderboardModel;
import hl.quizonline.model.QuestionModel;
import hl.quizonline.service.AccountService;
import hl.quizonline.service.AnswerService;
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
	
	@Autowired
	JoinExamService joinExamService;
	
	/** The answer service. */
	@Autowired
	AnswerService answerService;
	@Autowired
	AccountService accountService;
	
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
		    String currentUserName = authentication.getName();
		    System.out.println(currentUserName);
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
		
		//lay danh sach cuoc thi dang dien ra
		List<ExamPackage> examPackagePresentList = examPackageService.getListPresent();
		//danh sach de on tap
		List<ExamPackage> examPackageExerciseList = examPackageService.getListExercise();
		//de thi sap dien ra
		List<ExamPackage> examPackageIsCommingList = examPackageService.getListIsComing();
		//de thi da ket thuc
		List<ExamPackage> examPackageExpiredList = examPackageService.getListExpired();
		
		System.out.println("------------------exam package present------------------");
		for(int i =0;i<examPackagePresentList.size();i++) {
			System.out.println(examPackagePresentList.get(i));
		}
		System.out.println("------------------end exam package present------------------");
		
		model.addAttribute("examPackagePresentList",examPackagePresentList);
		model.addAttribute("examPackageExerciseList", examPackageExerciseList);
		model.addAttribute("examPackageIsCommingList", examPackageIsCommingList);
		model.addAttribute("examPackageExpiredList", examPackageExpiredList);
		
		return "examlist";
	}
	
	/**
	 * Exam detail show.
	 *
	 * @param examPackageID the exam package ID
	 * @param model the model
	 * @return the string
	 */
	@GetMapping(value = "/examdetail/{examPackageID}")
	public String examDetailShow(@PathVariable(name="examPackageID",required = false) Integer examPackageID,Model model) {
		if(examPackageID==null) {
			return "redirect:/examlist";
		}
		ExamPackage examPackage = examPackageService.getExamPackage(examPackageID);
		
		model.addAttribute("examPackage", examPackage);
		return "exam-detail";
	}
	
	/**
	 * Do exam form.
	 *
	 * @param examPackageID the exam package ID
	 * @param model the model
	 * @return the string
	 */
	@GetMapping(value = "/doexam/{examPackageID}")
	public String doExamForm(@PathVariable(name="examPackageID") Integer examPackageID,Model model) {
		Random generator = new Random();
		ExamPackage examPackage = examPackageService.getExamPackage(examPackageID);
		int max = examPackage.getExaminations().size()-1,min = 0;
		
		int value = generator.nextInt((max - min) + 1) + min;
		//Chon ngau nhien 1 de
		Examination exam = examPackage.getExaminations().get(value);
		//lay tat ca cau hoi trong de
		List<Question> questionList = questionService.getQuestionList(exam);
		
		//xáo trộn tất cả câu hỏi vừa lấy
		Collections.shuffle(questionList);
		List<Question> afterSplitQuestionList = null;
		//cắt list vừa lấy để lấy đúng số câu hỏi cần dùng
		if(examPackage.getNumberOfQuestion()<questionList.size()) {
			afterSplitQuestionList = questionList.subList(0, examPackage.getNumberOfQuestion()-1);
		}
		else {
			afterSplitQuestionList = questionList;
		}
		model.addAttribute("questionList", afterSplitQuestionList);
		model.addAttribute("examPackage",examPackage);
		return "do-exam";
	}
	
	/**
	 * Finish exam.
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
		//check correct
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
				if(answerInDB.getIdCorrect()) {
					userAnswerList.add(answerInDB.getAnswerContent());
				}
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
		//return result
		
		return ResponseEntity.ok(result);
	}
	
	@GetMapping("examleaderboard/{examPackageID}")
	public String examLeaderBoardForm(@PathVariable("examPackageID") Integer examPackageID,Model model) {
		if(examPackageID==null) {
			return "redirect:/examlist";
		}
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			ExamPackage examPackage = examPackageService.getExamPackage(examPackageID);
		    String currentUserName = authentication.getName();
		    Account account = accountService.getAccountByUsername(currentUserName).get();
		    List<JoinExamination> joinExamList = joinExamService.getByExamPackage(examPackage);
		    //set leaderboard
		    List<LeaderboardModel> leaderboardList = new ArrayList<LeaderboardModel>();
		    
		    for(int i =0;i<joinExamList.size();i++) {
		    	LeaderboardModel leaderboardModel = new LeaderboardModel();
		    	leaderboardModel.setAccount(joinExamList.get(i).getAccount());
		    	
		    	leaderboardModel.setExamTimes(joinExamList.get(i).getExamTimes());
		    	leaderboardModel.setScore(joinExamList.get(i).getScore());
		    	
		    	leaderboardList.add(leaderboardModel);
		    }
			
			model.addAttribute("leaderboardList", leaderboardList);
			model.addAttribute("examPackage", examPackage);
			return "exam-leaderboard";
		}
		
		
		return "redirect:/login";
	}
}
