package hl.quizonline.service;

import java.util.List;

import hl.quizonline.entity.ExamQuestion;
import hl.quizonline.entity.Examination;

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
	 * Gets the list examination.
	 *
	 * @param exam the exam
	 * @return the list
	 */
	List<ExamQuestion> getList(Examination exam);
	
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
	
	/**
	 * Delete all examQuestion have Exam
	 *
	 * @param exam the exam
	 */
	void delete(Examination exam);
}
