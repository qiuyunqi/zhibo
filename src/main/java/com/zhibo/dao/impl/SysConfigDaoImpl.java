package com.zhibo.dao.impl;

import org.springframework.stereotype.Repository;

import com.zhibo.dao.SysConfigDao;
import com.zhibo.model.SysConfig;

@Repository
public class SysConfigDaoImpl extends BaseDaoImpl<SysConfig, Long> implements SysConfigDao {
	public SysConfig getByCode(String code) {
		String hql = "from SysConfig where code=?";
		return this.findUniqueByHQL(hql, code);
	}
}
