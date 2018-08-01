package com.zhibo.service;

import java.math.BigDecimal;

import com.zhibo.model.FuUser;
import com.zhibo.model.LiveLotteryInfo;

public interface LotteryService {

	/**
	 * 保存对象
	 * @param info lotteryInfo 对象
	 * @param spendIntegral 抽奖扣除的积分
	 * @param fuUser 
	 */
	public void save(LiveLotteryInfo info, FuUser fuUser, BigDecimal spendIntegral);

	/**
	 * 根据用户和状态查询对象集合信息
	 * @param userId
	 * @param status 0: 非选定用户 1:选定用户
	 * @param isReceive 0: 礼物还没有领取 1:领取过了礼物
	 * @return
	 */
	public LiveLotteryInfo findByUserIdAndStatus(Long userId, Integer status, int isReceive);

	/**
	 * 保存中间型并修改特殊时段奖品是否继续启用
	 * @param info lotteryInfo 对象
	 * @param spendIntegral 抽奖扣除的积分
	 * @param fuUser 
	 * @param flag	谢谢参与 true : 是  false : 否
	 */
	public void saveAndSetTime(LiveLotteryInfo info, FuUser fuUser, BigDecimal spendIntegral, Boolean flag);
}
