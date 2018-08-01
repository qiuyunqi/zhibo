package com.zhibo.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zhibo.dao.SysAdminDao;
import com.zhibo.model.SysAdmin;
import com.zhibo.service.SysAdminService;

/**
 * 
 * @description 自动生成 service
 * 
 */
@Service
public class SysAdminServiceImpl implements SysAdminService {
	@Resource
	private SysAdminDao sysAdminDao;

	// ====================== 基本 C R U D 方法 ===========================
	public SysAdmin get(Long id) {
		return sysAdminDao.get(id);
	}

	public void save(SysAdmin entity) {
		sysAdminDao.save(entity);
	}

	public void delete(Long id) {
		sysAdminDao.delete(id);
	}

}
