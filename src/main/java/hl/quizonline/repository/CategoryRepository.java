package hl.quizonline.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import hl.quizonline.entity.Category;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Integer> {
	List<Category> findByCategoryName(String categoryName);
}
