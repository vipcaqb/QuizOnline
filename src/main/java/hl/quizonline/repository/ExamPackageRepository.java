package hl.quizonline.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import hl.quizonline.entity.Account;
import hl.quizonline.entity.ExamPackage;

@Repository
public interface ExamPackageRepository extends CrudRepository<ExamPackage, Integer> {
	List<ExamPackage> findByExamPackageTitleContaining(String key);
	List<ExamPackage> findByAccount(Account account);
}
