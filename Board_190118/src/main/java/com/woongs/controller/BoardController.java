package com.woongs.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.woongs.service.BoardPage;
import com.woongs.service.BoardService;
import com.woongs.util.BoardUtil;
import com.woongs.vo.BoardVO;
import com.woongs.vo.FileVO;
import com.woongs.vo.PageVO;
import com.woongs.vo.ReplyVO;
import com.woongs.vo.UserVO;

/**
 * DB에 있는 게시글 조회
 * @author Woongs
 *
 */
@Controller
public class BoardController {
	
	@Autowired
	BoardService boardService;
	
	@Autowired
	BoardPage boardPage;

	@Autowired
	HttpSession httpSession;
	
	// 첫화면 게시글 조회 (수정필요)
	@RequestMapping("/board")
	public String search(HttpServletRequest request, Model model) {
		
		int page		= Integer.parseInt(BoardUtil.nvl(request.getParameter("page"), "1"));	// 현재 페이지 파라미터 값
		String keyword	= BoardUtil.nvl(request.getParameter("keyword"), "");					// 검색어 파라미터 값
		String keyfield	= BoardUtil.nvl(request.getParameter("keyfield"), "");					// 검색타입 파라미터 값 (제목 or 내용)
		
		int prior	= Integer.parseInt(BoardUtil.nvl(request.getParameter("prior"), "-1"));	// 이전 페이지 파라미터 값 
		int next	= Integer.parseInt(BoardUtil.nvl(request.getParameter("next"), "-1"));	// 다음 페이지 파라미터 값

		// 검색어 처리
		PageVO searchVo = new PageVO();
		searchVo.setKeyword(keyword);		// 검색어
		searchVo.setKeyfield(keyfield);		// 검색타입 (제목 or 내용)
		searchVo.setCountList(3);			// 한 페이지에 출력될 게시물 수
		searchVo.setPage(page);				// 현재 페이지
				
		// 초기 페이징 처리를 위한 변수 셋팅
		int totalCount	= boardService.getCountBoardList(searchVo);	// 게시판의 게시물 총 갯수
		int countPage 	= 3; 										// 한 화면에 출력될 페이지 수
		int totalPage	= totalCount/searchVo.getCountList();		// 총 페이지 수 = 게시판의 게시물 총 갯수 / 한 페이지에 출력될 게시물 수

		if(totalCount % searchVo.getCountList() > 0) { // 페이지 갯수가 3, 9, 9... 로 나눠지지 않고 나머지 게시물이 있다면,
			totalPage++; // 총 페이지를 1증감
		}
		
		// url에 강압적으로 0 또는 -값을 입력하는 경우
		if(page <= 0) {
			page = 1;
		}
		if(page > totalPage) {
			page = totalPage;
		}
		
		// 키워드값이 있다면, 검색 결과 게시물 수에 따라 표시할 페이지가 달라짐
		if(!keyword.equals("")) {
			int tmpCountPage = totalCount/countPage; // 게시판 게시물 총 갯수/화면에 출력될 페이지 수 5/3
						
			if(tmpCountPage == 0) { // 검색된 게시물 수가 3개 미만인 경우, 화면에 출력될 페이지 수를 1로 초기화
				countPage = 1;
			} else if(tmpCountPage > 3) { // 검색된 게시물 수가 9개 이상인 경우, 화면에 출력될 페이지 수를 3로 초기화
				countPage = 3;
			}

			if(totalCount % countPage > 0) { // 페이지 갯수가 3, 6, 9... 로 나눠지지 않고 나머지 게시물이 있다면,
				countPage++; // 총 페이지를 1증감
			}
		}
		
		// 이전페이지, 다음페이지 처리 로직
		if(prior >= 0) { // 이전 페이지가 1보다 크면
			page = prior - 1; // 이전페이지 = 현페이지 - 1
			if(page <= 0) {	// 페이지가 1보다 작으면
				page = 1;	// 페이지를 1로 초기화
			}
		} else if(next >= 0) { // 다음 페이지가 1보다 크면
			page = next + 1; // 다음페이지 = 현페이지 + 1
			if(page > totalPage) { // 현페이지가 총페이지보다 크다면
				page = totalPage; // 현페이지를 총페이지로 초기화
			}
		} 
		
		searchVo.setPage(page-1); // 페이지 0 요청시, 총페이지보다 큰 페이지 요청시, 이전페이지, 다음페이지 처리 로직에 의해 현페이지 변동값 변경
		
		PageVO pageVO = new PageVO(totalCount, searchVo.getCountList(), countPage, totalPage, page);
				
		List<BoardVO> result = boardService.getBoardList(searchVo); // 게시글 조회 (검색어 포함)

		pageVO = boardPage.makePaging(pageVO); // 페이징 전체 처리
		pageVO.setKeyword(keyword);	// 키워드 셋팅
		pageVO.setKeyfield(keyfield); // 분야 셋팅
	
		model.addAttribute("result", result);	// 게시글 전체 값 model view로 넘김
		model.addAttribute("page", pageVO);		// 페이지 셋팅값 넘김
		
		return "board";
	}

