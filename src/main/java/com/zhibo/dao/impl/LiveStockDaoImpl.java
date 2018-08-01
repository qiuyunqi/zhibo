package com.zhibo.dao.impl;


import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.zhibo.dao.LiveStockDao;
import com.zhibo.model.LiveStock;

@Repository
public class LiveStockDaoImpl extends BaseDaoImpl<LiveStock, Long> implements LiveStockDao {

	@SuppressWarnings("unchecked")
	public List<LiveStock> findByTime(Date startDate, Date nowDate, Date startDate1, Date endDate1) {
		
	String hql = "FROM LiveStock  WHERE (tradeDate > :startDate AND tradeDate <= :endDate1) OR (tradeDate > :startDate1 AND tradeDate < :nowDate)"
				+ " GROUP BY DATE_FORMAT(tradeDate,'%Y:%m:%d %H:%i') ORDER BY id";
	return this.getSession().createQuery(hql)//
				.setParameter("startDate", startDate)//
				.setParameter("nowDate", nowDate)//
				.setParameter("startDate1", startDate1)//
				.setParameter("endDate1", endDate1)
				.list();
	}

	public LiveStock findLast() {
		String hql = "FROM LiveStock ORDER BY id DESC";
		return (LiveStock) this.getSession().createQuery(hql)//
				.setFirstResult(0)//
				.setMaxResults(1)//
				.uniqueResult();
	}

}
