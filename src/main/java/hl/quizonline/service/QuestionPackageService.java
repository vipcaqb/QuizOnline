package hl.quizonline.service;

import java.util.List;
import java.util.Optional;

import hl.quizonline.entity.QuestionPackage;

// TODO: Auto-generated Javadoc
/**
 * The Interface QuestionPackageService.
 */
public interface QuestionPackageService {
	
	/**
	 * Find by ID.
	 *
	 * @return the optional
	 */
	QuestionPackage findByID(int qpID);
	/**
	 * Gets the list.
	 *
	 * @param username the username
	 * @return the list
	 */
	List<QuestionPackage> getList(String username);
	
	/**
	 * Creates the.
	 *
	 * @param questionPackage the question package
	 */
	void create(QuestionPackage questionPackage);
	
	/**
	 * Edits the.
	 *
	 * @param questionPackage the question package
	 */
	void edit(QuestionPackage questionPackage);
	
	/**
	 * Delete.
	 *
	 * @param questionPackageID the question package ID
	 */
	void delete(int questionPackageID);
	
	/**
	 * Delete.
	 *
	 * @param questionPackage the question package
	 */
	void delete(QuestionPackage questionPackage);
	
}
