package com.woongs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.woongs.mapper.BoardMapper;
import com.woongs.vo.BoardVO;
import com.woongs.vo.FileVO;
import com.woongs.vo.PageVO;
import com.woongs.vo.ReplyVO;

@Service
public class BoardService {
	@Autowired
	BoardMapper boardMapper;
	
	// 게시물 전체 갯수
	public int getCountBoardList(PageVO pageVO) {
		return boardMapper.getCountBoardList(pageVO);
	}
	
	// 게시판 전체 조회
	public List<BoardVO> getBoardList(PageVO pageVO) {
		return boardMapper.getBoardList(pageVO);
	}
	
	// 게시판 글쓰기
	public void writeBoard(BoardVO boardVO) {
		boardMapper.writeBoard(boardVO);
	}
	
	// 게시물 번호에 따른 상세조회
	public BoardVO selectBoardDetailByBonum(BoardVO boardVO) {
		return boardMapper.selectBoardDetailByBonum(boardVO);
	}
	
	// 게시물 번호에 따른 조회수 증가
	public void updateCountByBonum(BoardVO boardVO) {
		boardMapper.updateCountByBonum(boardVO);
	}
	
	// 게시물 번호에 따른 삭제
	public void deleteBoardByBonum(BoardVO boardVO) {
		boardMapper.deleteBoardByBonum(boardVO);
	}
	
	// 게시물 번호에 의한 편집
	public void updateBoardByBonum(BoardVO boardVO) {
		boardMapper.updateBoardByBonum(boardVO);
	}
	
	// 게시물 번호에 의한 대글 조회
	public List<ReplyVO> selectReplyByBonum(ReplyVO replyVO) {
		return boardMapper.selectReplyByBonum(replyVO);
	}
	
	// 게시물 번호에 의한 댓글 입력
	public void insertReplyByBonum(ReplyVO replyVO) {
		boardMapper.insertReplyByBonum(replyVO);
	}
	
	// 게시물 번호, 댓글 번호에 의한 삭제
	public void deleteReply(ReplyVO replyVO) {
		boardMapper.deleteReply(replyVO);
	}
	
	// 파일 저장
	public void insertFiles(FileVO fileVO) {
		boardMapper.insertFiles(fileVO);
	}
	
	// 파일 조회
	public FileVO selectFiles(FileVO fileVO) {
		return boardMapper.selectFiles(fileVO);
	}
}
