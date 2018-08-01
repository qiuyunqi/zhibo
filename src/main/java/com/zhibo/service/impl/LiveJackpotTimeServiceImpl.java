package com.zhibo.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhibo.dao.LiveJackpotTimeDao;
import com.zhibo.model.LiveJackpotTime;
import com.zhibo.service.LiveJackpotTimeService;

@Service
public class LiveJackpotTimeServiceImpl implements LiveJackpotTimeService {
	
	@Autowired
	private LiveJackpotTimeDao timeDao;

	public List<LiveJackpotTime> findByTime(Date date, int isDel) {
		return timeDao.findByTime(date, isDel);
	}

}
