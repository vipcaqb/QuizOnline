package hl.quizonline.service;

import java.util.List;

import hl.quizonline.entity.ExamQuestion;

// TODO: Auto-generated Javadoc
/**
 * The Interface ExamQuestionService.
 */
public interface ExamQuestionService {

	/**
	 * Gets the exam question.
	 *
	 * @param examQuestionID the exam question ID
	 * @return the exam question
	 */
	ExamQuestion getExamQuestion(int examQuestionID);
	
	/**
	 * Gets the list.
	 *
	 * @return the list
	 */
	List<ExamQuestion> getList();
	
	/**
	 * Creates the.
	 *
	 * @param examQuestion the exam question
	 */
	void create(ExamQuestion examQuestion);
	
	/**
	 * Edits the.
	 *
	 * @param examQuestion the exam question
	 */
	void edit(ExamQuestion examQuestion);
	
	/**
	 * Delete.
	 *
	 * @param examQuestionID the exam question ID
	 */
	void delete(int examQuestionID);
	
	/**
	 * Delete.
	 *
	 * @param examQuestion the exam question
	 */
	void delete(ExamQuestion examQuestion);
	
}
