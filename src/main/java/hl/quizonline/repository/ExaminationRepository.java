package hl.quizonline.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import hl.quizonline.entity.ExamPackage;
import hl.quizonline.entity.Examination;
// TODO: Auto-generated Javadoc

/**
 * The Interface ExaminationRepository.
 */
@Repository
public interface ExaminationRepository extends JpaRepository<Examination, Integer> {
	
	/**
	 * Find by examination ID.
	 *
	 * @param examPackageId the exam package id
	 * @return the list
	 */
	List<Examination> findByExaminationID(int examPackageId);
	
	/**
	 * Find by exam package.
	 *
	 * @param examPackage the exam package
	 * @return the list
	 */
	List<Examination> findByExamPackage(ExamPackage examPackage);
	
	/**
	 * Find by exam package.
	 *
	 * @param examPackage the exam package
	 * @param pageable the pageable
	 * @return the page
	 */
	Page<Examination> findByExamPackage(ExamPackage examPackage,Pageable pageable);
	
	/**
	 * Find by exam package and examination title contains.
	 *
	 * @param examPackage the exam package
	 * @param examinationContent the examination content
	 * @param pageable the pageable
	 * @return the page
	 */
	Page<Examination> findByExamPackageAndExaminationTitleContains(ExamPackage examPackage, String examinationContent, Pageable pageable);
}
