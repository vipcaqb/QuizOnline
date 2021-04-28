package hl.quizonline.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import hl.quizonline.entity.ExamQuestion;
import hl.quizonline.entity.Examination;

@Repository
public interface ExamQuestionRepository extends CrudRepository<ExamQuestion, Integer> {
	List<ExamQuestion> findByExamination(Examination exam);
}
