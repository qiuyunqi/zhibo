package com.zhibo.dao;

import java.util.List;
import java.util.Map;

import com.zhibo.model.QidaScore;

public interface QidaScoreDao extends BaseDao<QidaScore, Long> {

	public List<QidaScore> findList(int i, int pageSize, Map<String, Object> map);

	public Integer getCount(Map<String, Object> map);

	public void setHQL(StringBuilder hqlB, List<Object> params, Map<String, Object> map);
}
