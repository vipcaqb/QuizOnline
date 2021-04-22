package hl.quizonline.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hl.quizonline.entity.Answer;
import hl.quizonline.entity.Question;
import hl.quizonline.repository.AnswerRepository;
import hl.quizonline.service.AnswerService;

@Service
public class AnswerServiceImpl implements AnswerService {
	
	@Autowired
	AnswerRepository answerRepository;

	@Override
	public List<Answer> getAll(Question question) {
		return answerRepository.findByQuestion(question);
	}

	@Override
	public void create(Answer answer) {
		answerRepository.save(answer);
	}

	@Override
	public void edit(Answer answer) {
		Optional<Answer> oa = answerRepository.findById(answer.getAnswerID());
		if(oa.isPresent()) {
			answerRepository.save(answer);
		}
	}

	@Override
	public void delete(int answerID) {
		answerRepository.deleteById(answerID);
	}

	@Override
	public void delete(Answer answer) {
		answerRepository.delete(answer);
	}

}
