package com.zhibo.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhibo.model.FuUser;
import com.zhibo.model.LiveJackpot;
import com.zhibo.model.LiveJackpotTime;
import com.zhibo.model.LiveLotteryInfo;
import com.zhibo.model.SysConfig;
import com.zhibo.service.FuUserService;
import com.zhibo.service.LiveJackpotService;
import com.zhibo.service.LiveJackpotTimeService;
import com.zhibo.service.LotteryService;
import com.zhibo.service.SysConfigService;
import com.zhibo.util.WriteJsonUtil;

/**
 * 抽奖
 * 
 * @author han
 * 
 */
@Controller
@RequestMapping("/live")
public class LotteryController extends BaseController {
	private static final Log logger = LogFactory.getLog(LotteryController.class);

	@Resource
	private LotteryService lotteryService;
	@Resource
	private LiveJackpotService liveJackpotService;
	@Resource
	private LiveJackpotTimeService liveJackpotTimeService;
	@Resource
	private SysConfigService sysConfigService;
	@Resource
	private FuUserService fuUserService;

	/**
	 * 抽奖
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/lottery" + URL_SUFFIX, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String lottery(HttpServletRequest request, HttpServletResponse response) {
		JSONObject obj = new JSONObject();
		try {
			FuUser fuUser = (FuUser) request.getSession().getAttribute("fuUser");
			fuUser = fuUserService.get(fuUser.getId());
			SysConfig sysConfig = sysConfigService.getByCode("004");
			if (null == fuUser.getIntegral()) {
				obj.put("success", 0);
				obj.put("message", "您的积分不足");
				WriteJsonUtil.writeJson(response, obj);
				return null;
			}
			if (fuUser.getQidaIntegral().subtract(sysConfig.getValue()).compareTo(BigDecimal.ZERO) == -1) {
				obj.put("success", 0);
				obj.put("message", "您的积分不足");
				WriteJsonUtil.writeJson(response, obj);
				return null;
			}
			LiveLotteryInfo lottery = lotteryService.findByUserIdAndStatus(fuUser.getId(), 1, 0);
			if (null != lottery) {
				int angle = new Random().nextInt((Integer) lottery.getLiveJackpot().getMaxAngle() - lottery.getLiveJackpot().getMinAngle()) + lottery.getLiveJackpot().getMinAngle();

				String msg = lottery.getLiveJackpot().getName();// 提示中奖奖品名称
				lottery.setFuUser(fuUser);
				lottery.setSpendIntegral(sysConfig.getValue());
				lottery.setDrawTime(new Date());
				lottery.setIsReceive(0);
				lottery.setStatus(2);
				if (null != lottery.getLiveJackpot() && lottery.getLiveJackpot().getGrade() != 0) {
					lottery.setIsWin(1);
				} else {
					lottery.setIsWin(0);
				}
				lotteryService.save(lottery, fuUser, sysConfig.getValue());

				obj.put("angle", angle);
				obj.put("msg", msg);
				obj.put("qidaIntegral", fuUser.getQidaIntegral());
				logger.info(obj.toString());
				WriteJsonUtil.writeJson(response, obj);
				return null;
			}
			List<LiveJackpot> jackpotList = liveJackpotService.findNowPeriods();
			// 查看当前时间是否是特殊段时间
			List<LiveJackpotTime> timeList = liveJackpotTimeService.findByTime(new Date(), 0);
			if (null != timeList && timeList.size() > 0) {
				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				for (LiveJackpotTime liveJackpot : timeList) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("id", liveJackpot.getLivejackpot().getId());
					map.put("minAngle", liveJackpot.getLivejackpot().getMinAngle());
					map.put("maxAngle", liveJackpot.getLivejackpot().getMaxAngle());
					map.put("name", liveJackpot.getLivejackpot().getName());
					map.put("changes", liveJackpot.getChanges());
					list.add(map);
				}
				Object result[] = awards(fuUser, jackpotList.size(), list, sysConfig.getValue().toString());// 抽奖后返回角度和奖品等级
				// 返回信息给jsp
				obj.put("angle", result[0]);
				obj.put("msg", result[2]);
				obj.put("qidaIntegral", fuUser.getQidaIntegral());
				logger.info(obj.toString());
				WriteJsonUtil.writeJson(response, obj);
				return null;
			} else {
				
				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				for (LiveJackpot liveJackpot : jackpotList) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("id", liveJackpot.getId());
					map.put("minAngle", liveJackpot.getMinAngle());
					map.put("maxAngle", liveJackpot.getMaxAngle());
					map.put("name", liveJackpot.getName());
					map.put("changes", liveJackpot.getChanges());
					list.add(map);
				}
				Object result[] = award(fuUser, list, sysConfig.getValue().toString());// 抽奖后返回角度和奖品等级
				// 返回信息给jsp
				
				obj.put("angle", result[0]);
				obj.put("msg", result[2]);
				obj.put("qidaIntegral", fuUser.getQidaIntegral());
				logger.info(obj.toString());
				WriteJsonUtil.writeJson(response, obj);
				return null;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			obj.put("success", 0);
			obj.put("message", "请联系管理员");
			try {
				WriteJsonUtil.writeJson(response, obj);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			return null;
		}
	}

	/**
	 * 特殊时段抽奖
	 * @param fuUser
	 * @param length
	 * @param prizeArr
	 * @param integral
	 * @return
	 */
	private Object[] awards(FuUser fuUser, int length,
			List<Map<String, Object>> prizeArr, String integral) {
		// 概率数组
		Integer obj[] = new Integer[prizeArr.size()];
		int i = 0;
		for (Map<String, Object> prize : prizeArr) {
			obj[i] = (Integer) prize.get("changes");
			i++;
		}
		Map<String, Object> prizeId = getRands(prizeArr, length); // 根据概率获取奖项id
		logger.info("中奖:" + prizeId);
		LiveJackpot liveJackpot = null;
		Boolean flag = true; // 抽中的奖品是否是 谢谢参与 true : 是  false : 否
		if (null == prizeId) {
			// 查询本期的谢谢参与奖
			liveJackpot  = liveJackpotService.findThanks();
		} else {
			// 保存是否中奖
			liveJackpot = liveJackpotService.getById((long) prizeId.get("id"));
			flag = false;
		}
		// 保存中奖信息 设置特殊时段的中奖奖品的概率为0并关闭
		LiveLotteryInfo info = new LiveLotteryInfo();
		info.setFuUser(fuUser);
		info.setLiveJackpot(liveJackpot);
		info.setSpendIntegral(new BigDecimal(integral));
		info.setDrawTime(new Date());
		info.setIsReceive(0);
		info.setStatus(0);
		if (null != liveJackpot && liveJackpot.getGrade() != 0) {
			info.setIsWin(1);
		} else {
			info.setIsWin(0);
		}
		lotteryService.saveAndSetTime(info, fuUser, new BigDecimal(integral), flag);

		// 旋转角度
		int angle = new Random().nextInt((Integer) liveJackpot.getMaxAngle() - liveJackpot.getMinAngle()) + liveJackpot.getMinAngle();

		String msg = liveJackpot.getName();// 提示中奖奖品名称
		return new Object[] { angle, prizeId, msg };

	}

