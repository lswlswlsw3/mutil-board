package com.woongs.vo;

import java.util.Date;

/**
 * 게시판 VO
 * @author Woongs
 */

public class BoardVO {
    private int bo_num;			// 번호
    private String bo_title;	// 제목
    private String bo_writer;	// 작성자
    private Date bo_date;		// 작성일자
    private int bo_count;		// 조회수
    private String bo_contents;	// 내용
    
    public BoardVO() {
    	
    }
    
    public BoardVO(String bo_title, String bo_writer, Date bo_date, int bo_count, String bo_contents) {
		this.bo_title = bo_title;
		this.bo_writer = bo_writer;
		this.bo_date = bo_date;
		this.bo_count = bo_count;
		this.bo_contents = bo_contents;
	}
    
	public int getBo_num() {
		return bo_num;
	}
	public void setBo_num(int bo_num) {
		this.bo_num = bo_num;
	}
	public String getBo_title() {
		return bo_title;
	}
	public void setBo_title(String bo_title) {
		this.bo_title = bo_title;
	}
	public String getBo_writer() {
		return bo_writer;
	}
	public void setBo_writer(String bo_writer) {
		this.bo_writer = bo_writer;
	}
	public Date getBo_date() {
		return bo_date;
	}
	public void setBo_date(Date bo_date) {
		this.bo_date = bo_date;
	}
	public int getBo_count() {
		return bo_count;
	}
	public void setBo_count(int bo_count) {
		this.bo_count = bo_count;
	}
	public String getBo_contents() {
		return bo_contents;
	}
	public void setBo_contents(String bo_contents) {
		this.bo_contents = bo_contents;
	}
	
	@Override
	public String toString() {
		return "BoardVO [bo_num=" + bo_num + ", bo_title=" + bo_title + ", bo_writer=" + bo_writer + ", bo_date="
				+ bo_date + ", bo_count=" + bo_count + ", bo_contents=" + bo_contents + "]";
	}
}