package com.woongs.service;

import org.springframework.stereotype.Service;

import com.woongs.vo.PageVO;

/**
 * 페이징 관련된 처리
 * @author Woongs
 *
 */
@Service
public class BoardPage {

	// 페이징 처리를 한다.
	public PageVO makePaging(PageVO pageVO) {

		int totalCount	= pageVO.getTotalCount();	// 게시판의 게시물 총 갯수
		int countList	= pageVO.getCountList();	// 한 페이지에 출력될 게시물 수
		int countPage 	= pageVO.getCountPage();	// 한 화면에 출력될 페이지 수
		int page 		= pageVO.getPage();			// 현재 페이지
		int totalPage	= pageVO.getTotalPage();		// 총 페이지 수 = 게시판의 게시물 총 갯수 / 한 페이지에 출력될 게시물 수
		
		int startPage 	= ((page - 1) / countList) * countList + 1;	// 시작페이지 : (5-1 / 10) * 10 + 1; 
		int endPage		= startPage + countPage - 1;	// 끝페이지 : 5 + 10 - 1; 

		if(totalPage < page) { // 현재 페이지가 총 페이지 보다 크다면,
			page = totalPage; // 총 페이지 최대값으로 현재 페이지를 셋팅
		}

		if(endPage > totalPage) { // 마지막 페이지가 총 페이지 보다 크다면,
			endPage = totalPage; // 총 페이지 최대값으로 마지막 페이지를 셋팅
		}
		
		PageVO newPageVO = new PageVO(totalCount, countList, countPage, totalPage, page, startPage, endPage);
		
		System.out.println("newPageVO : "+newPageVO.toString());
		
		return newPageVO;
	}
	
	/**
		if(totalPage < page) { // 현재 페이지가 총 페이지 보다 크다면,
			page = totalPage; // 총 페이지 최대값으로 현재 페이지를 셋팅
		}
		
		if(endPage > totalPage) { // 마지막 페이지가 총 페이지 보다 크다면,
			endPage = totalPage; // 총 페이지 최대값으로 마지막 페이지를 셋팅
		}
		
		if(startPage > 1) { // 첫시작 페이지가 1보다 크다면 2, 3, 4... 즉 시작페이지가 11, 21, 31... 등
			//System.out.println("<a href=\"?page=1\">처음</a>");
			page = 1;
		}
		
		if(page > 1) { // 현 페이지가 1보다 크다면, 즉, 이전으로 이동할 페이지가 존재한다면, page-1
			System.out.println("<a href=\"?page=" + (page - 1)  + "\">이전</a>");
		}
		
		for(int i = startPage; i <= endPage; i++) {
			if(i == page) { // 아래 출력 되는 페이지가 현재 페이지라면
				System.out.print("찐하게 [" + i + "] ");
			}
			
			System.out.print(" [" + i + "] ");
		}

		if(page < totalPage) { // 만약 현 페이지가 총 페이지보다 작다면, 즉. 다음으로 넘길 페이지가 존재 한다면, page+1
			System.out.println("<a href=\"?page=" + (page + 1)  + "\">다음</a>");
		}
		
		if(endPage < totalPage) { // 만약 마지막 페이지가 총페이지보다 작다면, 즉. 마지막으로 넘길 페이지가 존재한다면, 마지막 페이지로 이동
			System.out.print("<a href=\"?page=" + totalPage + "\">끝</a>");
		}

	 */
	
}
