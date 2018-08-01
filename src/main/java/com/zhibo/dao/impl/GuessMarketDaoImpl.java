package com.zhibo.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.zhibo.dao.GuessMarketDao;
import com.zhibo.model.GuessMarket;

/**
 * 
 * @description 自动生成 daoImpl
 * 
 * @author 弘威
 */
@Repository
@SuppressWarnings("all")
public class GuessMarketDaoImpl extends BaseDaoImpl<GuessMarket, Long> implements GuessMarketDao {

	public GuessMarket findByMap(Map<String, Object> map) {
		String hql = "from GuessMarket where 1=1 ";
		if (map.get("type") != null) {
			hql = hql + " and type = " + map.get("type");
		}
		hql = hql + " order by id desc";
		return this.findUniqueByHQL(hql, null);
	}

	public Integer getCount(Map<String, Object> map) {
		String hql = "from GuessMarket where 1=1 ";
		if (map.get("name") != null) {
			hql = hql + " and name like '%" + map.get("name") + "%'";
		}
		if (map.get("type") != null) {
			hql = hql + " and type = " + map.get("type");
		}
		return this.countQueryResult(hql);
	}

	public List<GuessMarket> findList(int i, int pageSize, Map<String, Object> map) {
		String hql = "from GuessMarket where 1=1 ";
		if (map.get("name") != null) {
			hql = hql + " and name like '%" + map.get("name") + "%'";
		}
		if (map.get("type") != null) {
			hql = hql + " and type = " + map.get("type");
		}
		hql = hql + " order by id desc";
		return this.findListByHQL(i, pageSize, hql, null);
	}
}
