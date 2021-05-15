package hl.quizonline.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import hl.quizonline.config.MyConstances;
import hl.quizonline.entity.Category;
import hl.quizonline.entity.ExamPackage;
import hl.quizonline.repository.CategoryRepository;
import hl.quizonline.repository.ExamPackageRepository;
import hl.quizonline.service.CategoryService;
import hl.quizonline.service.ExamPackageService;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired
	ExamPackageRepository exampackageRepository;
	
	@Autowired
	ExamPackageService examPackageService;
	
	@Autowired
	ExamPackageRepository examPackageRepository;

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
	@Transactional
	public void delete(int categoryID) {
		Optional<Category> oc = categoryRepository.findById(categoryID);
		if(oc.isEmpty()) return;
		
		if(oc.get().getExamPackages().size()==0) {
			categoryRepository.deleteById(categoryID);
		}
		else {
			Category category = oc.get();
			List<ExamPackage> examPackageList = examPackageRepository.findByCategoryID(categoryID);
			for(ExamPackage item : examPackageList) {
				examPackageService.clearCategory(item);
			}
			categoryRepository.delete(category);
		}
	}

	@Override
	public boolean exist(String categoryName) {
		List<Category> list = categoryRepository.findByCategoryName(categoryName);
		if(list.size() == 0) {
			return false;
		}
		return true;
	}

	@Override
	public Page<Category> searchByName(String name, int pageNo) {
		Pageable pageable = PageRequest.of(pageNo-1, MyConstances.PAGE_SIZE);
		return categoryRepository.findByCategoryNameContains(name, pageable);
	}

	
}
