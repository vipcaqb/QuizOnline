package hl.quizonline.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import hl.quizonline.entity.Account;
import hl.quizonline.entity.QuestionPackage;
@Repository
public interface QuestionPackageRepository extends CrudRepository<QuestionPackage, Integer> {
	List<QuestionPackage> findByAccount(Account acc);
}
