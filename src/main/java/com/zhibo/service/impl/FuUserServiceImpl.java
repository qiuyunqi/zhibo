package com.zhibo.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zhibo.dao.FuUserDao;
import com.zhibo.model.FuUser;
import com.zhibo.service.FuUserService;

@Service
public class FuUserServiceImpl implements FuUserService {
	@Resource
	private FuUserDao fuUserDao;

	public FuUser get(Long id) {
		return fuUserDao.get(id);
	}

	public void save(FuUser entity) {
		fuUserDao.save(entity);
	}

	public FuUser findUserByWeixinCode(String weixinCode) {
		return fuUserDao.findUserByWeixinCode(weixinCode);
	}

	public FuUser findUserByAccount(String accountName) {
		return fuUserDao.findUserByAccount(accountName);
	}

	public FuUser findFuUserById(Long userId) {
		return fuUserDao.findFuUserById(userId);
	}

	public FuUser findUserByRegInvitationcode(String invitation_code) {
		return fuUserDao.findUserByRegInvitationcode(invitation_code);
	}

	public Integer countInvitationCode(String code) {
		return fuUserDao.countInvitationCode(code);
	}

	public FuUser findLoginByToken(String token) {
		return fuUserDao.findLoginByToken(token);
	}

}