	// 게시물 번호에 따른 상세 게시판
	@RequestMapping("/board_detail/{bo_num}")
	public String boardDetail(@PathVariable int bo_num, Model model) {
		//int bo_num = BoardUtil.intNvl(request.getParameter("bo_num"), ""); // 게시물 번호
/*		int bo_num = Integer.parseInt(request.getParameter("bo_num")); // 게시물 번호		
*/		
		// 게시물 번호에 따른 조회를 위해 parameter 셋팅
		BoardVO boardVO = new BoardVO();
		boardVO.setBo_num(bo_num);
		
		// 게시물 번호에 따른 조회
		boardVO = boardService.selectBoardDetailByBonum(boardVO); // 게시물 번호에 따른 조회
		boardVO.setBo_count(boardVO.getBo_count()+1); // 조회수 증가
		boardService.updateCountByBonum(boardVO); // 조회수 갱신
		
		// board_detail에 보여질 조회값 셋팅
		model.addAttribute("boardVO", boardVO); // 체크
		
		ReplyVO replyVO = new ReplyVO();
		replyVO.setRe_bo_num(bo_num);
		
		List<ReplyVO> resultReply = boardService.selectReplyByBonum(replyVO);
		
		// 달린 댓글이 없었다면
		if(resultReply == null) {
			
		}
		
		// 파일 불러오기
		FileVO fileVO = new FileVO();
		fileVO.setF_bo_num(bo_num);
		fileVO = boardService.selectFiles(fileVO);
		
		System.out.println(">> fileVO : "+fileVO.toString()); // 삭제요망
		
		// 경로를 제외한 파일명만 셋팅해서 첨부파일에서 보여주기 위해
		String fileOriName = fileVO.getF_file_ori_name();
		fileOriName = fileOriName.substring(fileOriName.lastIndexOf("\\")+1, fileOriName.length());
		fileVO.setF_file_ori_name(fileOriName);
		
		model.addAttribute("resultReply", resultReply);
		model.addAttribute("resultFile", fileVO);
		
		return "board_detail";
	}
	
	// 글번호에 따른 삭제할 게시물 처리
	@RequestMapping("/delete")
	public String delete(HttpServletRequest request, Model model) {
		String errorMsg = "";
		// int bo_num = BoardUtil.intNvl(request.getParameter("bo_num"), ""); // 게시물 번호
		int bo_num = Integer.parseInt(request.getParameter("bo_num"));
		
		// 게시물 번호에 따른 조회를 위해 parameter 셋팅
		BoardVO boardVO = new BoardVO();
		boardVO.setBo_num(bo_num);
		
		// 게시물 번호에 따른 조회
		boardVO = boardService.selectBoardDetailByBonum(boardVO); // 게시물 번호에 따른 조회
		
		// 현 사용자와 게시물 작성자가 동일한지 판단
		UserVO loginUser = (UserVO) httpSession.getAttribute("loginUser"); // 로그인 유저정보
		
		// 세션정보가 만료됬다면 재 로그인을 위해 로그인 페이지 이동
		if(loginUser == null) {
			return "/login";
		}
		
		String loginUserSabun = loginUser.getUs_sabun(); // 로그인 사번정보
		String writer = boardVO.getBo_writer(); // 작성자정보

		boolean isWriter = writer.contains(loginUserSabun); // 작성자정보에 로그인 사번정보가 포함되어있는지

		// ******** 에러 메시지 어떻게 뿌려줄것인지 파악해봐야 함.
		if(!isWriter) {
			errorMsg = "작성자가 아니여서 삭제가 불가능 합니다.";
			model.addAttribute("error", errorMsg);
			return "redirect:/board";
		}

		boardService.deleteBoardByBonum(boardVO); // 삭제
		
		return "redirect:/board";
	}
	
