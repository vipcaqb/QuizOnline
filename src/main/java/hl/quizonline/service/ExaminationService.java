package hl.quizonline.service;

import java.util.List;

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
	
}
