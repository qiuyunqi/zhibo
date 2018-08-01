package com.zhibo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * FuGame entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "sys_dictionary")
public class FuDictionary implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1478760495989738120L;

	@Id
	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "pid")
	private Long pid;

	@Column(name = "isEnabled")
	private Integer isEnabled;

	// Constructors

	/** default constructor */
	public FuDictionary() {
	}

	/** full constructor */
	public FuDictionary(Long id, String name, Long pid, Integer isEnabled) {
		super();
		this.id = id;
		this.name = name;
		this.pid = pid;
		this.isEnabled = isEnabled;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getPid() {
		return this.pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public Integer getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(Integer isEnabled) {
		this.isEnabled = isEnabled;
	}

}