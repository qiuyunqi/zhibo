package com.zhibo.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zhibo.dao.FuDictionaryDao;
import com.zhibo.model.FuDictionary;
import com.zhibo.service.FuDictionaryService;

/**
 * 
 * @description 自动生成 service
 * 
 */
@Service
public class FuDictionaryServiceImpl implements FuDictionaryService {
	@Resource
	private FuDictionaryDao fuDictionaryDao;

	// ====================== 基本 C R U D 方法 ===========================
	public FuDictionary get(Long id) {
		return fuDictionaryDao.get(id);
	}

	public void save(FuDictionary entity) {
		fuDictionaryDao.save(entity);
	}

	public void delete(Long id) {
		fuDictionaryDao.delete(id);
	}

}
