package com.woongs.service;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.woongs.config.FileUploadProperties;
import com.woongs.exception.FileDownloadException;
import com.woongs.exception.FileUploadException;

/**
 * 파일 다운로드 서비스
 * @author Woongs
 *
 */

@Service
public class FileUploadDownloadService {
/*    private final Path fileLocation;
    
    @Autowired
    public FileUploadDownloadService(FileUploadProperties prop) {
        this.fileLocation = Paths.get(prop.getUploadDir())
                .toAbsolutePath().normalize();
        
        try {
            Files.createDirectories(this.fileLocation);
        } catch(Exception e) {
            throw new FileUploadException("파일을 업로드할 디렉토리를 생성하지 못했습니다.", e);
        }
    }

    // 파일 저장
    public String storeFile(MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        
        fileName = fileName.substring(fileName.lastIndexOf("/")+1, fileName.length()); // 파일명만 가져오기
//        fileName = "how.sql";

        >>> fileName : C:/Users/Woongs/Desktop/how.sql
        >>> targetLocation : C:\Users\Woongs\Desktop\how.sql
        

        System.out.println(">>> fileName : "+fileName); // 삭제요망
        
        try {
            // 파일명에 부적합 문자가 있는지 확인한다.
            if(fileName.contains(".."))
                throw new FileUploadException("파일명에 부적합 문자가 포함되어 있습니다. " + fileName);
            
            Path targetLocation = this.fileLocation.resolve(fileName);
            
            
            System.out.println(">>> targetLocation : "+targetLocation.toString()); // 삭제요망
            
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING); // 실질적인 파일 카피
            
            return fileName;
        }catch(Exception e) {
            throw new FileUploadException("["+fileName+"] 파일 업로드에 실패하였습니다. 다시 시도하십시오.",e);
        }
    }
    
    // 파일 로드
    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            
            if(resource.exists()) {
                return resource;
            }else {
                throw new FileDownloadException(fileName + " 파일을 찾을 수 없습니다.");
            }
        }catch(MalformedURLException e) {
            throw new FileDownloadException(fileName + " 파일을 찾을 수 없습니다.", e);
        }
    }*/
}
