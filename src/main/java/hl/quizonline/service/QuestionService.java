package hl.quizonline.service;

import java.util.List;

import org.springframework.data.domain.Page;

import hl.quizonline.entity.ExamPackage;
import hl.quizonline.entity.Examination;
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
	 * @return the question
	 */
	Question create(Question question);
	
	/**
	 * Edits the.
	 *
	 * @param question the question
	 */
	Question edit(Question question);
	
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
	
	/**
	 * Shuffle list question.
	 *
	 * @param examination the examination
	 * @return the question list
	 */
	List<Question> getQuestionList(Examination examination);
	
	/**
	 * Check question'user is correct.
	 *
	 * @param question the question
	 * @return true, if the question is correct
	 */
	boolean checkQuestionIsCorrect(Question question);
	
	/**
	 * Search question in question package by content.
	 *
	 * @param questionContent the question content
	 * @param qpID the qp ID
	 * @param pageNo the page no
	 * @param pageSize the page size
	 * @return the page
	 */
	Page<Question> searchQuestionByContent(String questionContent, int qpID, int pageNo, int pageSize);
	
	/**
	 * Gets the all.
	 *
	 * @param qpID the qp ID
	 * @param pageNo the page no
	 * @param pageSize the page size
	 * @return the all
	 */
	Page<Question> getAll(int qpID, int pageNo, int pageSize);
	
	/**
	 * Thêm danh sách câu hỏi questionList vào bộ đề có ID là questionPackageID.
	 *
	 * @param questionPackageID the question package ID
	 * @param questionList the question list
	 */
	void addListQuestionToQuestionPackage(int questionPackageID, List<Question> questionList);
	
	/**
	 * Xóa tất cả phương án trong 1 câu hỏi
	 *
	 * @param questionID the question ID
	 */
	void deleteAllAnswer(int questionID);
}
