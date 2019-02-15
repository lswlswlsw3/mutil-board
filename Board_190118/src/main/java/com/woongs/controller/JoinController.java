package com.woongs.controller;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.woongs.service.EmailService;
import com.woongs.service.UserService;
import com.woongs.util.AES256Util;
import com.woongs.util.BoardUtil;
import com.woongs.util.RandomCode;
import com.woongs.vo.AuthVO;
import com.woongs.vo.UserVO;

@Controller
public class JoinController {
	
	@Autowired
	EmailService emailService;

	@Autowired
	UserService userService;
	
	@Autowired
	AES256Util aes256Util;
	
	@Autowired
	RandomCode randomCode;
	
	@Autowired
	HttpSession httpSession;
	
	// 1. 로그인 페이지 이동
	@RequestMapping("/")
	public String login(HttpServletRequest request, Model model)
	{
		UserVO userVO = (UserVO) httpSession.getAttribute("loginUser"); // 세션정보 저장
		String errorMsg = request.getParameter("error");
		
		if(userVO != null) { // 만약 세션정보가 있다면
			return "redirect:/board";
		}
		
		model.addAttribute("error", errorMsg);
		
		return "login"; // 세션정보가 없다면 로그인 페이지
	}

	// 2. 로그인 페이지에서 입력한 정보가 올바른지 판단
	@RequestMapping("/login_chk")
	public String login_chk(HttpServletRequest request, Model model) throws NoSuchAlgorithmException, UnsupportedEncodingException, GeneralSecurityException
	{
		String errorMsg = "";													// 에러 메시지를 저장
		String sabun	= BoardUtil.nvl(request.getParameter("sabun"), "");		// 사번
		String password	= BoardUtil.nvl(request.getParameter("password"), "");	// 비밀번호
				
		if(sabun.equals("")) { // 사번을 입력했는지
			errorMsg = "사번을 입력하세요.";
			model.addAttribute("error", errorMsg);
			
			return "login";
		} else { // 사번을 입력했다면, 올바른 형식으로 입력했는지
			String sabunValid = BoardUtil.sabunValid(sabun); // 입력한 사번이 올바른 형식인지 판단

			if(!sabunValid.equals("")) { // 입력한 사번에 에러메시지가 있다면, 재입력을 위해 로그인 화면으로 이동
				errorMsg = sabunValid;
				model.addAttribute("error", errorMsg);
				return "login"; // 재입력을 위해 로그인 화면으로 이동
			}
		}
		
		if(password.equals("")) { // 비밀번호를 입력했는지
			errorMsg = "비밀번호를 입력하세요.";
			model.addAttribute("error", errorMsg);
			return "login";
		} else { // 비밀번호를 입력했다면, 올바른 형식으로 입력했는지
			String passwordValid = BoardUtil.passwordValid(password);
			
			if(!passwordValid.equals("")) { // 입력한 비밀번호에 에러메시지가 있다면, 재입력을 위해 로그인 화면으로 이동
				errorMsg = passwordValid;
				model.addAttribute("error", errorMsg);
				return "login"; // 재입력을 위해 로그인 화면으로 이동
			}
		}
		
		// 입력받은 사번, 비밀번호가 비어있지 않고, 정확 하다면 암호화
		password	= aes256Util.encrypt(password); // 비밀번호 암호화
				
		UserVO userVO = new UserVO();
		userVO.setUs_sabun(sabun);
		userVO.setUs_password(password);
		
		// 회원이 있는지 없는지 확인 조회
		userVO = userService.loginBySabunAndPassword(userVO);

		// 일치하는 정보가 없으면 로그인페이지로 이동
		if(userVO == null) {
			errorMsg = "일치하는 회원정보가 없습니다.";
			model.addAttribute("error", errorMsg);
			return "login";
		}
		
		// 로그인한 정보 세션에 담음
		httpSession.setAttribute("loginUser", userVO);
		
		return "redirect:/board";
	}
	
