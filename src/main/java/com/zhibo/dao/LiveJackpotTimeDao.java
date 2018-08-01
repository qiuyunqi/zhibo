package com.zhibo.dao;

import java.util.Date;
import java.util.List;

import com.zhibo.model.LiveJackpotTime;

public interface LiveJackpotTimeDao extends BaseDao<LiveJackpotTime, Long> {

	/**
	 * 通过时间和删除标志查询对象集合
	 * @param date  时间
	 * @param isDel 0: 不删除 1:删除 
	 * @return
	 */
	List<LiveJackpotTime> findByTime(Date date, int isDel);

	/**
	 * 根据jackpot对象的主键查询
	 * @param jackpotId
	 * @param isDel
	 * @return
	 */
	LiveJackpotTime getByJackpotId(Long jackpotId, int isDel);

}
