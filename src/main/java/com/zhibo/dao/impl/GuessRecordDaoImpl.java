package com.zhibo.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.zhibo.dao.GuessRecordDao;
import com.zhibo.model.GuessRecord;

/**
 * 
 * @description 自动生成 daoImpl
 * 
 * @author 弘威
 */
@Repository
public class GuessRecordDaoImpl extends BaseDaoImpl<GuessRecord, Long> implements GuessRecordDao {
	public Integer getCount(Map<String, Object> map) {
		String hql = "from GuessRecord where 1=1 ";
		if (map.get("userId") != null) {
			hql += " AND fuUser.id = " + map.get("userId");
		}
		if (map.get("userName") != null) {
			hql = hql + " and fuUser.userName like '%" + map.get("userName") + "%'";
		}
		if (map.get("marketId") != null) {
			hql = hql + " and guessMarket.id = " + map.get("marketId");
		}
		if (map.get("marketName") != null) {
			hql = hql + " and guessMarket.name like '%" + map.get("marketName") + "%'";
		}
		if (map.get("guessType") != null) {
			hql = hql + " and guessType = " + map.get("guessType");
		}
		if (map.get("orderIncome") != null) {
			hql = hql + " and orderIncome > 0 ";
		}
		return this.countQueryResult(hql);
	}

	public List<GuessRecord> findList(int i, int pageSize, Map<String, Object> map) {
		String hql = "from GuessRecord where 1=1 ";
		if (map.get("userId") != null) {
			hql += " AND fuUser.id = " + map.get("userId");
		}
		if (map.get("userName") != null) {
			hql = hql + " and fuUser.userName like '%" + map.get("userName") + "%'";
		}
		if (map.get("marketId") != null) {
			hql = hql + " and guessMarket.id = " + map.get("marketId");
		}
		if (map.get("marketName") != null) {
			hql = hql + " and guessMarket.name like '%" + map.get("marketName") + "%'";
		}
		if (map.get("guessType") != null) {
			hql = hql + " and guessType = " + map.get("guessType");
		}
		if (map.get("orderIncome") != null) {
			hql = hql + " and orderIncome > 0 ";
		}
		hql = hql + " order by id desc";
		return this.findListByHQL(i, pageSize, hql, null);
	}

}
