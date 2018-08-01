package com.zhibo.dao;

import java.util.List;
import java.util.Map;

import com.zhibo.model.GuessRecord;

public interface GuessRecordDao extends BaseDao<GuessRecord, Long> {
	public Integer getCount(Map<String, Object> map);

	public List<GuessRecord> findList(int i, int pageSize, Map<String, Object> map);

}
