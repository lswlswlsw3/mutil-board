package com.woongs.vo;

public class FileVO {
	
	private int f_num;				// 파일번호
	private int f_bo_num;			// 게시판번호
	private String f_file_name;		// 저장할 파일명
	private String f_file_ori_name;	// 실제 파일명
	private String f_file_url;		// 파일 URL
	
	public int getF_num() {
		return f_num;
	}
	public void setF_num(int f_num) {
		this.f_num = f_num;
	}
	public int getF_bo_num() {
		return f_bo_num;
	}
	public void setF_bo_num(int f_bo_num) {
		this.f_bo_num = f_bo_num;
	}
	public String getF_file_name() {
		return f_file_name;
	}
	public void setF_file_name(String f_file_name) {
		this.f_file_name = f_file_name;
	}
	public String getF_file_ori_name() {
		return f_file_ori_name;
	}
	public void setF_file_ori_name(String f_file_ori_name) {
		this.f_file_ori_name = f_file_ori_name;
	}
	public String getF_file_url() {
		return f_file_url;
	}
	public void setF_file_url(String f_file_url) {
		this.f_file_url = f_file_url;
	}
	
	@Override
	public String toString() {
		return "FileVO [f_num=" + f_num + ", f_bo_num=" + f_bo_num + ", f_file_name=" + f_file_name
				+ ", f_file_ori_name=" + f_file_ori_name + ", f_file_url=" + f_file_url + "]";
	}	
}
