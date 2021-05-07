package hl.quizonline.service.impl;

import java.util.List;

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
import hl.quizonline.repository.JoinExamRepository;
import hl.quizonline.service.JoinExamService;

@Service
public class JoinExamServiceImpl implements JoinExamService {

	@Autowired
	JoinExamRepository joinExamRepository;
	
	@Override
	public List<JoinExamination> getAll() {
		return (List<JoinExamination>) joinExamRepository.findAll();
	}


	@Override
	public void create(JoinExamination joinExamination) {
		joinExamRepository.save(joinExamination);
	}

	@Override
	public void edit(JoinExamination joinExamination) {
	}

	@Override
	public void delete(JoinExamination joinExamination) {
		joinExamRepository.delete(joinExamination);
	}


	@Override
	public List<JoinExamination> getByExamPackage(ExamPackage examPackage) {
		return joinExamRepository.findByExamPackage(examPackage);
	}


	@Override
	public int getExamTimes(Account account, ExamPackage examPackage) {
		List<JoinExamination> joinExamList = joinExamRepository.findByAccountAndExamPackage(account, examPackage);
		int examTimes =0;
		if(joinExamList!=null &&joinExamList.size()>0) {
			examTimes = joinExamList.size();
		}
		return examTimes;
	}


	@Override
	public Page<JoinExamination> getListByAccount(Account account, int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		return joinExamRepository.findByAccount(pageable, account);
	}


	@Override
	public Page<JoinExamination> getByExamPackage(ExamPackage examPackage, int pageNo, int pageSize,Sort sort) {
		Pageable pageable = PageRequest.of(pageNo-1, pageSize,sort);
		Page<JoinExamination> page = joinExamRepository.findByExamPackage(examPackage, pageable);
		return page;
	}



}
