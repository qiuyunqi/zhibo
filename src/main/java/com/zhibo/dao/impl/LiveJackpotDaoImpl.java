package com.zhibo.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zhibo.dao.LiveJackpotDao;
import com.zhibo.model.LiveJackpot;

@Repository
public class LiveJackpotDaoImpl extends BaseDaoImpl<LiveJackpot, Long> implements LiveJackpotDao {

	@SuppressWarnings("unchecked")
	public List<LiveJackpot> findNowPeriods() {
		String hql = "FROM LiveJackpot WHERE isDel = 0";
		return this.getSession().createQuery(hql).list();
	}

	public LiveJackpot findThanks() {
		String hql = "FROM LiveJackpot WHERE isDel = 0 AND grade = 0";
		return (LiveJackpot) this.getSession().createQuery(hql)//
				.setFirstResult(0)//
				.setMaxResults(1)//
				.uniqueResult();
	}

}
