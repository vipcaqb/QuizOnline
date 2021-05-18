package hl.quizonline.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import hl.quizonline.entity.QuestionPackage;

// TODO: Auto-generated Javadoc
/**
 * The Interface QuestionPackageService.
 */
public interface QuestionPackageService {
	
	/**
	 * Find by ID.
	 *
	 * @param qpID the qp ID
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
	 * Gets the list.
	 *
	 * @param username the username
	 * @param pageable the pageable
	 * @return the list
	 */
	Page<QuestionPackage> getList(String username, int pageNo, int pageSize);
	
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
	
	Page<QuestionPackage> searchByName(String name, int pageNo);
	
}
