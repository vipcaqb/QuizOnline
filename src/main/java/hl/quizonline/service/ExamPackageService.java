
package hl.quizonline.service;

import java.util.List;

import hl.quizonline.entity.ExamPackage;
import javassist.NotFoundException;

// TODO: Auto-generated Javadoc
/**
 * The Interface ExamPackageService.
 */
public interface ExamPackageService {
	
	/**
	 * Gets the list.
	 *
	 * @param username the username
	 * @return the list
	 */
	List<ExamPackage> getList(String username);
	
	/**
	 * Gets the list.
	 *
	 * @return the list
	 */
	List<ExamPackage> getList();
	
	/**
	 * Gets the list present.
	 *
	 * @return the list present
	 */
	List<ExamPackage> getListPresent();
	
	/**
	 * Gets the list exercise.
	 *
	 * @return the list exercise
	 */
	List<ExamPackage> getListExercise();
	
	/**
	 * Gets the list incoming start.
	 *
	 * @return the list incoming
	 */
	List<ExamPackage> getListIsComing();
	
	/**
	 * Gets the list expired.
	 *
	 * @return the list expired
	 */
	List<ExamPackage> getListExpired();
	
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
	 * @param examPackage the exam package
	 */
	void create(ExamPackage examPackage);
	
	/**
	 * Delete one exampackage.
	 *
	 * @param examPackageID the exam package ID
	 */
	void delete(Integer examPackageID);
	
	/**
	 * Update a exampackage.
	 *
	 * @param examPackage the exam package
	 */
	void update(ExamPackage examPackage);
	
	/**
	 * Gets the exam package.
	 *
	 * @param examPackageID the exam package ID
	 * @return the exam package
	 */
	ExamPackage getExamPackage(int examPackageID);
	
	/**
	 * Checks if this exam package is present.
	 *
	 * @return true, if is present
	 */
	boolean isPresent(ExamPackage examPackage);
}
