package com.zhibo.service;

import com.zhibo.model.SysAdmin;

/**
 * 
 * @description 自动生成 service
 * 
 */
public interface SysAdminService {
	public SysAdmin get(Long id);

	public void save(SysAdmin entity);

	public void delete(Long id);

}
