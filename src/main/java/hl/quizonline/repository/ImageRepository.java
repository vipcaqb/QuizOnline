package hl.quizonline.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import hl.quizonline.entity.Image;

@Repository
public interface ImageRepository extends CrudRepository<Image, Integer> {
	Optional<Image> findByUrl(String url);
}
