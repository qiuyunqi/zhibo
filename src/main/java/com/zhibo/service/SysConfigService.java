package com.zhibo.service;

import com.zhibo.model.SysConfig;

public interface SysConfigService {

	/**
	 * 根据id查询对象
	 * 
	 * @param id
	 * @return
	 */
	public SysConfig getByCode(String code);

}
