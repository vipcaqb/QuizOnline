package hl.quizonline.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import hl.quizonline.entity.Account;
import hl.quizonline.entity.ExamPackage;
import hl.quizonline.entity.Examination;
import hl.quizonline.entity.JoinExamination;
import hl.quizonline.repository.AccountRepository;
import hl.quizonline.repository.ExamPackageRepository;
import hl.quizonline.repository.JoinExamRepository;
import hl.quizonline.service.ExamPackageService;
import hl.quizonline.service.ExaminationService;

@Service
public class ExamPackageImpl implements ExamPackageService {
	
	@Autowired
	ExamPackageRepository examPackageRepository;
	
	@Autowired
	AccountRepository accountRepository;
	
	@Autowired
	ExaminationService examinationService;
	
	@Autowired
	JoinExamRepository joinExamRepository;

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
	@Transactional
	public void delete(Integer examPackageID) {
		//get examPackage
		Optional<ExamPackage> opExamPackage = examPackageRepository.findById(examPackageID);
		if(opExamPackage.isEmpty()) return;
		ExamPackage examPackage = opExamPackage.get();
		
		List<JoinExamination> joinExamList = examPackage.getJoinExaminations();
		//delete join exam
		for(int i = 0 ; i<joinExamList.size();i++) {
			joinExamRepository.delete(joinExamList.get(i));
		}
		//delete examination
		
		List<Examination> examinationList = examPackage.getExaminations();
		for(int i =0; i<examinationList.size();i++) {
			examinationService.delete(examinationList.get(i));
		}
		//delete examPackageCategory
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

	@Override
	public Page<ExamPackage> getPageByUsername(String username, int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo-1, pageSize);
		Account acc = accountRepository.findByUsername(username).get();
		Page<ExamPackage> page = examPackageRepository.findByAccount(acc, pageable);
		return page;
	}

	@Override
	public Page<ExamPackage> searchListByTitle(String examPackageTitlte, int categoryID, int pageNo, int pageSize,Sort sort) {
		Pageable pageable = PageRequest.of(pageNo-1, pageSize, sort);
		Page<ExamPackage> page = null;
		if(categoryID <0) {
			page = examPackageRepository.findByExamPackageTitleContains(examPackageTitlte, pageable);
		}
		else {
			page = examPackageRepository.findByExamPackageTitleContainsAndHasCategoryID(examPackageTitlte, categoryID, pageable);
		}
		return page;
	}

	@Override
	public Page<ExamPackage> searchListByFullnameAuthor(String fullname, int categoryID, int pageNo, int pageSize,Sort sort) {
		Pageable pageable = PageRequest.of(pageNo-1, pageSize, sort);
		Page<ExamPackage> page = null;
		
		if(categoryID<0) {
			page = examPackageRepository.findByAccountFullnameContains(fullname, pageable);
		}
		else {
			page = examPackageRepository.findByAccountFullnameContainsAndCategoryID(fullname, categoryID, pageable);
		}
		return page;
	}

	@Override
	public Page<ExamPackage> searchListByUsernameAuthor(String username, int categoryID, int pageNo, int pageSize,Sort sort) {
		Pageable pageable = PageRequest.of(pageNo-1, pageSize, sort);
		Page<ExamPackage> page = null;
		if(categoryID<0) {
			page = examPackageRepository.findByAccountUsernameContains(username, pageable);
		}
		else {
			page = examPackageRepository.findByAccountUsernameContainsAndCategoryID(username, categoryID, pageable);
		}
		return page;
	}

	@Override
	public Page<ExamPackage> searchList(int pageNo, int pageSize,Sort sort) {
		Pageable pageable = PageRequest.of(pageNo-1, pageSize, sort);
		return examPackageRepository.findAll(pageable);
	}

	@Override
	public Page<ExamPackage> getPageByUsername(String username, int pageNo, int pageSize, Sort sort) {
		Pageable pageable = PageRequest.of(pageNo-1, pageSize, sort);
		Account acc = accountRepository.findByUsername(username).get();
		return examPackageRepository.findByAccount(acc, pageable);
	}

	@Override
	public Page<ExamPackage> searchList(int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo-1, pageSize);
		return examPackageRepository.findAll(pageable);
	}

	@Override
	public void inscreaseDoExamTimes(int examPackageID, int doExamTimes) {
		if(examPackageID<0) return;
		Optional<ExamPackage> opExamPackage = examPackageRepository.findById(examPackageID);
		if(opExamPackage.isEmpty()) return;
		ExamPackage examPackage = opExamPackage.get();
		examPackage.setDoExamTime(examPackage.getDoExamTime()+examPackageID);
		examPackageRepository.save(examPackage);
	}
}
