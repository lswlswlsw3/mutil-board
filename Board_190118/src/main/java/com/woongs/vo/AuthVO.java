package com.woongs.vo;

/**
 * 인증 관련 VO
 * @author Woongs
 *
 */

public class AuthVO {
	private String au_auth_info;	// 인증정보
	private String au_email;		// 이메일
	
	public String getAu_auth_info() {
		return au_auth_info;
	}
	public void setAu_auth_info(String au_auth_info) {
		this.au_auth_info = au_auth_info;
	}
	public String getAu_email() {
		return au_email;
	}
	public void setAu_email(String au_email) {
		this.au_email = au_email;
	}
	
	@Override
	public String toString() {
		return "AuthVO [au_auth_info=" + au_auth_info + ", au_email=" + au_email + "]";
	}	
}