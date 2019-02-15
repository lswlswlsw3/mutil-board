package com.woongs.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.woongs.vo.AuthVO;
import com.woongs.vo.UserVO;

/*
 * 유저관련 메퍼
 */
@Repository
@Mapper
public interface UserMapper {
	public UserVO loginBySabunAndPassword(UserVO userVO); // 로그인 사번과 암호에 의해서
	
	public void insertUserAuthInfo(AuthVO authVO); // 생성한 인증번호 저장
	
	public AuthVO searchAuthInfoByEmail(AuthVO authVO); // 인증번호 조회 사번과 이메일에 의해서
	
	public void deleteAuthInfoByEamil(AuthVO authVO); // 이전 인증정보 삭제
	
	public UserVO checkSabunDuplicate(UserVO userVO); // 사번이 중복인지 확인
	
	public UserVO checkEmailDuplicate(UserVO userVO); // 이메일이 중복인지 확인
	
	public UserVO findSabunAndPasswordByEmail(UserVO userVO); // 사번과 비밀번호 찾기 이메일을 이용
	
	public void joinUser(UserVO userVO); // 회원가입 정보 입력
}
