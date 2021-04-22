package hl.quizonline.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hl.quizonline.entity.Question;
import hl.quizonline.entity.QuestionPackage;
import hl.quizonline.repository.QuestionPackageRepository;
import hl.quizonline.repository.QuestionRepository;
import hl.quizonline.service.QuestionService;

@Service
public class QuestionServiceImpl implements QuestionService {
	
	@Autowired
	QuestionRepository questionRepository;
	@Autowired
	QuestionPackageRepository questionPackageRepository;

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

}
