package hl.quizonline.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hl.quizonline.entity.Account;
import hl.quizonline.entity.QuestionPackage;
import hl.quizonline.repository.AccountRepository;
import hl.quizonline.repository.QuestionPackageRepository;
import hl.quizonline.service.QuestionPackageService;

@Service
public class QuestionPackageServiceImpl implements QuestionPackageService {

	@Autowired
	QuestionPackageRepository questionPackageRepository;
	
	@Autowired
	AccountRepository accountRepository;
	
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
	public void delete(int questionPackageID) {
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

}
