package hl.quizonline.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import hl.quizonline.entity.Account;
import hl.quizonline.entity.QuestionPackage;
// TODO: Auto-generated Javadoc

/**
 * The Interface QuestionPackageRepository.
 */
@Repository
public interface QuestionPackageRepository extends JpaRepository<QuestionPackage, Integer> {
	
	/**
	 * Find by account.
	 *
	 * @param acc the acc
	 * @return the list
	 */
	List<QuestionPackage> findByAccount(Account acc);
	
	/**
	 * Find by account.
	 *
	 * @param acc the acc
	 * @param pageable the pageable
	 * @return the page
	 */
	Page<QuestionPackage> findByAccount(Account acc, Pageable pageable);
	
	Page<QuestionPackage> findByNameContains(String key, Pageable pageable);
	
}
