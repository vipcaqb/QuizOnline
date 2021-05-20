package hl.quizonline.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import hl.quizonline.entity.Answer;
import hl.quizonline.entity.ExamPackage;
import hl.quizonline.entity.ExamQuestion;
import hl.quizonline.entity.Examination;
import hl.quizonline.entity.Image;
import hl.quizonline.entity.Question;
import hl.quizonline.entity.QuestionPackage;
import hl.quizonline.repository.AnswerRepository;
import hl.quizonline.repository.ImageRepository;
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
	@Autowired
	ImageRepository imageRepository;

	@Override
public List<Question> getAll(int questionPackageID) {
		QuestionPackage qp = questionPackageRepository.findById(questionPackageID).get();
		return questionRepository.findByQuestionPackage(qp);
	}

	@Override
	public Question create(Question question) {
		return questionRepository.save(question);
	}

	@Override
	public Question edit(Question question) {
		Optional<Question> oq = questionRepository.findById(question.getQuestionID());
		if(oq.isPresent()) {
			return questionRepository.save(question);
		}
		return null;
	}

	@Override
	@Transactional
	public void delete(int questionID) {
		Optional<Question> opQuestion = questionRepository.findById(questionID);
		if(opQuestion.isEmpty()) return;
		Question question = opQuestion.get();
		
		if(question.getImages()!=null) {
			for(int i =0;i <question.getImages().size();i++) {
				Image image = question.getImages().get(i);
				
				imageRepository.delete(image);
			}
		}
		if(question.getAnswers()!=null) {
			for(int i =0;i<question.getAnswers().size();i++) {
				Answer answer = question.getAnswers().get(i);
				
				answerRepository.delete(answer);
			}
		}
		questionRepository.delete(question);
	}

	@Override
	public void delete(Question question) {
		this.delete(question.getQuestionID());
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

	@Override
	public Page<Question> searchQuestionByContent(String questionContent, int qpID, int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo-1, pageSize);
		QuestionPackage qp = questionPackageRepository.findById(qpID).get();
		Page<Question> page = questionRepository.findByQuestionPackageAndQuestionContentContains(qp, questionContent, pageable);
		return page;
	}

	@Override
	public Page<Question> getAll(int qpID, int pageNo, int pageSize) {
		QuestionPackage qp = new QuestionPackage();
		qp.setQuestionPackageID(qpID);
		Pageable pageable = PageRequest.of(pageNo-1, pageSize);
		return questionRepository.findByQuestionPackage(qp, pageable);
	}

	@Override
	@Transactional
	public void addListQuestionToQuestionPackage(int questionPackageID, List<Question> questionList) {
		//Lây questionPackage
		QuestionPackage questionPackage = questionPackageRepository.findById(questionPackageID).get();
		
		for(int i=0;i<questionList.size();i++) {
			Question question = questionList.get(i);
			
			question.setQuestionPackage(questionPackage);
			questionRepository.save(question);
			for(int j = 0; j <question.getAnswers().size();j++) {
				Answer answer = question.getAnswers().get(j);
				answer.setQuestion(question);
				answerRepository.save(answer);
			}
		}
		System.out.println("Đưa dữ liệu lên thành công");
	}

	@Override
	public void deleteAllAnswer(int questionID) {
		Question question = getByID(questionID);
		if(question!= null) {
			if(question.getAnswers()!= null) {
				List<Answer> answerList = question.getAnswers();
				for(Answer answer: answerList) {
					answerRepository.delete(answer);
				}
			}
		}
	}
}
