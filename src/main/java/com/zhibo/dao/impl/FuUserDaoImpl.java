package com.zhibo.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.zhibo.dao.FuUserDao;
import com.zhibo.model.FuUser;

@Repository
public class FuUserDaoImpl extends BaseDaoImpl<FuUser, Long> implements FuUserDao {
	public FuUser findUserByWeixinCode(String weixinCode) {
		String hql = " from FuUser where state=1 and weixinCode=?";
		return this.findUniqueByHQL(hql, weixinCode);
	}

	public FuUser findUserByAccount(String accountName) {
		String hql = " from FuUser where state=1 and accountName=? or phone=?";
		return this.findUniqueByHQL(hql, accountName, accountName);
	}

	public FuUser findFuUserById(Long userId) {
		String hql = " from FuUser where id = ? ";
		return this.findUniqueByHQL(hql, userId);
	}

	public FuUser findUserByRegInvitationcode(String invitation_code) {
		String hql = " from FuUser where  invitationCode=? ";
		return this.findUniqueByHQL(hql, invitation_code);
	}

	public Integer countInvitationCode(String code) {
		List<Object> params = new ArrayList<Object>();
		String hql = " from FuUser where state=1 ";
		if (code != null) {
			params.add(code);
			hql = hql + " and invitationCode=? ";
		}
		return this.countQueryResult(hql, params);
	}

	public FuUser findLoginByToken(String token) {
		String hql = " from FuUser where state=1 and loginToken=? ";
		return this.findUniqueByHQL(hql, token);
	}
}
