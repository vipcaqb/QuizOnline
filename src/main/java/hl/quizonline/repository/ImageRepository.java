package hl.quizonline.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import hl.quizonline.entity.Image;

@Repository
public interface ImageRepository extends CrudRepository<Image, Integer> {

}
