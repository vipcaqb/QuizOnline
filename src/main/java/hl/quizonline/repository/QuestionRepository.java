package hl.quizonline.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import hl.quizonline.entity.Question;
import hl.quizonline.entity.QuestionPackage;

@Repository
public interface QuestionRepository extends CrudRepository<Question, Integer> {
	List<Question> findByQuestionPackage(QuestionPackage questionPackage);
}
