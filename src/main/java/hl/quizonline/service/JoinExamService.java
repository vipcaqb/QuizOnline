package hl.quizonline.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import hl.quizonline.entity.Account;
import hl.quizonline.entity.Category;
import hl.quizonline.entity.ExamPackage;
import hl.quizonline.entity.Examination;
import hl.quizonline.entity.JoinExamination;

// TODO: Auto-generated Javadoc
/**
 * The Interface JoinExamService.
 */
public interface JoinExamService {

	/**
	 * Gets the all.
	 *
	 * @return the all
	 */
	List<JoinExamination> getAll();
	
	/**
	 * Gets the by examination.
	 *
	 * @param examPackage the exam package
	 * @return the by examination
	 */
	List<JoinExamination> getByExamPackage(ExamPackage examPackage);
	
	/**
	 * Gets the by exam package.
	 *
	 * @param examPackage the exam package
	 * @param pageNo the page no
	 * @param pageSize the page size
	 * @return the by exam package
	 */
	Page<JoinExamination> getByExamPackage(ExamPackage examPackage, int pageNo, int pageSize, Sort sort);
	
	/**
	 * Creates the.
	 *
	 * @param joinExamination the join examination
	 */
	void create(JoinExamination joinExamination);
	
	/**
	 * Edits the.
	 *
	 * @param joinExamination the join examination
	 */
	void edit(JoinExamination joinExamination);
	
	/**
	 * Delete.
	 *
	 * @param joinExamination the join examination
	 */
	void delete(JoinExamination joinExamination);
	
	/**
	 * Gets the exam times.
	 *
	 * @param account the account
	 * @param examPackage the exam package
	 * @return the exam times
	 */
	int getExamTimes(Account account,ExamPackage examPackage);
	
	/**
	 * Gets the list by account.
	 *
	 * @param account the account
	 * @param pageNo the page no
	 * @param pageSize the page size
	 * @return the list by account
	 */
	Page<JoinExamination> getListByAccount(Account account, int pageNo, int pageSize);
	
	
}
