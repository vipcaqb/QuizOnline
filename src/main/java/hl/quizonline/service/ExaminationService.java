package hl.quizonline.service;

import java.util.List;

import org.springframework.data.domain.Page;

import hl.quizonline.entity.ExamPackage;
import hl.quizonline.entity.Examination;

// TODO: Auto-generated Javadoc
/**
 * The Interface ExaminationService.
 */
public interface ExaminationService {
	
	/**
	 * Gets the all.
	 *
	 * @param exampackageID the exampackage ID
	 * @return the all
	 */
	List<Examination> getAll(int exampackageID);
	
	/**
	 * Gets the exam.
	 *
	 * @param examID the exam ID
	 * @return the exam
	 */
	Examination getExam(int examID);
	
	/**
	 * Creates the.
	 *
	 * @param exam the exam
	 * @return the examination
	 */
	Examination create(Examination exam);
	
	/**
	 * Edits the.
	 *
	 * @param exam the exam
	 */
	void edit(Examination exam);
	
	/**
	 * Delete.
	 *
	 * @param examID the exam ID
	 */
	void delete(Integer examID);
	
	/**
	 * Delete.
	 *
	 * @param exam the exam
	 */
	void delete(Examination exam);
	
	/**
	 * Gets the page by exam package.
	 *
	 * @param examPackageID the exam package ID
	 * @param pageNo the page no
	 * @param pageSize the page size
	 * @return the page by exam package
	 */
	Page<Examination> getPageByExamPackage(int examPackageID,int pageNo, int pageSize);
	
	/**
	 * Search page of exam package by title.
	 *
	 * @param examPackageID the exam package ID
	 * @param examinationTitle the examination title
	 * @param pageNo the page no
	 * @param pageSize the page size
	 * @return the page
	 */
	Page<Examination> searchPageOfExamPackageByTitle(int examPackageID, String examinationTitle, int pageNo, int pageSize);
	
}
