package hl.quizonline.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hl.quizonline.entity.Answer;
import hl.quizonline.entity.ExamPackage;
import hl.quizonline.entity.ExamQuestion;
import hl.quizonline.entity.Examination;
import hl.quizonline.entity.Question;
import hl.quizonline.entity.QuestionPackage;
import hl.quizonline.repository.AnswerRepository;
import hl.quizonline.repository.QuestionPackageRepository;
import hl.quizonline.repository.QuestionRepository;
import hl.quizonline.service.ExamQuestionService;
import hl.quizonline.service.QuestionService;

@Service
public class QuestionServiceImpl implements QuestionService {
	
	@Autowired
	QuestionRepository questionRepository;
	@Autowired
	QuestionPackageRepository questionPackageRepository;
	@Autowired
	ExamQuestionService examQuestionService;
	@Autowired
	AnswerRepository answerRepository;

	@Override
public List<Question> getAll(int questionPackageID) {
		QuestionPackage qp = questionPackageRepository.findById(questionPackageID).get();
		return questionRepository.findByQuestionPackage(qp);
	}

	@Override
	public void create(Question question) {
		questionRepository.save(question);
	}

	@Override
	public void edit(Question question) {
		Optional<Question> oq = questionRepository.findById(question.getQuestionID());
		if(oq.isPresent()) {
			questionRepository.save(question);
		}
	}

	@Override
	public void delete(int questionID) {
		questionRepository.deleteById(questionID);
	}

	@Override
	public void delete(Question question) {
		questionRepository.delete(question);
	}

	@Override
	public Question getByID(int questionID) {
		return questionRepository.findById(questionID).get();
	}

	@Override
	public List<Question> getQuestionList(Examination examination) {
		List<Question> questionList = new ArrayList<Question>();
		
		//lay ExamQuestion list
		List<ExamQuestion> examQuestionList = examination.getExamQuestions();
		//lay questionPackage list
		List<QuestionPackage> questionPackageList = new ArrayList<QuestionPackage>();
		if(examQuestionList==null) return null;
		for(int i = 0;i<examQuestionList.size();i++) {
			QuestionPackage questionPackage = examQuestionList.get(i).getQuestionPackage();
			questionPackageList.add(questionPackage);
		}
		if(questionPackageList==null) return null;
		//foreach lay rawQuestionList them vao questionList
		for(int i =0;i<questionPackageList.size();i++) {
			questionList.addAll(questionPackageList.get(i).getQuestions());
		}
		//tra ve ket qua
		return questionList;
	}

	@Override
	public boolean checkQuestionIsCorrect(Question question) {
		boolean isCorrect = true;
		for(int i =0;i<question.getAnswers().size();i++) {
			Answer answerInDB = answerRepository.findById(question.getAnswers().get(i).getAnswerID()).get();
			if(question.getAnswers().get(i).getIdCorrect()!=answerInDB.getIdCorrect()) {
				isCorrect=false;
				break;
			}
		}
		return isCorrect;
	}
}
