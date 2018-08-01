package com.zhibo.dao;

import java.util.List;
import java.util.Map;

import com.zhibo.model.GuessMarket;

public interface GuessMarketDao extends BaseDao<GuessMarket, Long> {
	public GuessMarket findByMap(Map<String, Object> map);

	public Integer getCount(Map<String, Object> map);

	public List<GuessMarket> findList(int i, int pageSize, Map<String, Object> map);
}
