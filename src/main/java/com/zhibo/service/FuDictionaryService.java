package com.zhibo.service;

import com.zhibo.model.FuDictionary;

/**
 * 
 * @description 自动生成 service
 * 
 */
public interface FuDictionaryService {
	public FuDictionary get(Long id);

	public void save(FuDictionary entity);

	public void delete(Long id);

}
