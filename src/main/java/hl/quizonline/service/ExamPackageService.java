
package hl.quizonline.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import hl.quizonline.entity.ExamPackage;
import hl.quizonline.model.LineChartModel;
import javassist.NotFoundException;

// TODO: Auto-generated Javadoc
/**
 * The Interface ExamPackageService.
 */
public interface ExamPackageService {
	
	/**
	 * Gets the list.
	 *
	 * @param username the username
	 * @return the list
	 */
	List<ExamPackage> getList(String username);
	
	/**
	 * Gets the list.
	 *
	 * @return the list
	 */
	List<ExamPackage> getList();
	
	/**
	 * Gets the page by username.
	 *
	 * @param username the username
	 * @param pageNo the page no
	 * @param pageSize the page size
	 * @param sort the sort
	 * @return the page by username
	 */
	Page<ExamPackage> getPageByUsername(String username, int pageNo, int pageSize, Sort sort);
	
	/**
	 * Gets the list present.
	 *
	 * @return the list present
	 */
	List<ExamPackage> getListPresent();
	
	/**
	 * Gets the list exercise.
	 *
	 * @return the list exercise
	 */
	List<ExamPackage> getListExercise();
	
	/**
	 * Gets the list incoming start.
	 *
	 * @return the list incoming
	 */
	List<ExamPackage> getListIsComing();
	
	/**
	 * Gets the list expired.
	 *
	 * @return the list expired
	 */
	List<ExamPackage> getListExpired();
	
	/**
	 * Find with.
	 *
	 * @param key the key
	 * @return the list
	 */
	List<ExamPackage> findWith(String key);
	
	/**
	 * Creates the exampackage.
	 *
	 * @param examPackage the exam package
	 */
	void create(ExamPackage examPackage);
	
	/**
	 * Delete one exampackage.
	 *
	 * @param examPackageID the exam package ID
	 */
	void delete(Integer examPackageID);
	
	/**
	 * Update a exampackage.
	 *
	 * @param examPackage the exam package
	 */
	void update(ExamPackage examPackage);
	
	/**
	 * Gets the exam package.
	 *
	 * @param examPackageID the exam package ID
	 * @return the exam package
	 */
	ExamPackage getExamPackage(int examPackageID);
	
	/**
	 * Checks if this exam package is present.
	 *
	 * @param examPackage the exam package
	 * @return true, if is present
	 */
	boolean isPresent(ExamPackage examPackage);
	
	/**
	 * Search list by title.
	 *
	 * @param examPackageTitlte the exam package titlte
	 * @param categoryID the category ID
	 * @param pageNo the page no
	 * @param pageSize the page size
	 * @param sort the sort
	 * @return the page
	 */
	Page<ExamPackage> searchListByTitle(String examPackageTitlte,int categoryID, int pageNo, int pageSize,Sort sort);
	
	/**
	 * Search list by fullname author, if categoryID <0 then ignore compare categoryID.
	 *
	 * @param fullname the fullname
	 * @param categoryID the category ID
	 * @param pageNo the page no
	 * @param pageSiz the page siz
	 * @param sort the sort
	 * @return the page
	 */
	Page<ExamPackage> searchListByFullnameAuthor(String fullname, int categoryID, int pageNo, int pageSiz,Sort sort);
	
	/**
	 * Search list by username author, if categoryID <0 then ignore compare categoryID.
	 *
	 * @param username the username
	 * @param categoryID the category ID
	 * @param pageNo the page no
	 * @param pageSize the page size
	 * @param sort the sort
	 * @return the page
	 */
	Page<ExamPackage> searchListByUsernameAuthor(String username, int categoryID, int pageNo, int pageSize,Sort sort);

	/**
	 * Search list.
	 *
	 * @param pageNo the page no
	 * @param pageSize the page size
	 * @return the page
	 */
	Page<ExamPackage> searchList(int pageNo, int pageSize);

	/**
	 * Search list have sort.
	 *
	 * @param pageNo the page no
	 * @param pageSize the page size
	 * @param sort the sort
	 * @return the page
	 */
	Page<ExamPackage> searchList(int pageNo, int pageSize, Sort sort);

	/**
	 * Gets the page by username.
	 *
	 * @param username the username
	 * @param pageNo the page no
	 * @param pageSize the page size
	 * @return the page by username
	 */
	Page<ExamPackage> getPageByUsername(String username, int pageNo, int pageSize);
	
	/**
	 * Tăng số lượt thi.
	 *
	 * @param examPackageID the exam package ID
	 * @param doExamTimes the do exam times
	 */
	void inscreaseDoExamTimes(int examPackageID,int doExamTimes);
	
	/**
	 * Xóa tất cả danh mục mà nó tham chiếu tới.
	 *
	 * @param examPackage the exam package
	 */
	void clearCategory(ExamPackage examPackage);
	
	/**
	 * Search all.
	 *
	 * @param pageNo the page no
	 * @param key the key
	 * @return the page
	 */
	Page<ExamPackage> searchAll(int pageNo, String key);
	
	/**
	 * Inscrease view.
	 *
	 * @param examPackageID the exam package ID
	 */
	void inscreaseView(int examPackageID);
	
	/**
	 * Lấy toàn bộ lượt xem của các bộ đề.
	 *
	 * @return the total view
	 */
	long getTotalView();
	
	/**
	 * Lấy tổng số đề thi.
	 *
	 * @return the total exam package
	 */
	long getTotalExamPackage();

	/**
	 * Gets the total do exam time.
	 *
	 * @return the total do exam time
	 */
	long getTotalDoExamTime();
	
	/**
	 * Lấy lượt xem của 1 tháng trong 1 năm cho trước
	 *
	 * @param month the month
	 * @param year the year
	 * @return the views month
	 */
	long getViewsMonth(int month, int year);
	
	/**
	 * Lấy thông tin về line chart
	 *
	 * @param year the year
	 * @return the line chart data
	 */
	LineChartModel getLineChartData(int year);
	
	/**
	 * Lấy năm nhỏ nhất mà đề thi đã tạo
	 *
	 * @return the min year
	 */
	long getMinYear();
}
