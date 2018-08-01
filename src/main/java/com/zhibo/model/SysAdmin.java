package com.zhibo.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "qd_sys_admin")
public class SysAdmin implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4006285691912358034L;

	@Id
	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@Column(name = "account")
	private String account;

	@Column(name = "password")
	private String password;

	@Column(name = "type")
	private Integer type;

	@Column(name = "name")
	private String name;

	@Column(name = "job_desc")
	private String jobDesc;

	@Column(name = "login_token")
	private String loginToken;

	@Column(name = "state")
	private Integer state;

	@Column(name = "is_auto")
	private Boolean isAuto;

	@Column(name = "create_time")
	private Date createTime;

	@Column(name = "update_login_time")
	private Date updateLoginTime;

	@Column(name = "forbid_login_time")
	private Date forbidLoginTime;

	@Column(name = "login_error_times")
	private Integer loginErrorTimes;

	@Column(name = "login_ip")
	private String loginIp;

	@Column(name = "email")
	private String email;

	@Column(name = "phone")
	private String phone;

	public SysAdmin() {
		super();
	}

	public SysAdmin(Long id, String account, String password, Integer type, String name, String jobDesc, String loginToken, Integer state, Boolean isAuto, Date createTime, Date updateLoginTime,
			Date forbidLoginTime, Integer loginErrorTimes, String loginIp, String email, String phone) {
		super();
		this.id = id;
		this.account = account;
		this.password = password;
		this.type = type;
		this.name = name;
		this.jobDesc = jobDesc;
		this.loginToken = loginToken;
		this.state = state;
		this.isAuto = isAuto;
		this.createTime = createTime;
		this.updateLoginTime = updateLoginTime;
		this.forbidLoginTime = forbidLoginTime;
		this.loginErrorTimes = loginErrorTimes;
		this.loginIp = loginIp;
		this.email = email;
		this.phone = phone;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getJobDesc() {
		return jobDesc;
	}

	public void setJobDesc(String jobDesc) {
		this.jobDesc = jobDesc;
	}

	public String getLoginToken() {
		return loginToken;
	}

	public void setLoginToken(String loginToken) {
		this.loginToken = loginToken;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Boolean getIsAuto() {
		return isAuto;
	}

	public void setIsAuto(Boolean isAuto) {
		this.isAuto = isAuto;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateLoginTime() {
		return updateLoginTime;
	}

	public void setUpdateLoginTime(Date updateLoginTime) {
		this.updateLoginTime = updateLoginTime;
	}

	public Date getForbidLoginTime() {
		return forbidLoginTime;
	}

	public void setForbidLoginTime(Date forbidLoginTime) {
		this.forbidLoginTime = forbidLoginTime;
	}

	public Integer getLoginErrorTimes() {
		return loginErrorTimes;
	}

	public void setLoginErrorTimes(Integer loginErrorTimes) {
		this.loginErrorTimes = loginErrorTimes;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}
