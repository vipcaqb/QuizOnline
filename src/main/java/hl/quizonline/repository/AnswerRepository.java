package hl.quizonline.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import hl.quizonline.entity.Answer;
import hl.quizonline.entity.Question;

public interface AnswerRepository extends CrudRepository<Answer, Integer> {
	List<Answer> findByQuestion(Question question);
}