	// 전체 게시판에서 수정시 글번호를 가지고 수정 양식으로 이동
	@RequestMapping("/update_form")
	public String updateForm(HttpServletRequest request, Model model) {
		String errorMsg = "";
		// int bo_num = BoardUtil.intNvl(request.getParameter("bo_num"), ""); // 게시물 번호
		int bo_num = Integer.parseInt(request.getParameter("bo_num"));
		
		// 게시물 번호에 따른 조회를 위해 parameter 셋팅
		BoardVO boardVO = new BoardVO();
		boardVO.setBo_num(bo_num);
		
		// 게시물 번호에 따른 조회
		boardVO = boardService.selectBoardDetailByBonum(boardVO); // 게시물 번호에 따른 조회
		
		if(boardVO == null) {
			System.out.println(">>>>>>>>>>> boardVO is null?"); // 삭제요망
			return "/board";
		}
		
		// 현 사용자와 게시물 작성자가 동일한지 판단
		UserVO loginUser = (UserVO) httpSession.getAttribute("loginUser"); // 로그인 유저정보
		
		// 세션정보가 만료됬다면 재 로그인을 위해 로그인 페이지 이동
		if(loginUser == null) {
			return "/login";
		}
		
		String loginUserSabun = loginUser.getUs_sabun(); // 로그인 사번정보
		String writer = boardVO.getBo_writer(); // 작성자정보

		boolean isWriter = writer.contains(loginUserSabun); // 작성자정보에 로그인 사번정보가 포함되어있는지

		// ******** 에러 메시지 어떻게 뿌려줄것인지 파악해봐야 함.
		if(!isWriter) {
			errorMsg = "작성자가 아니여서 편집이 불가능 합니다.";
			model.addAttribute("error", errorMsg);
			return "redirect:/board";
		}
		
		// 파일정보 조회를 위한 파라미터 설정
		FileVO fileVO = new FileVO();
		fileVO.setF_bo_num(bo_num);
		
		fileVO = boardService.selectFiles(fileVO); // 파일정보 조회
		
		model.addAttribute("fileVO", fileVO);
		model.addAttribute("boardVO", boardVO);
	
		return "update_form";
	}
	
	// 글번호에 따른 게시글 수정
	@RequestMapping("/update")
	public String update(HttpServletRequest request, Model model, BoardVO boardVO)
	{
		String errorMsg = "";
		//int bo_num		= BoardUtil.intNvl(request.getParameter("bo_num"), "");		// 글번호
		int bo_num		= Integer.parseInt(request.getParameter("bo_num"));		// 글번호
		
		String title	= BoardUtil.nvl(boardVO.getBo_title(), "");		// 제목
		String contents = BoardUtil.nvl(boardVO.getBo_contents(), "");	// 내용
		Date date 		= Calendar.getInstance().getTime();						// 날짜
		
		// 제목을 입력하지 않았을때
		if(title.equals("")) {
			errorMsg = "제목을 입력하세요.";
			model.addAttribute("error", errorMsg);
			return "update_form";
		}
		
		// 내용을 입력하지 않았을때
		if(contents.equals("")) {
			errorMsg = "내용을 입력하세요.";
			model.addAttribute("error", errorMsg);
			return "update_form";
		}
		
		// 세션에서 로그인한 유저의 이름과 사번을 추출해서 작성자를 셋팅
		UserVO userVO = (UserVO) httpSession.getAttribute("loginUser");
		// 로그인이 되어 있지 않다면, 즉 세션이 없다면 로그인 페이지로 이동
		if(userVO == null) {
			return "/login";
		}
		
		// 업데이트를 위해 parameter 셋팅
		boardVO = new BoardVO();
		boardVO.setBo_num(bo_num);
		boardVO.setBo_title(title);
		boardVO.setBo_contents(contents);
		boardVO.setBo_date(date);

		System.out.println(">>>> 수정할 게시물 정보 : "+boardVO.toString()); // 삭제요망
		
		boardService.updateBoardByBonum(boardVO); // 편집을 위한 갱신
		
		return "redirect:/board";
	}	
	
	// 전체 게시판에서 글쓰기 버튼 누를시
	@RequestMapping("/write_form")
	public String writeForm() {
		return "write_form"; // 작성폼 페이지 이동
	}
	
