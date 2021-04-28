package hl.quizonline.controller;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import hl.quizonline.entity.Account;
import hl.quizonline.entity.Answer;
import hl.quizonline.entity.ExamPackage;
import hl.quizonline.entity.Question;
import hl.quizonline.entity.QuestionPackage;
import hl.quizonline.service.AccountService;
import hl.quizonline.service.AnswerService;
import hl.quizonline.service.QuestionPackageService;
import hl.quizonline.service.QuestionService;


// TODO: Auto-generated Javadoc
/**
 * The Class QuestionController.
 */
@Controller
@RequestMapping("/manage/question")
public class QuestionController {
	
	/** The question package service. */
	@Autowired
	QuestionPackageService questionPackageService;
	
	/** The question service. */
	@Autowired
	QuestionService questionService;
	
	/** The account service. */
	@Autowired
	AccountService accountService;
	
	/** The answer service. */
	@Autowired
	AnswerService answerService;

	/**
	 * List exam.
	 *
	 * @param questionPackageID the question package ID
	 * @param model the model
	 * @return the string
	 */
	@GetMapping(value = {"","/{questionPackageID}"})
	public String listExam(@PathVariable(name ="questionPackageID",required = false ) Integer questionPackageID, Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
		    String currentUserName = authentication.getName();
		    List<QuestionPackage> questionPackageList = questionPackageService.getList(currentUserName);
		    model.addAttribute("questionPackageList", questionPackageList);
		    if(questionPackageID == null) {
		    	if(questionPackageList.size()>0) {
		    		questionPackageID = questionPackageList.get(0).getQuestionPackageID();
		    	}
		    }
		    
		    if(questionPackageID!= null) {
		    	List<Question> questionList = questionService.getAll(questionPackageID);
		    	model.addAttribute("questionList", questionList);
		    }
		}
		model.addAttribute("questionPackageID", questionPackageID);
		return "manage/manage-question";
	}
	
	/**
	 * Adds the question package.
	 *
	 * @param questionPackageName the question package name
	 * @return the string
	 */
	@PostMapping("/addpackage")
	public String addQuestionPackage(@RequestParam String questionPackageName) {
		QuestionPackage qp = new QuestionPackage();
		qp.setName(questionPackageName);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentUserName = authentication.getName();
		Account acc = accountService.getAccountByUsername(currentUserName).get();
		qp.setAccount(acc);
		System.out.println(acc);
		questionPackageService.create(qp);
		return "redirect:/manage/question";
	}
	
	/**
	 * Adds the question form.
	 *
	 * @param questionPackageID the question package ID
	 * @param question the question
	 * @param model the model
	 * @return the string
	 */
	@GetMapping("/addquestion/{questionPackageID}")
	public String addQuestionForm(@PathVariable(name = "questionPackageID") Integer questionPackageID,Question question,Model model) {
		List<Question> questionList = questionService.getAll(questionPackageID);
		
		QuestionPackage qp = questionPackageService.findByID(questionPackageID);
		model.addAttribute("questionPackage", qp);
		model.addAttribute("questionPackageID", questionPackageID);
		model.addAttribute("questionList", questionList);
		return "manage/manage-question-add";
	}
	
	/**
	 * Adds the question.
	 *
	 * @param questionPackageID the question package ID
	 * @param content the content
	 * @param txtPhuongAn the txt phuong an
	 * @param cbPhuongAn the cb phuong an
	 * @return the string
	 */
	@PostMapping("/addquestion/{questionPackageID}")
	@Transactional
	public String addQuestion(@PathVariable(name = "questionPackageID") Integer questionPackageID,
			@RequestParam("content") String content,
			@RequestParam("txt-pa") List<String> txtPhuongAn,@RequestParam(name = "cb-pa",required = false) List<Integer> cbPhuongAn) {
		QuestionPackage qp = questionPackageService.findByID(questionPackageID);
		Question question = new Question();
		
		List<Answer> answerList = new ArrayList<Answer>();
		//set answer
		for(int i =0; i< txtPhuongAn.size();i++) {
			boolean isCorrect = false;
			for(int j=0;j<cbPhuongAn.size();j++) {
				if(i==cbPhuongAn.get(j)-1) {
					isCorrect=true;
					break;
				}
			}
			Answer ans = new Answer(txtPhuongAn.get(i), isCorrect, question);
			answerService.create(ans);
			answerList.add(ans);
		}
		//set question
		question.setQuestionContent(content);
		question.setAnswers(answerList);
		question.setQuestionPackage(qp);
		//do add question
		questionService.create(question);
		System.out.println("Đã tạo xong câu hỏi");
		return "redirect:/manage/question/addquestion/"+questionPackageID;
	}
	
	@GetMapping("/editquestion/{questionPackageID}/{questionID}")
	public String editQuestionForm(@PathVariable(name = "questionPackageID") Integer questionPackageID,
			@PathVariable(name = "questionID") Integer questionID,
			Model model){
		//get info
		List<Question> questionList = questionService.getAll(questionPackageID);
		QuestionPackage qp = questionPackageService.findByID(questionPackageID);
		Question question = questionService.getByID(questionID);

		
		//return in model
		model.addAttribute("question", question);
		model.addAttribute("questionPackage", qp);
		model.addAttribute("questionPackageID", questionPackageID);
		model.addAttribute("questionList", questionList);
		return "manage/manage-question-edit";
	}
}
