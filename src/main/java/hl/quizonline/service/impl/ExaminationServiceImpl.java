package hl.quizonline.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import hl.quizonline.entity.ExamPackage;
import hl.quizonline.entity.ExamQuestion;
import hl.quizonline.entity.Examination;
import hl.quizonline.repository.ExamPackageRepository;
import hl.quizonline.repository.ExamQuestionRepository;
import hl.quizonline.repository.ExaminationRepository;
import hl.quizonline.service.ExaminationService;

@Service
public class ExaminationServiceImpl implements ExaminationService {
	
	@Autowired
	ExamQuestionRepository examQuestionRepository;
	
	@Autowired
	ExamPackageRepository examPackageRepository;
	
	@Autowired
	ExaminationRepository examinationRepository;
	
	@Override
	public List<Examination> getAll(int exampackageID) {
		ExamPackage ep = examPackageRepository.findById(exampackageID).get();
		return examinationRepository.findByExamPackage(ep);
	}

	@Override
	public Examination create(Examination exam) {
		return examinationRepository.save(exam);
	}

	@Override
	public void edit(Examination exam) {
		Optional<Examination> oe = examinationRepository.findById(exam.getExaminationID());
		if(oe.isPresent()) {
			examinationRepository.save(exam);
		}
	}

	@Override
	public void delete(Integer examID) {
		Examination exam = new Examination();
		exam.setExaminationID(examID);
		//Xoa tat ca examQuestion
		List<ExamQuestion> examQuestionList = examQuestionRepository.findByExamination(exam);
		for(int i =0; i < examQuestionList.size();i++) {
			examQuestionRepository.delete(examQuestionList.get(i));
		}
		examinationRepository.deleteById(examID);
	}

	@Override
	public void delete(Examination exam) {
		this.delete(exam.getExaminationID());
	}

	@Override
	public Examination getExam(int examID) {
		return examinationRepository.findById(examID).get();
	}

	@Override
	public Page<Examination> getPageByExamPackage(int examPackageID,int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo-1, pageSize);
		ExamPackage ep = new ExamPackage();
		ep.setExamPackageID(examPackageID);
		Page<Examination> page = examinationRepository.findByExamPackage(ep, pageable);
		return page;
	}

	@Override
	public Page<Examination> searchPageOfExamPackageByTitle(int examPackageID, String examinationTitle, int pageNo,
			int pageSize) {
		Pageable pageable = PageRequest.of(pageNo-1, pageSize);
		ExamPackage examPackage = new ExamPackage();
		examPackage.setExamPackageID(examPackageID);
		return examinationRepository.findByExamPackageAndExaminationTitleContains(examPackage, examinationTitle, pageable);
	}

}