	// 글작성후 저장 버튼 누를시
	@RequestMapping("/write")
	public String write(HttpServletRequest request, Model model, @RequestPart MultipartFile files) throws IllegalStateException, IOException {
		String errorMsg = ""; // 에러메시지
		String title	= BoardUtil.nvl(request.getParameter("title"), "");		// 제목
		String contents = BoardUtil.nvl(request.getParameter("contents"), "");	// 내용
		Date date 		= Calendar.getInstance().getTime();						// 날짜

		String sourceFileName = files.getOriginalFilename(); // 소스파일명

		// 제목을 입력하지 않았을때
		if(title.equals("")) {
			errorMsg = "제목을 입력하세요.";
			model.addAttribute("error", errorMsg);
			return "write_form";
		}
		
		// 내용을 입력하지 않았을때
		if(contents.equals("")) {
			errorMsg = "내용을 입력하세요.";
			model.addAttribute("error", errorMsg);
			return "write_form";
		}
		
		// 세션에서 로그인한 유저의 이름과 사번을 추출해서 작성자를 셋팅
		UserVO userVO = (UserVO) httpSession.getAttribute("loginUser");
		// 로그인이 되어 있지 않다면, 즉 세션이 없다면 로그인 페이지로 이동
		if(userVO == null) {
			return "/login";
		}
		
		String name		= userVO.getUs_name();	// 이름
		String sabun	= userVO.getUs_sabun();	// 사번
		String writer	= name+"("+sabun+")";	// 작성자 : 이름(사번) 형식

		BoardVO boardVO = new BoardVO(title, writer, date, 0, contents);

		boardService.writeBoard(boardVO); // 게시글 입력
		int f_bo_num = boardVO.getBo_num(); // 게시글 입력한 글번호 가져옴
		
		System.out.println(">> f_bo_num : "+f_bo_num); // 삭제요망
		
		// 파일을 업로드 한 경우에만, 파일 저장로직 실행 함.
		if(!files.getOriginalFilename().equals("")) {
			System.out.println(">> sourceFileName : "+sourceFileName); // 삭제요망
			
			String sourceFileNameExtension = FilenameUtils.getExtension(sourceFileName).toLowerCase(); // 소스파일확장자
			System.out.println(">> sourceFileNameExtension : "+sourceFileNameExtension); // 삭제요망
			
	        File destinationFile; // 목적지파일
	        String destinationFileName; // 목적지파일명
	        String fileUrl =  "C:\\Users\\Woongs\\Desktop\\works\\Board_190118\\src\\main\\resources\\static\\uploads\\"; // test용 URL
	        // String fileUrl = "src/main/resources/static/uploads";
	        // "src"+File.separator+"main";  
	        
	        do { 
	            destinationFileName = RandomStringUtils.randomAlphanumeric(16) + "." + sourceFileNameExtension; // 렌덤적으로 변환해서 파일명 생성
	            destinationFile = new File(fileUrl + destinationFileName); // URL + 렌덤적으로 변환한 파일명
	        } while (destinationFile.exists()); 
	        
	        System.out.println(">>> destinationFileName : "+destinationFileName); // 삭제요망
	        System.out.println(">>> destinationFile : "+destinationFile); // 삭제요망
	        
	        destinationFile.getParentFile().mkdirs(); // 폴더생성
	        files.transferTo(destinationFile); // 파일 이동
	        
	        FileVO fileVO = new FileVO();
	        	        
	        fileVO.setF_bo_num(f_bo_num);
	        fileVO.setF_file_name(destinationFileName);
	        fileVO.setF_file_ori_name(sourceFileName);
	        fileVO.setF_file_url(fileUrl);
	        
	        boardService.insertFiles(fileVO);
		}
		
		return "redirect:/board";
	}
	
	// 댓글달기 버튼 누를시
	@RequestMapping("/write_reply")
	public String write_reply(HttpServletRequest request, Model model) {
		String errorMsg = ""; // 에러메시지
		
		int bo_num		= Integer.parseInt(request.getParameter("bo_num"));		// 글번호
		String contents = BoardUtil.nvl(request.getParameter("contents"), "");	// 내용
		Date date 		= Calendar.getInstance().getTime();						// 날짜
		
		System.out.println(">>>>>>>> bo_num "+bo_num);
		System.out.println(">>>>>>>> contents "+contents);
		
		// 내용을 입력하지 않았을때
		if(contents.equals("")) {
			errorMsg = "내용을 입력하세요.";
			model.addAttribute("error", errorMsg);
			// return "redirect:/board_detail?bo_num="+bo_num;
			return "redirect:/board_detail/"+bo_num;
		}
		
		// 세션에서 로그인한 유저의 이름과 사번을 추출해서 작성자를 셋팅
		UserVO userVO = (UserVO) httpSession.getAttribute("loginUser");
		// 로그인이 되어 있지 않다면, 즉 세션이 없다면 로그인 페이지로 이동
		if(userVO == null) {
			return "/login";
		}
		
		String name		= userVO.getUs_name();	// 이름
		String sabun	= userVO.getUs_sabun();	// 사번
		String writer	= name+"("+sabun+")";	// 작성자 : 이름(사번) 형식

		ReplyVO replyVO = new ReplyVO();
		replyVO.setRe_bo_num(bo_num);
		replyVO.setRe_contents(contents);
		replyVO.setRe_date(date);
		replyVO.setRe_writer(writer);
	
		boardService.insertReplyByBonum(replyVO);
		
		//return "redirect:/board_detail?bo_num="+bo_num;
		return "redirect:/board_detail/"+bo_num;
	}
	
