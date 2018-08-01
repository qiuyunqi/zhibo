package com.zhibo.dao.impl;

import org.springframework.stereotype.Repository;

import com.zhibo.dao.LotteryDao;
import com.zhibo.model.LiveLotteryInfo;

@Repository
public class LotteryDaoImpl extends BaseDaoImpl<LiveLotteryInfo, Long> implements LotteryDao {

	public LiveLotteryInfo findByUserIdAndStatus(Long userId, Integer status, int isReceive) {
		String hql = "FROM LiveLotteryInfo WHERE fuUser.id = :userId AND status = :status AND isReceive = :isReceive ORDER BY id DESC";
		return (LiveLotteryInfo) this.getSession().createQuery(hql)//
				.setParameter("userId", userId)//
				.setParameter("status", status)//
				.setParameter("isReceive", isReceive)//
				.setFirstResult(0)//
				.setMaxResults(1)//
				.uniqueResult();
	}

}
