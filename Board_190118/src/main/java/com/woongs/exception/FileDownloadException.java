package com.woongs.exception;

/**
 * 파일 업로드 Exception
 * @author Woongs
 *
 */
public class FileDownloadException extends RuntimeException {
    public FileDownloadException(String message) {
        super(message);
    }
    
    public FileDownloadException(String message, Throwable cause) {
        super(message, cause);
    }	
}