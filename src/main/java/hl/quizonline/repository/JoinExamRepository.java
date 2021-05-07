package hl.quizonline.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import hl.quizonline.entity.Account;
import hl.quizonline.entity.ExamPackage;
import hl.quizonline.entity.JoinExamination;
import hl.quizonline.entity.JoinExaminationPK;

@Repository
public interface JoinExamRepository extends JpaRepository<JoinExamination,Long> {
	List<JoinExamination> findByExamPackage(ExamPackage examPackage);
	List<JoinExamination> findByAccountAndExamPackage(Account account, ExamPackage examPackage);
	Page<JoinExamination> findByAccount(Pageable pageable,Account account);
	Page<JoinExamination> findByExamPackage(ExamPackage examPackage, Pageable pageable);
}