	// 비밀번호 찾는 페이지 이동
	@RequestMapping("/find")
	public String find() {
		return "find";
	}
	
	// 비밀번호 찾는 과정 
	@RequestMapping("/find_chk")
	public String find_chk(HttpServletRequest request, Model model) throws NoSuchAlgorithmException, UnsupportedEncodingException, GeneralSecurityException {
		String email = BoardUtil.nvl(request.getParameter("email"), ""); // 이메일 주소 받음
		String errMsg = "";
		
		// 이메일을 입력 안했다면,
		if(email.equals("")) {
			errMsg = "이메일을 입력해주세요.";
			model.addAttribute("error", errMsg);
			return "find";
		} else { // 이메일을 입력했는데, 형식에 맞지 않는다면
			errMsg = BoardUtil.emailValid(email); // 이메일 형식 확인
			if(!errMsg.equals("")) {
				model.addAttribute("error", errMsg);
				return "find";
			}
			
			// 이메일이 존재하면 이메일로 사번과 비밀번호를 전송
			UserVO userVO = new UserVO();
			userVO.setUs_email(email);
			
			userVO = userService.checkEmailDuplicate(userVO); // 이메일이 존재하는지 확인
			
			if(userVO != null) {
				// 이메일로 사번과 비밀번호 전송
				userVO.setUs_email(email);
				userVO = userService.findSabunAndPasswordByEmail(userVO);
				
				String sabun = userVO.getUs_sabun();
				String password = aes256Util.decrypt(userVO.getUs_password());
				
				String to = email;
				String subject = "잊으신 사번과 비밀번호 확인하세요.";
				String text = "사번 : "+sabun+", 비밀번호 : "+password;
				
				emailService.sendSimpleMessage(to, subject, text);
			} else {
				errMsg = "존재하지 않는 회원입니다.";
				model.addAttribute("error", errMsg);
				return "find";
			}
		}		
		
		return "login";
	}
	
	// 로그아웃
	@RequestMapping("/logout")
	public String logout() {
		httpSession.invalidate(); // 로그아웃
		
		return "redirect:/";
	}

	// 회원가입 버튼 누를시, 이동할 페이지 설정
	@RequestMapping("/join")
	public String join() {
		return "join_step_1";
	}
	
	// "join_step_1"에서 약관동의 관련 페이지 처리 (현재 아무런 처리가 없음)
	@RequestMapping("/join_step_1")
	public String join_step_1(HttpServletRequest request, Model model)
	{
		String errorMsg = "";
		
		String use_rule		= BoardUtil.nvl(request.getParameter("use_rule_check"), "");			// 이용약관(필수) 체크 확인
		String private_info = BoardUtil.nvl(request.getParameter("private_info_check"), "");	// 개인정보(필수) 체크 확인
		String advertise	= BoardUtil.nvl(request.getParameter("advertise_check"), "");			// 광고(선택) 체크 확인
		
		// 이용약관, 개인정보 중 하나라도 선택이 안됬다면
		if(use_rule.equals("") || private_info.equals("")) {
			errorMsg = "이용약관, 개인정보는 필수 체크항목 입니다.";
			model.addAttribute("error", errorMsg);
			return "join_step_1";
		}
		
		return "join_step_2";
	}

