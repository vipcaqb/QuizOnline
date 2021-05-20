package hl.quizonline.service;

import hl.quizonline.entity.Image;

// TODO: Auto-generated Javadoc
/**
 * The Interface ImageService.
 */
public interface ImageService {
	
	/**
	 * Gets the image by ID.
	 *
	 * @param imageID the image ID
	 * @return the image by ID
	 */
	Image getImageByID(int imageID);
	
	/**
	 * Creates the image.
	 *
	 * @param img the img
	 * @return the image
	 */
	Image createImage(Image img);
	
	/**
	 * Edits the image.
	 *
	 * @param img the img
	 * @return the image
	 */
	Image editImage(Image img);
	
	/**
	 * Delete image.
	 *
	 * @param imageUrl the image url
	 */
	void deleteImage(String imageUrl);
	
	/**
	 * Delete image.
	 *
	 * @param imageID the image ID
	 */
	void deleteImage(int imageID);
	
}
