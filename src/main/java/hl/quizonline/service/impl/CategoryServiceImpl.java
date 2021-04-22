package hl.quizonline.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hl.quizonline.entity.Category;
import hl.quizonline.repository.CategoryRepository;
import hl.quizonline.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	CategoryRepository categoryRepository;

	@Override
	public List<Category> getAll() {
		return (List<Category>) categoryRepository.findAll();
	}

	@Override
	public Category findByID(Integer categoryID) {
		return categoryRepository.findById(categoryID).get();
	}

	@Override
	public void create(Category category) {
		categoryRepository.save(category);
	}

	@Override
	public void edit(Category category) {
		//check existing
		Optional<Category> oc = categoryRepository.findById(category.getCategoryID());
		if(oc.isPresent()) {
			categoryRepository.save(category);
		}
	}

	@Override
	public void delete(Category category) {
		categoryRepository.delete(category);
	}

	@Override
	public void delete(int categoryID) {
		categoryRepository.deleteById(categoryID);
	}

	@Override
	public boolean exist(String categoryName) {
		List<Category> list = categoryRepository.findByCategoryName(categoryName);
		if(list.size() == 0) {
			return false;
		}
		return true;
	}

	
}
