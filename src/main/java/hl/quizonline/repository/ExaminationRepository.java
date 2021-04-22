package hl.quizonline.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import hl.quizonline.entity.Examination;
@Repository
public interface ExaminationRepository extends CrudRepository<Examination, Integer> {
	List<Examination> findByExaminationID(int examPackageId);
}
