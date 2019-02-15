package com.woongs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.woongs.mapper.CommonCodeMapper;
import com.woongs.vo.CommonVO;

@Service
public class CommonCodeService {
	@Autowired
	CommonCodeMapper commonCodeMapper;
	
	@Cacheable(value="promotionCache")
	public List<CommonVO> selectCommonCode() {
		System.out.println("cached service called!!");
		return commonCodeMapper.selectCommonCode();
	}
}