	// 글작성후 저장 버튼 누를시
	@RequestMapping("/delete_reply")
	public String delete_reply(HttpServletRequest request, Model model) {
		String errorMsg = "";
		
		int bo_num = Integer.parseInt(request.getParameter("re_bo_num"));		// 글번호
		int re_num = Integer.parseInt(request.getParameter("re_num")); 			// 댓글번호
		String re_writer = BoardUtil.nvl(request.getParameter("re_writer"), "");// 댓글 작성자
		
		// 현 사용자와 게시물 작성자가 동일한지 판단
		UserVO loginUser = (UserVO) httpSession.getAttribute("loginUser"); // 로그인 유저정보
		
		// 세션정보가 만료됬다면 재 로그인을 위해 로그인 페이지 이동
		if(loginUser == null) {
			return "/login";
		}
		
		String loginUserSabun = loginUser.getUs_sabun(); // 로그인 사번정보

		boolean isWriter = re_writer.contains(loginUserSabun); // 작성자정보에 로그인 사번정보가 포함되어있는지

		if(!isWriter) {
			errorMsg = "작성자가 아니여서 삭제가 불가능 합니다.";
			model.addAttribute("error", errorMsg);
			// return "redirect:/board_detail?bo_num="+bo_num;
			return "redirect:/board_detail/"+bo_num;
		}		
		
		ReplyVO replyVO = new ReplyVO();
		replyVO.setRe_bo_num(bo_num);
		replyVO.setRe_num(re_num);
		boardService.deleteReply(replyVO);
		
		return "redirect:/board_detail/"+bo_num;
	}
	
	// 상세게시판에서 글번호에 따른 첨부파일 다운로드
	@RequestMapping("/download_file/{f_bo_num}")
    public ResponseEntity<Resource> fileDown(@PathVariable int f_bo_num) throws Exception {
		// 파일 조회를 위한 파라미터 설정
		FileVO fileVO = new FileVO();
		fileVO.setF_bo_num(f_bo_num);
		
		// fileVO 조회 후, 저장
		fileVO = boardService.selectFiles(fileVO);
		
		String fileOriName = fileVO.getF_file_ori_name();	// 변환전 파일명(Path포함)
		String fileName = fileVO.getF_file_name();			// 변환된 파일명

		fileOriName = fileOriName.substring(fileOriName.lastIndexOf("\\")+1, fileOriName.length()); // 저장한 실제 파일명으로 전환을 위해
		
		// 불러올 파일이 있는 폴더 path 설정
		Path rootLocation = Paths.get("src/main/resources/static/uploads");
		
		try {
			Path file_ = rootLocation.resolve(fileName);
			Resource resource = new UrlResource(file_.toUri());
			Resource file = resource;
			
			return ResponseEntity.ok()
//					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileOriName + "\"")					
					.body(file);	
        } catch (MalformedURLException e) {
        	throw new RuntimeException("Error! -> message = " + e.getMessage());
        }
    }
	
/*	private final Path rootLocation = Paths.get("filestorage");*/
	
/*	@Override
    public Resource loadFile(String filename) {
        try {
            Path file = rootLocation.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if(resource.exists() || resource.isReadable()) {
                return resource;
            }else{
            	throw new RuntimeException("FAIL!");
            }
        } catch (MalformedURLException e) {
        	throw new RuntimeException("Error! -> message = " + e.getMessage());
        }
    }*/
	
	
/*    
     * Download Files
     
	@RequestMapping("/files/{filename}")
	public ResponseEntity<Resource> downloadFile(@PathVariable String filename) {
		try {
			Path file_ = rootLocation.resolve(filename);
			Resource resource = new UrlResource(file_.toUri());
			
			Resource file = resource;
			
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
					.body(file);	
        } catch (MalformedURLException e) {
        	throw new RuntimeException("Error! -> message = " + e.getMessage());
        }
	}*/
	
}