package com.woongs.vo;

/**
 * 페이징 관련 VO
 * @author Woongs
 *
 */
public class PageVO {
	private int totalCount;	// 게시판의 게시물 총 갯수
	private int countList;	// 한 페이지에 출력될 게시물 수
	private int countPage;	// 한 화면에 출력될 페이지 수
	private int totalPage;	// 총 페이지 수 = 게시판의 게시물 총 갯수 / 한 페이지에 출력될 게시물 수
	private int page;		// 현재 페이지
	private int startPage;	// 시작 페이지
	private int endPage;	// 끝 페이지
	
	private String keyword;  // 검색어
	private String keyfield; // 검색대상
	private String ascdecs;
	private String ordeby;
	
	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getKeyfield() {
		return keyfield;
	}

	public void setKeyfield(String keyfield) {
		this.keyfield = keyfield;
	}

	public String getAscdecs() {
		return ascdecs;
	}

	public void setAscdecs(String ascdecs) {
		this.ascdecs = ascdecs;
	}

	public String getOrdeby() {
		return ordeby;
	}

	public void setOrdeby(String ordeby) {
		this.ordeby = ordeby;
	}

	public PageVO() {

	}

	public PageVO(int totalCount, int countList, int countPage, int page) {
		super();
		this.totalCount = totalCount;
		this.countList = countList;
		this.countPage = countPage;
		this.page = page;
	}

	public PageVO(int totalCount, int countList, int countPage, int totalPage, int page, int startPage, int endPage) {
		super();
		this.totalCount = totalCount;
		this.countList = countList;
		this.countPage = countPage;
		this.totalPage = totalPage;
		this.page = page;
		this.startPage = startPage;
		this.endPage = endPage;
	}	

	public PageVO(int totalCount, int countList, int countPage, int totalPage, int page) {
		super();
		this.totalCount = totalCount;
		this.countList = countList;
		this.countPage = countPage;
		this.totalPage = totalPage;
		this.page = page;
	}

	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getCountList() {
		return countList;
	}
	public void setCountList(int countList) {
		this.countList = countList;
	}
	public int getCountPage() {
		return countPage;
	}
	public void setCountPage(int countPage) {
		this.countPage = countPage;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getStartPage() {
		return startPage;
	}
	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}
	public int getEndPage() {
		return endPage;
	}
	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	@Override
	public String toString() {
		return "PageVO [totalCount=" + totalCount + ", countList=" + countList + ", countPage=" + countPage
				+ ", totalPage=" + totalPage + ", page=" + page + ", startPage=" + startPage + ", endPage=" + endPage
				+ ", keyword=" + keyword + ", keyfield=" + keyfield + ", ascdecs=" + ascdecs + ", ordeby=" + ordeby
				+ "]";
	}
}
