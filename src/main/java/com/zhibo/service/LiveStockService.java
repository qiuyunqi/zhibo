package com.zhibo.service;

import java.util.Date;
import java.util.List;

import com.zhibo.model.LiveStock;

public interface LiveStockService {
	
	/**
	 * 查询9:00 到现在的数据  现在的最大的时间为下午3:30
	 * @param startTime
	 * @param nowDate
	 * @return
	 */
	List<LiveStock> findByTime(String startTime, Date nowDate);

	/**
	 * 查询最后一条数据
	 * @return
	 */
	LiveStock findLast();

}
