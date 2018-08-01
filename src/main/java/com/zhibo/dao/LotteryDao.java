package com.zhibo.dao;

import com.zhibo.model.LiveLotteryInfo;

public interface LotteryDao extends BaseDao<LiveLotteryInfo, Long>{

	/**
	 * 根据用户和状态查询对象集合信息
	 * @param userId
	 * @param status 0: 非选定用户 1:选定用户
	 * @param isReceive 0: 礼物还没有领取 1:领取过了礼物
	 * @return
	 */
	public LiveLotteryInfo findByUserIdAndStatus(Long userId, Integer status, int isReceive);

}
