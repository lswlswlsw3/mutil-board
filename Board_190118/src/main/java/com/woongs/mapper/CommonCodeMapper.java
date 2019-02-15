package com.woongs.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.woongs.vo.CommonVO;

/**
 * 공통코드 매퍼
 * @author Woongs
 *
 */
@Repository
@Mapper
public interface CommonCodeMapper {
	public List<CommonVO> selectCommonCode();
}
