package com.zhibo.service;

import java.util.List;

import com.zhibo.model.LiveJackpot;

public interface LiveJackpotService {

	/**
	 * 查询全部抽奖池信息
	 * @return
	 */
	public List<LiveJackpot> finaAll();

	/**
	 * 根据id查询对象
	 * @param id
	 * @return
	 */
	public LiveJackpot getById(long id);

	/**
	 * 查询当前期数的奖品池
	 * @return
	 */
	public List<LiveJackpot> findNowPeriods();

	/**
	 * 查询当前期数的谢谢参与奖
	 * @return
	 */
	public LiveJackpot findThanks();

}
