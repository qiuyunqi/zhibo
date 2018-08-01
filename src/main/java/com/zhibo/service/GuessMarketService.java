package com.zhibo.service;

import java.util.List;
import java.util.Map;

import com.zhibo.model.GuessMarket;

/**
 * 
 * @description 自动生成 service
 * 
 */
public interface GuessMarketService {
	public GuessMarket get(Long id);

	public void save(GuessMarket entity);

	public void delete(Long id);

	public GuessMarket findByMap(Map<String, Object> map);

	public Integer getCount(Map<String, Object> map);

	public List<GuessMarket> findList(int i, int pageSize, Map<String, Object> map);

}
