package com.zhibo.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zhibo.dao.LiveJackpotDao;
import com.zhibo.model.LiveJackpot;
import com.zhibo.service.LiveJackpotService;

@Service
public class LiveJackpotServiceImpl implements LiveJackpotService {

	@Resource
	private LiveJackpotDao liveJackpotDao;
	
	public List<LiveJackpot> finaAll() {
		return liveJackpotDao.findAll();
	}

	public LiveJackpot getById(long id) {
		return liveJackpotDao.get(id);
	}

	public List<LiveJackpot> findNowPeriods() {
		// 查询出最新一期的期数
		return liveJackpotDao.findNowPeriods();
	}

	public LiveJackpot findThanks() {
		return liveJackpotDao.findThanks();
	}

}
