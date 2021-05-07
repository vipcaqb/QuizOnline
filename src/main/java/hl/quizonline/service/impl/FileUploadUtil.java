package hl.quizonline.service.impl;

import java.io.*;
import java.nio.file.*;
 
import org.springframework.web.multipart.MultipartFile;
 
// TODO: Auto-generated Javadoc
/**
 * The Class FileUploadUtil.
 */
public class FileUploadUtil {
     
    /**
     * Lưu file
     *
     * @param uploadDir Đường dẫn lưu file
     * @param fileName Tên file muốn lưu
     * @param multipartFile the multipart file
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static void saveFile(String uploadDir, String fileName,
            MultipartFile multipartFile) throws IOException {
        Path uploadPath = Paths.get(uploadDir);
         
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
         
        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {        
            throw new IOException("Could not save image file: " + fileName, ioe);
        }      
    }
}