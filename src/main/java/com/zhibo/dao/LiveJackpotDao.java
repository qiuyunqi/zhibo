package com.zhibo.dao;

import java.util.List;

import com.zhibo.model.LiveJackpot;

public interface LiveJackpotDao extends BaseDao<LiveJackpot, Long> {

	/**
	 * 查询最新一期的数据
	 * @return
	 */
	List<LiveJackpot> findNowPeriods();

	/**
	 * 查询当前期数的参与奖
	 * @return
	 */
	LiveJackpot findThanks();

}
