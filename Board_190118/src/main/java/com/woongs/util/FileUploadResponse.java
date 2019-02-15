package com.woongs.util;

/**
 * 파일업로드 후 응답할 클레스
 * @author Woongs
 *
 */
public class FileUploadResponse {
    private String fileName;		// 파일명
    private String fileDownloadUri;	// 파일 다운 uri
    private String fileType;		// 파일타입
    private long size;				// 파일 사이즈
    
	public FileUploadResponse(String fileName, String fileDownloadUri, String fileType, long size) {
		this.fileName = fileName;
		this.fileDownloadUri = fileDownloadUri;
		this.fileType = fileType;
		this.size = size;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileDownloadUri() {
		return fileDownloadUri;
	}

	public void setFileDownloadUri(String fileDownloadUri) {
		this.fileDownloadUri = fileDownloadUri;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}
    
}
