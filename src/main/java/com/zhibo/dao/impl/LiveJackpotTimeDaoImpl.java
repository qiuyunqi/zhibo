package com.zhibo.dao.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.zhibo.dao.LiveJackpotTimeDao;
import com.zhibo.model.LiveJackpotTime;

@Repository
public class LiveJackpotTimeDaoImpl extends BaseDaoImpl<LiveJackpotTime, Long> implements LiveJackpotTimeDao {

	@SuppressWarnings("unchecked")
	public List<LiveJackpotTime> findByTime(Date date, int isDel) {
		String hql = "FROM LiveJackpotTime WHERE isDel = :isDel AND startTime < :date AND endTime > :date";
		return this.getSession().createQuery(hql)//
				.setParameter("isDel", isDel)//
				.setParameter("date", date)//
				.list();
	}

	public LiveJackpotTime getByJackpotId(Long jackpotId, int isDel) {
		String hql = "FROM LiveJackpotTime WHERE isDel = :isDel AND livejackpot.id = :jackpotId";
		return (LiveJackpotTime) this.getSession().createQuery(hql)//
				.setParameter("isDel", isDel)//
				.setParameter("jackpotId", jackpotId)//
				.uniqueResult();

				
	}

}
