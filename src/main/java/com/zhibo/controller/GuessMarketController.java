package com.zhibo.controller;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhibo.model.FuUser;
import com.zhibo.model.GuessMarket;
import com.zhibo.model.GuessRecord;
import com.zhibo.model.LiveStock;
import com.zhibo.service.FuUserService;
import com.zhibo.service.GuessMarketService;
import com.zhibo.service.GuessRecordService;
import com.zhibo.service.LiveStockService;
import com.zhibo.service.QidaScoreService;
import com.zhibo.util.DateUtil;
import com.zhibo.util.WriteJsonUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 大盘猜猜乐
 */
@Controller
@RequestMapping("/live")
public class GuessMarketController {
	@Resource
	private GuessMarketService guessMarketService;
	@Resource
	private GuessRecordService guessRecordService;
	@Resource
	private FuUserService fuUserService;
	@Resource
	private QidaScoreService qidaScoreService;
	@Resource
	private LiveStockService liveStockService;

	/**
	 * 大盘猜猜乐
	 */
	@RequestMapping(value = "/guess.html", produces = "text/html;charset=UTF-8")
	public String guess(Integer pageNo, Integer pageSize, HttpServletRequest request, ModelMap model) {
		try {
			if (pageNo == null || pageSize == null) {
				pageNo = 1;
				pageSize = 5;
			}
			FuUser user = (FuUser) request.getSession().getAttribute("fuUser");
			FuUser fuUser = fuUserService.get(Long.valueOf("4546"));
			request.getSession().setAttribute("fuUser", fuUser);

			// 中奖名单 前12个
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("orderIncome", 1);
			List<GuessRecord> records = guessRecordService.findList(0, 12, map);
			model.put("records", records);

			// 查询每期的个人流水信息列表
			Map<String, Object> map1 = new HashMap<String, Object>();
			map1.put("userId", fuUser.getId());
			List<GuessRecord> recordList = guessRecordService.findList((pageNo - 1) * pageSize, pageSize, map1);
			Integer count = guessRecordService.getCount(map1);

			request.setAttribute("recordList", recordList);
			request.setAttribute("pageSize", pageSize);
			request.setAttribute("pageNo", pageNo);
			request.setAttribute("totalCount", count);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "live/guess";
	}

	/**
	 * 单独获取stock数据 用于K线图
	 * 
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getStockData.html")
	@ResponseBody
	public String getStockDate(HttpServletResponse response) throws Exception {
		JSONObject obj = new JSONObject();
		List<LiveStock> liveStock = liveStockService.findByTime("9:30:00", new Date());
		if (null == liveStock || liveStock.size() <= 0) {
			obj.put("success", 0);
			obj.put("msg", "暂无数据");
			WriteJsonUtil.writeJson(response, obj);
			return null;
		}

		// 当前价 均价 交易量 幅度 当前时间(格式: 2017/02/20/一10:17)
		// 计算今天的年月日 星期
		String nowTime = null;
		String nowDay = DateUtil.getStrFromDate(new Date(), "yyyy/MM/dd");
		String nowWeek = DateUtil.getWeekOfDate(new Date());

		List<Object> list = new ArrayList<Object>();
		List<Object> list1 = new ArrayList<Object>();
		List<Object> list2 = new ArrayList<Object>();
		List<Object> list3 = new ArrayList<Object>();
		for (LiveStock ls : liveStock) {

			nowTime = DateUtil.getStrFromDate(ls.getTradeDate(), "HH:mm");
			String nowDate = nowDay + "/" + nowWeek + nowTime;
			obj.put("nowDate", nowDate);
			String curPrice = new DecimalFormat("#0.00").format(ls.getCurPrice());
			list1.add(curPrice);//
			// 1 升 0 降
			if (ls.getCurPrice() - ls.getPreClosePrice() >= 0) {
				obj.put("curFalg", 1);
			} else {
				obj.put("curFalg", 0);
			}
			obj.put("changeRate", ls.getChangeRate() + "%");

			if (ls.getChangeRate() >= 0) {
				obj.put("changeFalg", 1);
			} else {
				obj.put("changeFalg", 0);
			}

			list2.add(ls.getTradeVol());

			Double tradeAmut = ls.getTradeAmut();//
			list3.add(tradeAmut);
		}

		list.add(liveStock.get(0).getPreClosePrice()); // 昨日收盘价
		list.add(list1); // 当前价
		list.add(list2); // 交易次数
		list.add(list3); // 交易额
		obj.put("IndShortName", liveStock.get(0).getIndShortName());// 指数简称
		obj.put("indcode", liveStock.get(0).getIndCode());// 指数代码
		obj.put("success", 1);
		obj.put("list", list);
		WriteJsonUtil.writeJson(response, obj);
		return null;

	}

	/**
	 * 单独获取stock数据 用于K线图上面的标头数据
	 * 
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getStockData1.html")
	@ResponseBody
	public String getStockDate1() {
		JSONObject obj = new JSONObject();
		LiveStock liveStock = liveStockService.findLast();
		if (null == liveStock) {
			obj.put("success", 0);
			obj.put("msg", "暂无数据");
			return obj.toString();
		}
		// 当前价 均价 交易量 幅度 当前时间(格式: 2017/02/20/一10:17)
		// 计算今天的年月日 星期
		String nowDay = DateUtil.getStrFromDate(new Date(), "yyyy/MM/dd");
		String nowWeek = DateUtil.getWeekOfDate(new Date());
		String nowTime = DateUtil.getStrFromDate(liveStock.getTradeDate(), "HH:mm");
		String nowDate = nowDay + "/" + nowWeek + nowTime;

		obj.put("curPrice", liveStock.getCurPrice());
		// 1 升 0 降
		if (liveStock.getCurPrice() - liveStock.getOpenPrice() >= 0) {
			obj.put("curFalg", 1);
		} else {
			obj.put("curFalg", 0);
		}
		obj.put("changeRate", liveStock.getChangeRate() + "%");
		if (liveStock.getChangeRate() >= 0) {
			obj.put("changeFalg", 1);
		} else {
			obj.put("changeFalg", 0);
		}
		Integer tradeVol = liveStock.getTradeVol();
		obj.put("tradeVol", tradeVol);
		obj.put("nowDate", nowDate);
		obj.put("success", 1);
		return obj.toString();
	}

	/**
	 * 根据数字获取亿单位的数值
	 * 
	 * @param tradeVol
	 * @return
	 */
	@SuppressWarnings("unused")
	private String getVol(Integer tradeVol) {
		if (0 < tradeVol && tradeVol <= 10000) {
			return tradeVol.toString() + "手";
		}
		if (10000 < tradeVol && tradeVol < 100000000) {
			Double n = tradeVol / 10000.00;
			String format = new DecimalFormat("#0.00").format(n);
			return format + "万手";
		}
		Double n = tradeVol / 100000000.00;
		String format = new DecimalFormat("#0.00").format(n);
		return format + "亿手";
	}

	/**
	 * 分页数据
	 */
	@RequestMapping(value = "/guessData.html", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String guessData(Integer pageNo, Integer pageSize, HttpServletRequest request, ModelMap model) {
		JSONObject json = new JSONObject();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			List<GuessMarket> markets = guessMarketService.findList((pageNo - 1) * pageSize, pageSize, map);
			JSONArray array = new JSONArray();
			if (markets != null && markets.size() > 0) {
				for (GuessMarket market : markets) {
					JSONObject obj = new JSONObject();
					obj.put("id", market.getId());
					obj.put("guessScore", market.getGuessScore());
					obj.put("acceptFactor", market.getAcceptFactor());
					obj.put("acceptResult", market.getAcceptResult());
					obj.put("state", market.getState());
					obj.put("isClose", market.getIsClose());
					obj.put("type", market.getType());
					if (market.getType() == 1) {
						obj.put("time", "参与时间: 00:00-13:00");
					} else {
						obj.put("time", "参与时间: 00:00-09:15");
					}
					obj.put("createTime", market.getCreateTime() == null ? "" : DateUtil.getStrFromDate(market.getCreateTime(), "yyyy.MM.dd"));
					obj.put("acceptTime", market.getAcceptTime() == null ? "" : DateUtil.getStrFromDate(market.getAcceptTime(), "yyyy.MM.dd"));

					map.put("marketId", market.getId());
					List<GuessRecord> list = guessRecordService.findList(0, Integer.MAX_VALUE, map);

					Map<String, Object> map1 = new HashMap<String, Object>();
					map1.put("marketId", market.getId());
					map1.put("guessType", 1);
					List<GuessRecord> list1 = guessRecordService.findList(0, Integer.MAX_VALUE, map1);

					Map<String, Object> map2 = new HashMap<String, Object>();
					map2.put("marketId", market.getId());
					map2.put("guessType", 2);
					List<GuessRecord> list2 = guessRecordService.findList(0, Integer.MAX_VALUE, map2);

					Map<String, Object> map3 = new HashMap<String, Object>();
					map3.put("marketId", market.getId());
					map3.put("guessType", 3);
					List<GuessRecord> list3 = guessRecordService.findList(0, Integer.MAX_VALUE, map3);

					Map<String, Object> map4 = new HashMap<String, Object>();
					map4.put("marketId", market.getId());
					map4.put("guessType", 4);
					List<GuessRecord> list4 = guessRecordService.findList(0, Integer.MAX_VALUE, map4);
					obj.put("percent1", list1.size() == 0 ? "0" : list.size() == 0 ? "0" : new BigDecimal(list1.size() * 100).divide(new BigDecimal(list.size()), 0, BigDecimal.ROUND_HALF_UP));
					obj.put("percent2", list2.size() == 0 ? "0" : list.size() == 0 ? "0" : new BigDecimal(list2.size() * 100).divide(new BigDecimal(list.size()), 0, BigDecimal.ROUND_HALF_UP));
					obj.put("percent3", list3.size() == 0 ? "0" : list.size() == 0 ? "0" : new BigDecimal(list3.size() * 100).divide(new BigDecimal(list.size()), 0, BigDecimal.ROUND_HALF_UP));
					obj.put("percent4", list4.size() == 0 ? "0" : list.size() == 0 ? "0" : new BigDecimal(list4.size() * 100).divide(new BigDecimal(list.size()), 0, BigDecimal.ROUND_HALF_UP));
					array.add(obj);
				}
				// 查询个人的总消费积分 已经总得盈亏的总和
				json.put("array", array);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json.toString();
	}

	/**
	 * 判断是否封盘
	 */
	@RequestMapping(value = "/isClose.html", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String isClose(Long marketId, ModelMap model) {
		try {
			GuessMarket market = guessMarketService.get(marketId);
			if (market.getIsClose() == 1 && market.getType() == 0) {
				return "-1";// 上午封盘无法竞猜
			}
			if (market.getIsClose() == 1 && market.getType() == 1) {
				return "-2";// 下午封盘无法竞猜
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 购买猜猜乐
	 */
	@RequestMapping(value = "/payGuess.html", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String payGuess(Long marketId, Integer guessType, ModelMap model, HttpServletRequest request) {
		try {
			GuessMarket market = guessMarketService.get(marketId);
			FuUser fuUser = (FuUser) request.getSession().getAttribute("fuUser");
			if (fuUser == null) {
				return "-2";// 未登录
			}
			FuUser user = fuUserService.get(fuUser.getId());
			if (user.getQidaIntegral() == null || user.getQidaIntegral().compareTo(market.getGuessScore()) == -1) {
				return "-3";// 积分不足
			}
			GuessRecord record = new GuessRecord();
			record.setFuUser(user);
			record.setGuessMarket(market);
			record.setGuessTime(new Date());
			record.setGuessType(guessType);
			record.setScore(market.getGuessScore());
			record.setTradeNo(String.valueOf((int) ((Math.random() * 1000000 + 100000))));
			record.setOrderIncome(BigDecimal.ZERO);
			guessRecordService.save(record);
			// 扣除积分
			user.setQidaIntegral((user.getQidaIntegral() == null ? BigDecimal.ZERO : user.getQidaIntegral()).subtract(market.getGuessScore()));
			fuUserService.save(user);
			// 积分明细
			qidaScoreService.saveScoreByQd(user, 56, market.getGuessScore(), BigDecimal.ZERO, user.getQidaIntegral(), 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * public static void main(String[] args) { List<Object> list0 = new ArrayList<Object>(); List<Object> list1 = new ArrayList<Object>(); List<Object> list2 = new ArrayList<Object>(); List<Object> list3 = new ArrayList<Object>();
	 * 
	 * for (int i = 0; i < 10; i++) { double random1 = Math.random(); double random2 = Math.random(); double random3 = Math.random(); list1.add(random1); list2.add(random2); list3.add(random3); } list0.add(list1); list0.add(list2); list0.add(list3);
	 */
	/*
	 * for (Object obj1 : list1) { System.out.println(obj1); }
	 * 
	 * System.out.println("--------------------------------------------"); for (Object obj2 : list2) { System.out.println(obj2); } System.out.println("--------------------------------------------"); for (Object obj3 : list3) { System.out.println(obj3); }
	 */
	/*
	 * System.out.println(list0); }
	 */

}
