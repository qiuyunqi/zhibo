package com.zhibo.service.impl;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zhibo.dao.FuUserDao;
import com.zhibo.dao.LiveJackpotTimeDao;
import com.zhibo.dao.LotteryDao;
import com.zhibo.model.FuUser;
import com.zhibo.model.LiveJackpotTime;
import com.zhibo.model.LiveLotteryInfo;
import com.zhibo.service.LotteryService;
import com.zhibo.service.QidaScoreService;

@Service
public class LotteryServiceImpl implements LotteryService {
	
	@Resource
	private LotteryDao lotteryDao;
	@Resource
	private QidaScoreService scoreService;
	@Resource
	private FuUserDao fuUserDao;
	@Resource
	private LiveJackpotTimeDao timeDao;

	public void save(LiveLotteryInfo info, FuUser fuUser, BigDecimal spendIntegral) {
		lotteryDao.save(info);
		BigDecimal subtract = fuUser.getQidaIntegral().subtract(spendIntegral);
		fuUser.setQidaIntegral(subtract);
		fuUserDao.save(fuUser);
		scoreService.saveScoreByQd(fuUser, 55, spendIntegral, BigDecimal.ZERO, subtract, 0);
	}

	public LiveLotteryInfo findByUserIdAndStatus(Long userId, Integer status, int isReceive) {
		return lotteryDao.findByUserIdAndStatus(userId, status, isReceive);
	}

	public void saveAndSetTime(LiveLotteryInfo info, FuUser fuUser, BigDecimal spendIntegral, Boolean flag) {
		this.save(info, fuUser, spendIntegral);
		if (!flag) { // 不是谢谢参与
			LiveJackpotTime time = timeDao.getByJackpotId(info.getLiveJackpot().getId(), 0);
			time.setIsDel(1);
			timeDao.save(time);
		}
	}

}
