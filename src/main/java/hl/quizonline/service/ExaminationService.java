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
	 * @param ep the ep
	 * @return the all
	 */
	List<Examination> getAll(int exampackageID);
	
	/**
	 * Creates the.
	 *
	 * @param exam the exam
	 */
	void create(Examination exam);
	
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
