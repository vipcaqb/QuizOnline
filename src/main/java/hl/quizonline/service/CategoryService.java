package hl.quizonline.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import hl.quizonline.entity.Category;

// TODO: Auto-generated Javadoc
/**
 * The Interface CategoryService.
 */
public interface CategoryService {
	
	/**
	 * Gets the all.
	 *
	 * @return the all
	 */
	List<Category> getAll();
	
	/**
	 * Find by ID.
	 *
	 * @param categoryID the category ID
	 * @return the category
	 */
	Category findByID(Integer categoryID);
	
	/**
	 * Creates the.
	 *
	 * @param category the category
	 */
	void create(Category category);
	
	/**
	 * Edits the.
	 *
	 * @param category the category
	 */
	void edit(Category category);
	
	/**
	 * Delete.
	 *
	 * @param category the category
	 */
	void delete(Category category);
	
	/**
	 * Delete.
	 *
	 * @param categoryID the category ID
	 */
	void delete(int categoryID);
	
	/**
	 * Exist.
	 *
	 * @param categoryName the category name
	 * @return true, if successful
	 */
	boolean exist(String categoryName);
	
	Page<Category> searchByName(String name, int pageNo);
}
