package com.woongs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.woongs.mapper.UserMapper;
import com.woongs.vo.AuthVO;
import com.woongs.vo.UserVO;

/*
 * 유저관련 서비스
 */
@Service
public class UserService {
	@Autowired
	UserMapper userMapper;
	
	// 로그인 사번과 패스워드에 의해서
	public UserVO loginBySabunAndPassword(UserVO userVO) {
		return userMapper.loginBySabunAndPassword(userVO);
	}
	
	// 생성한 인증번호 저장
	public void insertUserAuthInfo(AuthVO authVO) {
		userMapper.insertUserAuthInfo(authVO);
	}
	
	// 인증번호 조회 사번과 이메일에 의해
	public AuthVO searchAuthInfoByEmail(AuthVO authVO) {
		return userMapper.searchAuthInfoByEmail(authVO);
	}
	
	// 이전 인증번호 삭제 이메일에 의해
	public void deleteAuthInfoByEamil(AuthVO authVO) {
		userMapper.deleteAuthInfoByEamil(authVO);
	}
	
	// 사번이 중복인지 확인
	public UserVO checkSabunDuplicate(UserVO userVO) {
		return userMapper.checkSabunDuplicate(userVO);
	}

	// 이메일이 중복인지 확인
	public UserVO checkEmailDuplicate(UserVO userVO) {
		System.out.println("이메일 중복체크!"); // 삭제요망
		return userMapper.checkEmailDuplicate(userVO);
	}
	
	// 회원가입 정보 입력
	public void joinUser(UserVO userVO) {
		userMapper.joinUser(userVO);
	}
	
	// 사번과 비밀번호 찾기 이메일을 이용
	public UserVO findSabunAndPasswordByEmail(UserVO userVO) {
		return userMapper.findSabunAndPasswordByEmail(userVO);
	}
}