	// 인증번호 이메일 전송
	@RequestMapping("/join_step_2")
	public String join_step_2(HttpServletRequest request, Model model) {
		String email = BoardUtil.nvl(request.getParameter("email"), ""); // 이메일 주소 받음
		String errMsg = "";
		
		// 이메일을 입력 안했다면,
		if(email.equals("")) {
			errMsg = "이메일을 입력해주세요.";
			model.addAttribute("error", errMsg);
			return "join_step_2";
		} else { // 이메일을 입력했는데, 형식에 맞지 않는다면
			errMsg = BoardUtil.emailValid(email); // 이메일 형식 확인
			if(!errMsg.equals("")) {
				model.addAttribute("error", errMsg);
				return "join_step_2";
			}
			
			// 중복된 이메일인지 확인을 위한 parameter
			UserVO userVO = new UserVO();
			userVO.setUs_email(email);
			
			// 캐시 테스틀 위해 반복 삭제요망
			for (int i = 0; i < 10; i++) {
				userVO = userService.checkEmailDuplicate(userVO); // 이메일 중복확인
			}
			
			if(userVO != null) {
				errMsg = "이미 가입된 이메일 주소입니다.";
				model.addAttribute("error", errMsg);
				return "join_step_2";
			}
		}
		
		// 이메일을 정상적으로 입력했다면 렌덤한 6자리 숫자를 이메일로 전송한다.
		String randomCD = randomCode.numberGen(6, 2);
		String to = email; // 이메일 받을 사람
		String title = "회원가입 인증코드 메일 입니다.";
		String content = "입력할 6자리 번호는 "+randomCD+" 입니다.";

		// 인증정보 저장을 위한 파라미터VO 준비
		AuthVO authVO = new AuthVO();
		authVO.setAu_email(email);
		authVO.setAu_auth_info(randomCD);
		
		userService.deleteAuthInfoByEamil(authVO);	// 이메일 전송전 인증테이블 이전 인증번호 삭제
		userService.insertUserAuthInfo(authVO);		// 이메일 전송전 인증테이블에 저장
		
		emailService.sendSimpleMessage(to, title, content); // 이메일 전송
		httpSession.setAttribute("email", email);
				
		return "join_step_3";
	}
	
