package com.zhibo.dao;

import com.zhibo.model.SysConfig;

public interface SysConfigDao extends BaseDao<SysConfig, Long> {
	public SysConfig getByCode(String code);
}
