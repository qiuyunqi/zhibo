package com.zhibo.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zhibo.dao.SysConfigDao;
import com.zhibo.model.SysConfig;
import com.zhibo.service.SysConfigService;

@Service
public class SysConfigServiceImpl implements SysConfigService {

	@Resource
	private SysConfigDao sysConfigDao;

	public SysConfig getByCode(String code) {
		return sysConfigDao.getByCode(code);
	}

}
