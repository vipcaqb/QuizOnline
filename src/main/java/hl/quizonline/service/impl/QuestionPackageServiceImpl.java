package hl.quizonline.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import hl.quizonline.config.MyConstances;
import hl.quizonline.entity.Account;
import hl.quizonline.entity.ExamQuestion;
import hl.quizonline.entity.Question;
import hl.quizonline.entity.QuestionPackage;
import hl.quizonline.repository.AccountRepository;
import hl.quizonline.repository.ExamQuestionRepository;
import hl.quizonline.repository.QuestionPackageRepository;
import hl.quizonline.service.QuestionPackageService;
import hl.quizonline.service.QuestionService;

@Service
public class QuestionPackageServiceImpl implements QuestionPackageService {

	@Autowired
	QuestionPackageRepository questionPackageRepository;
	
	@Autowired
	AccountRepository accountRepository;
	
	@Autowired
	ExamQuestionRepository examQuestionRepository;
	
	@Autowired
	ExamQuestionRepository examQuestion;
	
	@Autowired
	QuestionService questionService;
	
	@Override
	public List<QuestionPackage> getList(String username) {
		Account acc = accountRepository.findByUsername(username).get();
		return questionPackageRepository.findByAccount(acc);
	}

	@Override
	public void create(QuestionPackage questionPackage) {
		questionPackageRepository.save(questionPackage);
	}

	@Override
	public void edit(QuestionPackage questionPackage) {
		Optional<QuestionPackage> oqp = questionPackageRepository.findById(questionPackage.getQuestionPackageID());
		if(oqp.isPresent()) {
			questionPackageRepository.save(questionPackage);
		}
	}

	@Override
	@Transactional
	public void delete(int questionPackageID) {
		Optional<QuestionPackage> opQuestionPackage = questionPackageRepository.findById(questionPackageID);
		if(opQuestionPackage.isEmpty()) return;
		QuestionPackage questionPackage = opQuestionPackage.get();
		//delete examQuestion
		List<ExamQuestion> examQuestionList = questionPackage.getExamQuestions();
		if(examQuestionList!=null) {
			for(int i =0;i <examQuestionList.size();i++) {
				examQuestionRepository.delete(examQuestionList.get(i));
			}
		}
		//delete question
		List<Question> questionList = questionPackage.getQuestions();
		if(questionList!=null) {
			for(int i = 0;i<questionList.size();i++) {
				questionService.delete(questionList.get(i));
			}
		}
		//delete questionPackage
		questionPackageRepository.deleteById(questionPackageID);
	}

	@Override
	public void delete(QuestionPackage questionPackage) {
		questionPackageRepository.delete(questionPackage);
	}

	@Override
	public QuestionPackage findByID(int qpID) {
		Optional<QuestionPackage> oqp = questionPackageRepository.findById(qpID);
		if(oqp.isPresent()) {
			return oqp.get();
		}
		return null;
	}

	@Override
	public Page<QuestionPackage> getList(String username, int pageNo, int pageSize) {
		Account acc = accountRepository.findByUsername(username).get();
		Pageable pageable = PageRequest.of(pageNo-1, pageSize);
		return questionPackageRepository.findByAccount(acc, pageable);
	}

	@Override
	public Page<QuestionPackage> searchByName(String name, int pageNo) {
		Pageable pageable = PageRequest.of(pageNo-1, MyConstances.PAGE_SIZE);
		return questionPackageRepository.findByNameContains(name, pageable);
	}

}
