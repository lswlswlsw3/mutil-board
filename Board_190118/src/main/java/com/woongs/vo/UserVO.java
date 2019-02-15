package com.woongs.vo;

/**
 * User정보 관련 VO
 * @author Woongs
 *
 */
public class UserVO {
	private String us_sabun;	// 사번
	private String us_name;		// 이름
	private String us_password;	// 패스워드
	private String us_email;	// 이메일
	
	public String getUs_sabun() {
		return us_sabun;
	}
	public void setUs_sabun(String us_sabun) {
		this.us_sabun = us_sabun;
	}
	public String getUs_name() {
		return us_name;
	}
	public void setUs_name(String us_name) {
		this.us_name = us_name;
	}
	public String getUs_password() {
		return us_password;
	}
	public void setUs_password(String us_password) {
		this.us_password = us_password;
	}
	public String getUs_email() {
		return us_email;
	}
	public void setUs_email(String us_email) {
		this.us_email = us_email;
	}
	
	@Override
	public String toString() {
		return "UserVO [us_sabun=" + us_sabun + ", us_name=" + us_name + ", us_password=" + us_password + ", us_email="
				+ us_email + "]";
	}
}
