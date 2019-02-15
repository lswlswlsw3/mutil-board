package com.woongs.vo;

import java.util.Date;

/**
 * 댓글 관련 VO
 * @author Woongs
 *
 */
public class ReplyVO {
	private int re_bo_num; // 게시물 번호(FK)
	private int re_num; // 댓글 번호
	private String re_contents; // 댓글 내용
	private String re_writer; // 댓글 작성자
	private Date re_date; // 댓글 작성일자
	
	public int getRe_bo_num() {
		return re_bo_num;
	}
	public void setRe_bo_num(int re_bo_num) {
		this.re_bo_num = re_bo_num;
	}
	public int getRe_num() {
		return re_num;
	}
	public void setRe_num(int re_num) {
		this.re_num = re_num;
	}
	public String getRe_contents() {
		return re_contents;
	}
	public void setRe_contents(String re_contents) {
		this.re_contents = re_contents;
	}
	public String getRe_writer() {
		return re_writer;
	}
	public void setRe_writer(String re_writer) {
		this.re_writer = re_writer;
	}
	public Date getRe_date() {
		return re_date;
	}
	public void setRe_date(Date re_date) {
		this.re_date = re_date;
	}
	
	@Override
	public String toString() {
		return "ReplyVO [re_bo_num=" + re_bo_num + ", re_num=" + re_num + ", re_contents=" + re_contents
				+ ", re_writer=" + re_writer + ", re_date=" + re_date + "]";
	}		
}
