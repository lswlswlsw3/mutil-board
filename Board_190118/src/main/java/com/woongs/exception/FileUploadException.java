package com.woongs.exception;

/**
 * 파일 업로드 Exception
 * @author Woongs
 *
 */
public class FileUploadException extends RuntimeException {
    public FileUploadException(String message) {
        super(message);
    }
    
    public FileUploadException(String message, Throwable cause) {
        super(message, cause);
    }	
}