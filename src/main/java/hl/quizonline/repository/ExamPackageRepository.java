package hl.quizonline.repository;

import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import hl.quizonline.entity.Account;
import hl.quizonline.entity.ExamPackage;
import hl.quizonline.entity.QuestionPackage;

// TODO: Auto-generated Javadoc
/**
 * The Interface ExamPackageRepository.
 */
@Repository
public interface ExamPackageRepository extends JpaRepository<ExamPackage, Integer> {
	
	/**
	 * Find by exam package title containing.
	 *
	 * @param key the key
	 * @return the list
	 */
	List<ExamPackage> findByExamPackageTitleContaining(String key);
	
	/**
	 * Find by account.
	 *
	 * @param account the account
	 * @return the list
	 */
	List<ExamPackage> findByAccount(Account account);
	
	/**
	 * Find by account.
	 *
	 * @param account the account
	 * @param pageable the pageable
	 * @return the page
	 */
	Page<ExamPackage> findByAccount(Account account, Pageable pageable);
	
	/**
	 * Find by is exercise exam.
	 *
	 * @param isExercise the is exercise
	 * @return the list
	 */
	List<ExamPackage> findByIsExerciseExam(boolean isExercise);
	
	/**
	 * Lấy danh sách cuộc thi đang diễn ra và đã được public
	 *
	 * @param currentDateTime the current date time
	 * @return the list
	 */
	@Query("SELECT ep from ExamPackage ep WHERE ep.isExerciseExam = false AND ep.startDatetime < ?1 AND ep.endDatetime > ?1 AND isPublic = true")
	List<ExamPackage> findByIsExamAndPresent(Date currentDateTime);
	
	/**
	 * Find by is exam and comming soon.
	 *
	 * @param currentDateTime the current date time
	 * @return the list
	 */
	@Query("SELECT ep from ExamPackage ep WHERE ep.isExerciseExam = false AND ep.startDatetime > ?1 AND isPublic = true")
	List<ExamPackage> findByIsExamAndCommingSoon(Date currentDateTime);
	
	/**
	 * Find exam expired.
	 *
	 * @param currentDateTime the current date time
	 * @return the list
	 */
	@Query("SELECT ep FROM ExamPackage ep WHERE ep.isExerciseExam = false AND ep.endDatetime < ?1 AND isPublic = true")
	List<ExamPackage> findExamExpired(Date currentDateTime);
	
	/**
	 * Tìm page examPackage có chứa title và category ID.
	 *
	 * @param examPackageTitle the exam package title
	 * @param categoryID the category ID
	 * @param pageable the pageable
	 * @return the page
	 */
	@Query("SELECT ep "
			+ "FROM ExamPackage ep "
			+ "INNER JOIN ep.categories epc "
			+ "WHERE ep.examPackageTitle LIKE %?1% "
			+ "AND epc.categoryID = ?2 AND ep.isPublic = true")
	Page<ExamPackage> findByExamPackageTitleContainsAndHasCategoryID(
			String examPackageTitle, 
			int categoryID,
			Pageable pageable);
	
	@Query("SELECT ep "
			+ "FROM ExamPackage ep "
			+ "WHERE ep.examPackageTitle LIKE %?1% AND ep.isPublic = true")
	Page<ExamPackage> findByExamPackageTitleContains(String examPackageTitle, Pageable pageable);
	/**
	 * Find by account fullname contains and category ID.
	 *
	 * @param fullname the fullname
	 * @param categoryID the category ID
	 * @param pageable the pageable
	 * @return the page
	 */
	@Query("SELECT ep "
			+ "FROM ExamPackage ep "
			+ "INNER JOIN ep.categories epc "
			+ "INNER JOIN ep.account acc "
			+ "WHERE acc.fullname LIKE %?1% "
			+ "AND epc.categoryID = ?2 AND ep.isPublic = true")
	Page<ExamPackage> findByAccountFullnameContainsAndCategoryID(String fullname, int categoryID,Pageable pageable);
	
	@Query("SELECT ep "
			+ "FROM ExamPackage ep "
			+ "INNER JOIN ep.categories epc  "
			+ "INNER JOIN ep.account acc "
			+ "WHERE acc.fullname LIKE %?1% AND ep.isPublic = true")
	Page<ExamPackage> findByAccountFullnameContains(String fullname, Pageable pageable);
	/**
	 * Find by account username contains and category ID.
	 *
	 * @param username the username
	 * @param categoryID the category ID
	 * @param pageable the pageable
	 * @return the page
	 */
	@Query("SELECT ep "
			+ "FROM ExamPackage ep "
			+ "INNER JOIN ep.categories epc "
			+ "INNER JOIN ep.account acc "
			+ "WHERE acc.username LIKE %?1% "
			+ "AND epc.categoryID = ?2 AND ep.isPublic = true")
	Page<ExamPackage> findByAccountUsernameContainsAndCategoryID(String username, int categoryID,Pageable pageable);
	
	@Query("SELECT ep "
			+ "FROM ExamPackage ep "
			+ "INNER JOIN ep.account acc "
			+ "WHERE acc.username LIKE %?1% AND ep.isPublic = true")
	Page<ExamPackage> findByAccountUsernameContains(String username, Pageable pageable);

	@Query("SELECT ep "
			+ "FROM ExamPackage ep "
			+ "INNER JOIN ep.categories c "
			+ "WHERE c.categoryID = ?1")
	List<ExamPackage> findByCategoryID(int categoryID);
}
