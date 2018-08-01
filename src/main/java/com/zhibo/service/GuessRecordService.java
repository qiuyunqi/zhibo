package com.zhibo.service;

import java.util.List;
import java.util.Map;

import com.zhibo.model.GuessRecord;

/**
 * 
 * @description 自动生成 service
 * 
 */
public interface GuessRecordService {
	public GuessRecord get(Long id);

	public void save(GuessRecord entity);

	public void delete(Long id);

	public Integer getCount(Map<String, Object> map);

	public List<GuessRecord> findList(int i, int pageSize, Map<String, Object> map);

}
