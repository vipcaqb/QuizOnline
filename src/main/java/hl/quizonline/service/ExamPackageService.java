
package hl.quizonline.service;

import java.util.List;

import hl.quizonline.entity.ExamPackage;

// TODO: Auto-generated Javadoc
/**
 * The Interface ExamPackageService.
 */
public interface ExamPackageService {
	
	/**
	 * Gets the list.
	 *
	 * @return the list
	 */
	List<ExamPackage> getList();
	
	/**
	 * Find with.
	 *
	 * @param key the key
	 * @return the list
	 */
	List<ExamPackage> findWith(String key);
	
	/**
	 * Creates the exampackage.
	 *
	 * @param username the username
	 * @param examPackage the exam package
	 */
	void create(String username,ExamPackage examPackage);
	
	/**
	 * Delete one exampackage.
	 *
	 * @param username the username
	 * @param examPackageID the exam package ID
	 */
	void delete(String username,Integer examPackageID);
	
	/**
	 * Update a exampackage.
	 *
	 * @param username the username
	 * @param examPackage the exam package
	 */
	void update(String username, ExamPackage examPackage);
	
}