	/**
	 * 特殊时段抽奖算法
	 * @param prizeArr
	 * @param length
	 * @return
	 */
	private Map<String, Object> getRands(List<Map<String, Object>> prizeArr, int length) {
		Map<String, Object> result = null;
		Integer randomnum = randomnum(1, 100);
		int line = 0;
		int temp = 0;
		int sum = 100;

		for (int i = 0; i < length; i++) {// 概率数组循环
			if (i > prizeArr.size() -1) {
				return null;
			}
			int c = (int) prizeArr.get(i).get("changes");
			temp = temp + c;
			line = sum - temp;

			if (c != 0) {
				if (randomnum > line && randomnum <= (line + c)) {
					result = prizeArr.get(i);
					break;
				}
			}
		}
		return result;
	}

	// 抽奖并返回角度和奖项
	public Object[] award(FuUser fuUser, List<Map<String, Object>> prizeArr, String integral) {
		// 概率数组
		Integer obj[] = new Integer[prizeArr.size()];
		int i = 0;
		for (Map<String, Object> prize : prizeArr) {
			obj[i] = (Integer) prize.get("changes");
			i++;
		}
		Map<String, Object> prizeId = getRand(prizeArr); // 根据概率获取奖项id
		logger.info("中奖:" + prizeId);
		// 保存是否中奖
		LiveJackpot liveJackpot = liveJackpotService.getById((long) prizeId.get("id"));
		LiveLotteryInfo info = new LiveLotteryInfo();
		info.setFuUser(fuUser);
		info.setLiveJackpot(liveJackpot);
		info.setSpendIntegral(new BigDecimal(integral));
		info.setDrawTime(new Date());
		info.setIsReceive(0);
		info.setStatus(0);
		if (null != liveJackpot && liveJackpot.getGrade() != 0) {
			info.setIsWin(1);
		} else {
			info.setIsWin(0);
		}
		lotteryService.save(info, fuUser, new BigDecimal(integral));

		// 旋转角度
		int angle = new Random().nextInt((Integer) liveJackpot.getMaxAngle() - liveJackpot.getMinAngle()) + liveJackpot.getMinAngle();

		String msg = liveJackpot.getName();// 提示中奖奖品名称
		return new Object[] { angle, prizeId, msg };
	}

	// 根据概率获取奖项
	public Map<String, Object> getRand(List<Map<String, Object>> prizeArr) {
		Map<String, Object> result = null;
		Integer randomnum = randomnum(1, 100);
		int line = 0;
		int temp = 0;
		int sum = 100;

		for (int i = 0; i < prizeArr.size(); i++) {// 概率数组循环
			int c = (int) prizeArr.get(i).get("changes");
			temp = temp + c;
			line = sum - temp;

			if (c != 0) {
				if (randomnum > line && randomnum <= (line + c)) {
					result = prizeArr.get(i);
					break;
				}
			}
		}
		return result;
	}

	// 获取2个值之间的随机数
	private static Integer randomnum(int smin, int smax) {
		int range = smax - smin;
		double rand = Math.random();
		int sum = smin + (int) Math.round(rand * range);
		return sum;
	}

	/**
	 * 基础测试
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		for (int k = 0; k < 100; k++) {
			Integer obj[] = {20};
			// Integer obj[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 80, 20};
			Integer result = null;
			Integer randomnum = randomnum(1, 100);
			int line = 0;
			int temp = 0;
			int sum = 100;

			for (int i = 0; i < obj.length; i++) {// 概率数组循环
				int c = obj[i];
				temp = temp + c;
				line = sum - temp;

				if (c != 0) {
					if (randomnum > line && randomnum <= (line + c)) {
						result = obj[i];
						break;
					}
				}
			}
			System.out.println("第 " + k + "次的结果" + result);
		}

	}
}
