package com.zhibo.dao;

import java.util.Date;
import java.util.List;

import com.zhibo.model.LiveStock;

public interface LiveStockDao extends BaseDao<LiveStock, Long>{

	/**
	 * 查询9:00 到现在的数据  现在的最大的时间为下午3:30
	 * @param startDate
	 * @param nowDate
	 * @return
	 */
	List<LiveStock> findByTime(Date startDate, Date nowDate, Date startDate1, Date endDate1);

	/**
	 * 查询最后一条数据
	 * @return
	 */
	LiveStock findLast();

}
