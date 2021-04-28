package hl.quizonline.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import hl.quizonline.entity.Account;
import hl.quizonline.entity.ExamPackage;

@Repository
public interface ExamPackageRepository extends CrudRepository<ExamPackage, Integer> {
	List<ExamPackage> findByExamPackageTitleContaining(String key);
	List<ExamPackage> findByAccount(Account account);
	
	List<ExamPackage> findByIsExerciseExam(boolean isExercise);
	
	@Query("SELECT ep from ExamPackage ep WHERE ep.isExerciseExam = false AND ep.startDatetime < ?1 AND ep.endDatetime > ?1")
	List<ExamPackage> findByIsExamAndPresent(Date currentDateTime);
	@Query("SELECT ep from ExamPackage ep WHERE ep.isExerciseExam = false AND ep.startDatetime > ?1")
	List<ExamPackage> findByIsExamAndCommingSoon(Date currentDateTime);
	@Query("SELECT ep FROM ExamPackage ep WHERE ep.isExerciseExam = false AND ep.endDatetime < ?1")
	List<ExamPackage> findExamExpired(Date currentDateTime);
	
}
