package hl.quizonline.service;

import java.util.List;

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
	 * @return the exam times
	 */
	int getExamTimes(Account account,ExamPackage examPackage);
}
