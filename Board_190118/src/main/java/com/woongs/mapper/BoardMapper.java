package com.woongs.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.woongs.vo.BoardVO;
import com.woongs.vo.FileVO;
import com.woongs.vo.PageVO;
import com.woongs.vo.ReplyVO;

@Repository
@Mapper
public interface BoardMapper {
	public int getCountBoardList(PageVO pageVO);	// 게시판 전체 글 갯수
	
	public List<BoardVO> getBoardList(PageVO pageVO); // 게시판 전체 조회
	
	public void writeBoard(BoardVO boardVO); // 게시물 글쓰기

	public BoardVO selectBoardDetailByBonum(BoardVO boardVO); // 게시물 번호에 따른 상세조회
	
	public void updateCountByBonum(BoardVO boardVO); // 게시물 번호에 따른 조회수 증가
	
	public void deleteBoardByBonum(BoardVO boardVO); // 게시물 번호에 따른 삭제
	
	public void updateBoardByBonum(BoardVO boardVO); // 게시물 번호에 의한 편집
	
	public List<ReplyVO> selectReplyByBonum(ReplyVO replyVO); // 게시물 번호에 의한 댓글 조회
	
	public void insertReplyByBonum(ReplyVO replyVO); // 게시물 번호에 의한 댓글 입력
	
	public void deleteReply(ReplyVO replyVO); // 게시물 번호, 댓글 번호에 의한 삭제
	
	public void insertFiles(FileVO fileVO); // 파일 저장

	public FileVO selectFiles(FileVO fileVO); // 파일 조회
}
