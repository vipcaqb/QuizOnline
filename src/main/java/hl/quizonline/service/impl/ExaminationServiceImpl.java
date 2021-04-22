package hl.quizonline.service.impl;

import java.util.List;

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
	public void create(Examination exam) {
		examinationRepository.save(exam);
	}

	@Override
	public void edit(Examination exam) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Integer examID) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Examination exam) {
		// TODO Auto-generated method stub
		
	}

}
