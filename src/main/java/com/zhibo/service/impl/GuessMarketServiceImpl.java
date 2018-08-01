package com.zhibo.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zhibo.dao.GuessMarketDao;
import com.zhibo.model.GuessMarket;
import com.zhibo.service.GuessMarketService;

/**
 * 
 * @description 自动生成 service
 * 
 */
@Service
public class GuessMarketServiceImpl implements GuessMarketService {
	@Resource
	private GuessMarketDao guessMarketDao;

	// ====================== 基本 C R U D 方法 ===========================
	public GuessMarket get(Long id) {
		return guessMarketDao.get(id);
	}

	public void save(GuessMarket entity) {
		guessMarketDao.save(entity);
	}

	public void delete(Long id) {
		guessMarketDao.delete(id);
	}

	public GuessMarket findByMap(Map<String, Object> map) {
		return guessMarketDao.findByMap(map);
	}

	public Integer getCount(Map<String, Object> map) {
		return guessMarketDao.getCount(map);
	}

	public List<GuessMarket> findList(int i, int pageSize, Map<String, Object> map) {
		return guessMarketDao.findList(i, pageSize, map);
	}

}
