package hl.quizonline.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hl.quizonline.entity.Image;
import hl.quizonline.repository.ImageRepository;
import hl.quizonline.service.ImageService;

@Service
public class ImageServiceImpl implements ImageService {

	@Autowired
	ImageRepository imageRepository;
	
	@Override
	public Image createImage(Image img) {
		return imageRepository.save(img);
	}

	@Override
	public Image editImage(Image img) {
		Optional<Image> oi = imageRepository.findById(img.getImageID());
		if(oi.isEmpty()) return null;
		return imageRepository.save(img);
	}

	@Override
	public void deleteImage(String imageUrl) {
		Optional<Image> oi = imageRepository.findByUrl(imageUrl);
		if(oi.isEmpty()) return;
		imageRepository.delete(oi.get());;
	}

	@Override
	public Image getImageByID(int imageID) {
		Optional<Image> oi = imageRepository.findById(imageID);
		if(oi.isEmpty()) return null;
		return oi.get();
	}

	@Override
	public void deleteImage(int imageID) {
		imageRepository.deleteById(imageID);
	}
	
}
