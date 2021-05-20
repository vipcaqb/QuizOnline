package hl.quizonline.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FileUploadController {
	
	/**
	 * Gets the image.
	 *
	 * @param photo the photo
	 * @return the image
	 */
	@RequestMapping(value = "getimage/{photo}",method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<ByteArrayResource> getImage(@PathVariable("photo") String photo){
		if(!photo.equals("")||photo !=null) {
			try {
				Path filename = Paths.get("avatar-upload",photo);
				byte[] buffer = Files.readAllBytes(filename);
				
				ByteArrayResource byteArrayResource = new ByteArrayResource(buffer);
				return ResponseEntity.ok().contentLength(buffer.length)
						.contentType(MediaType.parseMediaType("image/png"))
						.body(byteArrayResource);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println("da lay xong anh");
		return ResponseEntity.badRequest().build();
	}
	@RequestMapping(value = "getJson/{fileName}",method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<ByteArrayResource> getJson(@PathVariable("fileName") String fileName){
		
		if(!fileName.equals("")||fileName!=null) {
			Path fullFileName = Paths.get("src//main//resources//static//json",fileName);
			byte[] buffer;
			try {
				buffer = Files.readAllBytes(fullFileName);
				ByteArrayResource byteArrayResource = new ByteArrayResource(buffer);
				return ResponseEntity.ok().contentLength(buffer.length)
						.contentType(MediaType.APPLICATION_JSON_UTF8).body(byteArrayResource);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return ResponseEntity.badRequest().build();
	}
	
	
	@RequestMapping(value = "getQuestionImage/{imageName}",method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<ByteArrayResource> getQuestionImage(@PathVariable("imageName") String photo){
		if(!photo.equals("")||photo !=null) {
			try {
				Path filename = Paths.get("upload-question-image",photo);
				byte[] buffer = Files.readAllBytes(filename);
				
				ByteArrayResource byteArrayResource = new ByteArrayResource(buffer);
				return ResponseEntity.ok().contentLength(buffer.length)
						.contentType(MediaType.parseMediaType("image/png"))
						.body(byteArrayResource);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println("da lay xong anh");
		return ResponseEntity.badRequest().build();
	}
}
