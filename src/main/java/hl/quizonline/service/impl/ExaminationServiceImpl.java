package hl.quizonline.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hl.quizonline.entity.ExamPackage;
import hl.quizonline.entity.Examination;
import hl.quizonline.repository.ExamPackageRepository;
import hl.quizonline.repository.ExaminationRepository;
import hl.quizonline.service.ExaminationService;

@Service
public class ExaminationServiceImpl implements ExaminationService {

	@Autowired
	ExamPackageRepository examPackageRepository;
	
	@Autowired
	ExaminationRepository examinationRepository;
	
	@Override
	public List<Examination> getAll(int exampackageID) {
		return examinationRepository.findByExaminationID(exampackageID);

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
		examinationRepository.deleteById(examID);
	}

	@Override
	public void delete(Examination exam) {
		examinationRepository.delete(exam);
	}

	@Override
	public Examination getExam(int examID) {
		return examinationRepository.findById(examID).get();
	}

}
