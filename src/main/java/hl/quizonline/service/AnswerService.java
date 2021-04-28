package hl.quizonline.service;

import java.util.List;

import hl.quizonline.entity.Answer;
import hl.quizonline.entity.Question;

// TODO: Auto-generated Javadoc
/**
 * The Interface AnswerService.
 */
public interface AnswerService {
	
	/**
	 * Gets the all.
	 *
	 * @param question the question
	 * @return the all
	 */
	List<Answer> getAll(Question question);
	
	/**
	 * Gets the answer.
	 *
	 * @param answerID the answer ID
	 * @return the answer
	 */
	Answer getAnswer(int answerID);
	
	/**
	 * Creates the.
	 *
	 * @param answer the answer
	 */
	void create(Answer answer);
	
	/**
	 * Edits the.
	 *
	 * @param answer the answer
	 */
	void edit(Answer answer);
	
	/**
	 * Delete.
	 *
	 * @param answerID the answer ID
	 */
	void delete(int answerID);
	
	/**
	 * Delete.
	 *
	 * @param answer the answer
	 */
	void delete(Answer answer);
	
}
