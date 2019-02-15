package com.woongs.util;

import java.util.regex.Pattern;

/**
 * 프로젝트 전반 처리관련 util 클레스 static으로써 객체생성 없이 활용한다.
 * @author Woongs
 * @since 19.01.28
 */
public class BoardUtil {
	
	/**
	 * request.getParameter()의 값이 null인 경우 기본값을 리턴하는 함수
	 * @param val request.getParameter()의 값
	 * @param def request.getParameter()의 값이 null인 경우 기본값
	 * @return output request.getParameter()의 값 이거나 기본값
	 */
	public static String nvl(String val, String def)
	{
		String output = (val == null ? "" : val);
		
		if(output.isEmpty() && !def.isEmpty()) output = def;
		
		System.out.println("called nvl! : "+output);		
		return output;
	}
	
	// 페이지값, 게시물번호 등 파라미터를 sn로 받을때
	public static int intNvl(String val, String def)
	{
		String output = (val == null ? "" : val);
		if(output.isEmpty() && !def.isEmpty()) output = def;
		
		String rule = "^[0-9]{7}$"; // 0~9까지 숫자만

        boolean ruleCheck = Pattern.matches(rule, output);
		
        if(!ruleCheck) {
        	System.out.println("0~9까지의 숫자만 입력하세요.");
        	return 0;
        }
        
		return Integer.parseInt(output);
	}
	
	// 사용 여부 판단해서 삭제필요
	public static String nvl(String val, String def, String option)
	{
		String output = nvl(val, def);
		
		//필터
		if(option.equals("number")) {
			output = val.replaceAll("^[0-9]", "");	
		}else if(option.equals("string")) {
			
		}
		System.out.println("called new nvl! "+output);
		
		return output;
	}

	// 이전 페이지
	public static String nextOrPriorPage(String val) {
		
		String output = (val == null ? "" : val);
		
		return output;
	}
	
	/**
	 * 사번이 정확성 판단 함수 (0~9까지 숫자 7자리)
	 * @param sabun 사번
	 * @return msg 사번 정확성 메시지
	 */
	public static String sabunValid(String sabun) {
		// 0~9까지 숫자 7자리
        String regEx = "^[0-9]{7}$";
        String msg = "";
        
        boolean regCheck = false;
        
        regCheck = Pattern.matches(regEx, sabun);
        
		if(!regCheck) { // 사번이 7자리 숫자가 아니라면,
			msg = "사번은 7자리 숫자로 입력하세요.";
		}
        
		return msg;		
	}

	/**
	 * 비밀번호 정확성 판단 함수 (a~z까지 대소문자, 0~9까지 숫자, !@#$%^&* 특수문자 허용, 최소 6자리~최대 20자리)
	 * @param password 비밀번호
	 * @return msg 비밀번호 정확성 메시지 
	 */
	public static String passwordValid(String password) {
		// a-z까지 대소문자, 0~9까지 숫자, !@#$%^&* 특수문자 허용, 최소 6자리~최대 20자리
        String regEx = "^[a-zA-Z0-9!@#$%^&*]{6,20}$";
        String msg = "";        
        
        boolean regCheck = false;
        
        regCheck = Pattern.matches(regEx, password);

        if(!regCheck) {
			msg = "비밀번호는 a~z까지 대소문자, 0~9까지 숫자, !@#$%^&* 특수문자 허용, 최소 6자리~최대 20자리 입니다.";
		}
        
		return msg;		
	}	
	
	/**
	 * 이메일 주소가 정확성 판단 함수 (a~z까지 대소문자, 0~9까지 숫자, ._%+- 특수문자 허용)
	 * @param email 이메일 주소
	 * @return msg 이메일 정확성 메시지
	 */
	public static String emailValid(String email) {
		// a~z까지 대소문자, 0~9까지 숫자, ._%+- 특수문자 허용
        String regEx = "^[a-zA-Z0-9._%+-]+\\@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}+$";
        String msg = "";
        
        boolean regCheck = false;
        
        regCheck = Pattern.matches(regEx, email);

        if(!regCheck) {
			msg = "이메일은 a~z까지 대소문자, 0~9까지 숫자, ._%+- 특수문자 허용 합니다.";
		}
        
		return msg;
	}	
	
	/**
	 * 이름 정확성 판단 함수 (가~힣까지 2자~10자)
	 * @param name
	 * @return name 이름 정확성 메시지
	 */
	public static String nameValid(String name) {
		// 이름은 a~z까지 대소문자, 가~힣까지 2자~10자로 작성해주세요.
        String regEx = "^[a-zA-Z가-힣]{2,10}+$";
        String msg = "";
        
        boolean regCheck = false;
        
        regCheck = Pattern.matches(regEx, name);

        if(!regCheck) {
			msg = "이름은 a~z까지 대소문자, 가~힣까지 2자~10자로 작성해주세요.";
		}
        
		return msg;
	}	
}
