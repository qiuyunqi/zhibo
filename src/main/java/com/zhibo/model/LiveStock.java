package com.zhibo.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 上证指数
 * @author han
 *
 */

@Entity
@Table(name = "live_stock")
public class LiveStock implements Serializable {

	private static final long serialVersionUID = -2420095728784599832L;
	
	@Id
	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false)
	private Long id;
	
	@Column(name = "change_rate")
	private Double changeRate;
	
	@Column(name = "cur_price")
	private Double curPrice;
	
	@Column(name = "high_price")
	private Double highPrice;
	
	@Column(name = "ind_code")
	private String indCode;

	@Column(name = "ind_short_name")
	private String indShortName;
	
	@Column(name = "ind_uni_code")
	private Integer indUniCode;
	
	@Column(name = "low_price")
	private Double lowPrice;
	
	@Column(name = "open_price")
	private Double openPrice;
	
	@Column(name = "pre_close_price")
	private Double preClosePrice;
	
	@Column(name = "price_updown")
	private Double priceUpdown;
	
	@Column(name = "time_stamp")
	private Date timeStamp;
	
	@Column(name = "trade_amut")
	private Double tradeAmut;
	
	@Column(name = "trade_date")
	private Date tradeDate;
	
	@Column(name = "trade_vol")
	private Integer tradeVol;
	
	public LiveStock(){}

	public LiveStock(Long id, Double changeRate, Double curPrice,
			Double highPrice, String indCode, String indShortName,
			Integer indUniCode, Double lowPrice, Double openPrice,
			Double preClosePrice, Double priceUpdown, Date timeStamp,
			Double tradeAmut, Date tradeDate, Integer tradeVol) {
		super();
		this.id = id;
		this.changeRate = changeRate;
		this.curPrice = curPrice;
		this.highPrice = highPrice;
		this.indCode = indCode;
		this.indShortName = indShortName;
		this.indUniCode = indUniCode;
		this.lowPrice = lowPrice;
		this.openPrice = openPrice;
		this.preClosePrice = preClosePrice;
		this.priceUpdown = priceUpdown;
		this.timeStamp = timeStamp;
		this.tradeAmut = tradeAmut;
		this.tradeDate = tradeDate;
		this.tradeVol = tradeVol;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getChangeRate() {
		return changeRate;
	}

	public void setChangeRate(Double changeRate) {
		this.changeRate = changeRate;
	}

	public Double getCurPrice() {
		return curPrice;
	}

	public void setCurPrice(Double curPrice) {
		this.curPrice = curPrice;
	}

	public Double getHighPrice() {
		return highPrice;
	}

	public void setHighPrice(Double highPrice) {
		this.highPrice = highPrice;
	}

	public String getIndCode() {
		return indCode;
	}

	public void setIndCode(String indCode) {
		this.indCode = indCode;
	}

	public String getIndShortName() {
		return indShortName;
	}

	public void setIndShortName(String indShortName) {
		this.indShortName = indShortName;
	}

	public Integer getIndUniCode() {
		return indUniCode;
	}

	public void setIndUniCode(Integer indUniCode) {
		this.indUniCode = indUniCode;
	}

	public Double getLowPrice() {
		return lowPrice;
	}

	public void setLowPrice(Double lowPrice) {
		this.lowPrice = lowPrice;
	}

	public Double getOpenPrice() {
		return openPrice;
	}

	public void setOpenPrice(Double openPrice) {
		this.openPrice = openPrice;
	}

	public Double getPreClosePrice() {
		return preClosePrice;
	}

	public void setPreClosePrice(Double preClosePrice) {
		this.preClosePrice = preClosePrice;
	}

	public Double getPriceUpdown() {
		return priceUpdown;
	}

	public void setPriceUpdown(Double priceUpdown) {
		this.priceUpdown = priceUpdown;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	public Double getTradeAmut() {
		return tradeAmut;
	}

	public void setTradeAmut(Double tradeAmut) {
		this.tradeAmut = tradeAmut;
	}

	public Date getTradeDate() {
		return tradeDate;
	}

	public void setTradeDate(Date tradeDate) {
		this.tradeDate = tradeDate;
	}

	public Integer getTradeVol() {
		return tradeVol;
	}

	public void setTradeVol(Integer tradeVol) {
		this.tradeVol = tradeVol;
	}
}
