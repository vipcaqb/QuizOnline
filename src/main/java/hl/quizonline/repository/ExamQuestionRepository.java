package hl.quizonline.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import hl.quizonline.entity.ExamQuestion;

@Repository
public interface ExamQuestionRepository extends CrudRepository<ExamQuestion, Integer> {

}
