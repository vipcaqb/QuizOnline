package hl.quizonline.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hl.quizonline.entity.Question;
import hl.quizonline.entity.QuestionPackage;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {
	List<Question> findByQuestionPackage(QuestionPackage questionPackage);
	Page<Question> findByQuestionPackage(QuestionPackage questionPackage,Pageable pageable);
	Page<Question> findByQuestionPackageAndQuestionContentContains(QuestionPackage questionPackage, String questionContent, Pageable pageable);
}
