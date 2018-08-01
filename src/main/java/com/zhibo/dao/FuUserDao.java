package com.zhibo.dao;

import com.zhibo.model.FuUser;

public interface FuUserDao extends BaseDao<FuUser, Long> {
	public FuUser findUserByWeixinCode(String weixinCode);

	public FuUser findUserByAccount(String accountName);

	public FuUser findFuUserById(Long userId);

	public FuUser findUserByRegInvitationcode(String invitation_code);

	public Integer countInvitationCode(String code);

	public FuUser findLoginByToken(String token);
}