	// 이메일 인증 페이지 처리
	@RequestMapping("join_step_3")
	public String join_step_3(HttpServletRequest request, Model model) throws NoSuchAlgorithmException, UnsupportedEncodingException, GeneralSecurityException
	{
		String sabun		= BoardUtil.nvl(request.getParameter("sabun"), "");		// 사번
		String name			= BoardUtil.nvl(request.getParameter("name"), "");		// 이름
		String email		= BoardUtil.nvl(request.getParameter("email"), "");		// 이메일
		String password 	= BoardUtil.nvl(request.getParameter("password"), "");	// 암호
		String auth_info	= BoardUtil.nvl(request.getParameter("auth_info"), "");	// 인증번호
		String errorMsg		= "";
		UserVO userVO = new UserVO();

		// 사번 관련 체크 : 1. 사번입력하지 않은 경우 2. 사번형식이 잘못된 경우 3. 사번이 중복되는 경우 
		// 사번을 입력하지 않은 경우
		if(sabun.equals("")) {
			errorMsg = "사번을 입력하세요.";
			model.addAttribute("error", errorMsg);
			return "join_step_3";		// 사번이 입력되지 않았기 때문에, 회원가입 페이지로 다시 이동
		} else {
			// 사번의 형식 확인
			errorMsg = BoardUtil.sabunValid(sabun);
			if(!errorMsg.equals("")) {
				model.addAttribute("error", errorMsg);
				return "join_step_3";	// 사번 형식이 맞지 않기때문에, 회원가입 페이지로 다시 이동
			}

			userVO.setUs_sabun(sabun);							// 사번 중복체크를 위해 parameter 셋팅
			userVO = userService.checkSabunDuplicate(userVO);	// 사번 중복체크를 위해 조회
			
			// 입력한 사번이 조회 된다면 중복
			if(userVO != null) {
				errorMsg = "중복된 사번입니다. 다시확인 하세요.";
				model.addAttribute("error", errorMsg);
				return "join_step_3";	// 중복된 사번이기 때문에, 회원가입 페이지로 다시 이동
			}
		}

		// 이름 관련 체크 : 1. 이름을 입력하지 않은 경우 2. 이름형식이 잘못된 경우
		if(name.equals("")) {
			errorMsg = "이름을 입력하세요.";
			model.addAttribute("error", errorMsg);
			return "join_step_3";	// 이름이 입력되지 않아서, 회원가입 페이지로 다시 이동
		} else {
			errorMsg = BoardUtil.nameValid(name); // 이름 형식 확인
			if(!errorMsg.equals("")) {
				model.addAttribute("error", errorMsg);
				return "join_step_3";	// 이름 형식이 맞지 않기때문에, 회원가입 페이지로 다시 이동
			}
		}
		
		// 이메일 관련 체크 : 1. 이메일을 입력하지 않은 경우 2. 이메일 형식이 잘못된 경우 3. 이메일이 중복된 경우
		if(email.equals("")) {
			errorMsg = "이메일을 입력하세요.";
			model.addAttribute("error", errorMsg);
			return "join_step_3";	// 이메일이 입력되지 않아서, 회원가입 페이지로 다시 이동
		} else {
			errorMsg = BoardUtil.emailValid(email); // 이메일 형식 확인
			if(!errorMsg.equals("")) {
				model.addAttribute("error", errorMsg);
				return "join_step_3";	// 이메일 형식이 맞지 않기때문에, 회원가입 페이지로 다시 이동
			}
			
			// 이메일 중복여부를 위한 parameter 셋팅
			userVO = new UserVO();
			userVO.setUs_email(email);
			userVO = userService.checkEmailDuplicate(userVO);
			
			// 입력한 이메일이 조회 된다면 중복
			if(userVO != null) {
				errorMsg = "중복된 이메일입니다. 다시확인 하세요.";
				model.addAttribute("error", errorMsg);
				return "join_step_3";	// 중복된 이메일이기 때문에, 회원가입 페이지로 다시 이동
			}
		}

		// 비밀번호 관련 체크 : 1. 비밀번호를 입력하지 않은 경우 2. 비밀번호 형식이 잘못된 경우
		if(password.equals("")) {
			errorMsg = "비밀번호를 입력하세요.";
			model.addAttribute("error", errorMsg);
			return "join_step_3";	// 비밀번호가 입력되지 않아서, 회원가입 페이지로 다시 이동
		} else {
			errorMsg = BoardUtil.passwordValid(password); // 비밀번호 형식 확인
			if(!errorMsg.equals("")) {
				model.addAttribute("error", errorMsg);
				return "join_step_3";	// 비밀번호 형식이 맞지 않기때문에, 회원가입 페이지로 다시 이동
			}
			
			password = aes256Util.encrypt(password); // 비밀번호 정상 입력으로 암호화
		}

		// 인증번호 관련 체크 : 1. 인증번호를 입력하지 않은 경우 2. 인증번호가 동일하지 않은 경우
		if(auth_info.equals("")) {
			errorMsg = "인증번호를 입력하세요.";
			model.addAttribute("error", errorMsg);
			return "join_step_3";	// 인증번호가 입력되지 않아서, 회원가입 페이지로 다시 이동
		} else {
			AuthVO authVO = new AuthVO();	// 인증번호 검색을 위한 파라미터 VO
			authVO.setAu_email(email);			// 이메일
			authVO.setAu_auth_info(auth_info);	// 인증번호

			authVO = userService.searchAuthInfoByEmail(authVO); // 인증번호 이메일에 따른
			
			if(!authVO.getAu_auth_info().equals(auth_info)) { // 인증번호가 동일하지 않을때
				errorMsg = "인증번호를 다시 확인하세요.";
				model.addAttribute("error", errorMsg);
				httpSession.invalidate(); // 저장한 이메일 삭제
				return "join_step_3"; // 인증번호 재전송을 위해 페이지 이동
			}
		}
		
		System.out.println("회원가입 인증 성공!");

		// 회원가입을 위한 parameter 셋팅
		userVO = new UserVO();
		userVO.setUs_sabun(sabun);		 // 사번
		userVO.setUs_name(name);		 // 이름
		userVO.setUs_email(email);		 // 이메일
		userVO.setUs_password(password); // 비밀번호
		
		userService.joinUser(userVO); // 회원가입 성공
		// 인증 테이블의 이메일과 인증번호를 삭제해야 되는지 판단이 안됨 (19-01-29)
		
		return "redirect:/";
	}
}
