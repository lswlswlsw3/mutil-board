package com.woongs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.woongs.service.CommonCodeService;
import com.woongs.vo.CommonVO;


@Controller
public class CacheController {
	@Autowired
	CommonCodeService commonCodeService;
	
	@RequestMapping("/cache_test")
	public String cache_test() {
		
		List<CommonVO> commonResult = commonCodeService.selectCommonCode();
	
		return "login";
	}
}
