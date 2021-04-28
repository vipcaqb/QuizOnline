package hl.quizonline.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hl.quizonline.entity.Account;
import hl.quizonline.entity.ExamPackage;
import hl.quizonline.repository.AccountRepository;
import hl.quizonline.repository.ExamPackageRepository;
import hl.quizonline.service.ExamPackageService;
import javassist.NotFoundException;

@Service
public class ExamPackageImpl implements ExamPackageService {
	
	@Autowired
	ExamPackageRepository examPackageRepository;
	
	@Autowired
	AccountRepository accountRepository;

	@Override
	public List<ExamPackage> getList(String username){
		Optional<Account> acc = accountRepository.findByUsername(username);
		if(acc.isPresent()) {
			List<ExamPackage> list = examPackageRepository.findByAccount(acc.get());
			return list;
		}
		else return null;
	}

	@Override
	public List<ExamPackage> findWith(String key) {
		return examPackageRepository.findByExamPackageTitleContaining(key);
	}

	@Override
	public void create(ExamPackage examPackage) {
		examPackageRepository.save(examPackage);
	}

	@Override
	public void delete(Integer examPackageID) {
		//delete join exam
		
		//delete examQuestion
		
		//delete examination
		
		//delete examPackageCategory
		
		//delete examPackage
		
		examPackageRepository.deleteById(examPackageID);
	}

	@Override
	public void update(ExamPackage examPackage) {
		
		Optional<ExamPackage> eOptional = examPackageRepository.findById(examPackage.getExamPackageID());
		if(eOptional.isPresent()) {
			examPackageRepository.save(examPackage);
		}
	}

	@Override
	public ExamPackage getExamPackage(int examPackageID) {
		Optional<ExamPackage> oep = examPackageRepository.findById(examPackageID);
		if(oep.isPresent()) {
			return oep.get();
		}
		return null;
	}

	@Override
	public List<ExamPackage> getList() {
		return (List<ExamPackage>) examPackageRepository.findAll();
	}

	@Override
	public List<ExamPackage> getListPresent() {
		Date now = new Date(System.currentTimeMillis());
		return examPackageRepository.findByIsExamAndPresent(now);
	}

	@Override
	public List<ExamPackage> getListExercise() {
		return examPackageRepository.findByIsExerciseExam(true);
	}

	@Override
	public List<ExamPackage> getListIsComing() {
		Date now = new Date(System.currentTimeMillis());
		return examPackageRepository.findByIsExamAndCommingSoon(now);
	}

	@Override
	public List<ExamPackage> getListExpired() {
		Date now = new Date(System.currentTimeMillis());
		return examPackageRepository.findExamExpired(now);
	}

	@Override
	public boolean isPresent(ExamPackage examPackage) {
		Date now = new Date(System.currentTimeMillis());
		if(examPackage.isExerciseExam()|| examPackage.getStartDatetime()==null || examPackage.getEndDatetime()==null) {
			return true;
		}
		if(now.compareTo(examPackage.getStartDatetime()) <0|| now.compareTo(examPackage.getEndDatetime())>0) {
			return false;
		}
		else return true;
	}
}
