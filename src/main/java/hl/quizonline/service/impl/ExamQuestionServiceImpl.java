package hl.quizonline.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hl.quizonline.entity.ExamQuestion;
import hl.quizonline.entity.Examination;
import hl.quizonline.repository.ExamQuestionRepository;
import hl.quizonline.service.ExamQuestionService;

@Service
public class ExamQuestionServiceImpl implements ExamQuestionService {
	
	@Autowired
	ExamQuestionRepository examQuestionRepository;

	@Override
	public ExamQuestion getExamQuestion(int examQuestionID) {
		return examQuestionRepository.findById(examQuestionID).get();
	}

	@Override
	public List<ExamQuestion> getList() {
		return (List<ExamQuestion>) examQuestionRepository.findAll();
	}

	@Override
	public void create(ExamQuestion examQuestion) {
		examQuestionRepository.save(examQuestion);
	}

	@Override
	public void edit(ExamQuestion examQuestion) {
		//check exist
		Optional<ExamQuestion> oeq = examQuestionRepository.findById(examQuestion.getExamQuestionID());
		if(oeq.isPresent()) {
			examQuestionRepository.save(examQuestion);
		}
	}

	@Override
	public void delete(int examQuestionID) {
		examQuestionRepository.deleteById(examQuestionID);
	}

	@Override
	public void delete(ExamQuestion examQuestion) {
		examQuestionRepository.delete(examQuestion);
	}

	@Override
	public List<ExamQuestion> getList(Examination exam) {
		return examQuestionRepository.findByExamination(exam);
	}

	@Override
	@Transactional
	public void delete(Examination exam) {
		List<ExamQuestion> examQuestionList = examQuestionRepository.findByExamination(exam);
		for(int i=0;i<examQuestionList.size();i++ ) {
			examQuestionRepository.delete(examQuestionList.get(i));
		}
	}

}
