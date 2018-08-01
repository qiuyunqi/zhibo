package com.zhibo.service;

import com.zhibo.model.FuUser;

public interface FuUserService {

	public FuUser get(Long id);

	public void save(FuUser entity);

	public FuUser findUserByWeixinCode(String weixinCode);

	public FuUser findUserByAccount(String accountName);

	public FuUser findFuUserById(Long userId);

	public FuUser findUserByRegInvitationcode(String invitation_code);

	public Integer countInvitationCode(String code);

	public FuUser findLoginByToken(String token);
}
