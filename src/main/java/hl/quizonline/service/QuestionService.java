package hl.quizonline.service;

import java.util.List;

import hl.quizonline.entity.Question;
import hl.quizonline.entity.QuestionPackage;

// TODO: Auto-generated Javadoc
/**
 * The Interface QuestionService.
 */
public interface QuestionService {
	
	/**
	 * Gets the by ID.
	 *
	 * @param questionID the question ID
	 * @return the by ID
	 */
	Question getByID(int questionID);
	
	/**
	 * Gets the all.
	 *
	 * @param qpID the qp ID
	 * @return the all
	 */
	List<Question> getAll(int qpID);
	
	/**
	 * Creates the.
	 *
	 * @param question the question
	 */
	void create(Question question);
	
	/**
	 * Edits the.
	 *
	 * @param question the question
	 */
	void edit(Question question);
	
	/**
	 * Delete.
	 *
	 * @param questionID the question ID
	 */
	void delete(int questionID);
	
	/**
	 * Delete.
	 *
	 * @param question the question
	 */
	void delete(Question question);
	
}
