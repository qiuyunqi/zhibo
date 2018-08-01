package com.zhibo.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhibo.dao.LiveStockDao;
import com.zhibo.model.LiveStock;
import com.zhibo.service.LiveStockService;
import com.zhibo.util.DateUtil;

@Service
public class LiveStockServiceImpl implements LiveStockService {

	@Autowired
	private LiveStockDao liveStockDao;

	public List<LiveStock> findByTime(String startTime, Date nowDate) {
		
		// 获取今天的年月日
		String baseTime = DateUtil.showDate(new Date());
		// 组装今天的 年 月 日 开始时间
		String newDate = baseTime + " " + startTime;
		//组装今天上午的 年 月 日结束时间
		String noonEndDate = baseTime + " " + "11:30:00";
		//组装今天下午的 年 月 日开始时间
		String noonStartDate = baseTime + " " + "13:00:00";
		// 组装今天的 年 月 日 结束时间
		String endDate = baseTime + " " + "15:00:00";
		// 早上开始时间
		Date startDate = DateUtil.getDateFromString(newDate, "yyyy-MM-dd HH:mm:ss");
		// 下午开始时间
		Date startDate1 = DateUtil.getDateFromString(noonStartDate, "yyyy-MM-dd HH:mm:ss");//下午的 年 月 日开始时间
		// 上午结束时间
		Date endDate1 = DateUtil.getDateFromString(noonEndDate, "yyyy-MM-dd HH:mm:ss");//上午的 年 月 日结束时间
	//	long endTime = DateUtil.getDateFromString(endDate, "yyyy-MM-dd HH:mm:ss").getTime();
      /*if (endTime - nowDate.getTime() < 0) {
		}*/
		// 下午 15：30 下午结束时间
		nowDate = DateUtil.getDateFromString(endDate, "yyyy-MM-dd HH:mm:ss");
		
		return liveStockDao.findByTime(startDate, nowDate, startDate1, endDate1);
		
	}
	
	public static void main(String[] args) {
		String showDate = DateUtil.showDate(new Date());
		String newDate = showDate + " " + "9:00:00";
		System.out.println(newDate);
		System.out.println(DateUtil.getDateFromString(newDate));
	}

	public LiveStock findLast() {
		return liveStockDao.findLast();
	}
}
