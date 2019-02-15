package com.woongs.vo;

/**
 * 공통 VO
 * @author Woongs
 */

public class CommonVO {
	private String cm_key;		// 공통키
	private String cm_value;	// 공통값
	
	public String getCm_key() {
		return cm_key;
	}
	public void setCm_key(String cm_key) {
		this.cm_key = cm_key;
	}
	public String getCm_value() {
		return cm_value;
	}
	public void setCm_value(String cm_value) {
		this.cm_value = cm_value;
	}
	
	@Override
	public String toString() {
		return "CommonVO [cm_key=" + cm_key + ", cm_value=" + cm_value + "]";
	}		
}
