package com.app.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	// 分钟增加
	public static Date addDateMinutes(Date date, int minutes) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MINUTE, minutes);
		return cal.getTime();
	}

	// 秒增加
	public static Date addDateSeconds(Date date, int seconds) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.SECOND, seconds);
		return cal.getTime();
	}

	// 小时增加
	public static Date addDateHours(Date date, int hours) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.HOUR, hours);
		return cal.getTime();
	}

	// 天增加
	public static Date addDateDays(Date date, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, days);
		return cal.getTime();
	}
	
	// 两个日期相差的天数  first 小日期   second 大日期
	public static int longOfTwoDate(Date first, Date second) {
		Calendar calendarFirst = Calendar.getInstance();
		calendarFirst.setTime(first);
		calendarFirst.set(Calendar.HOUR_OF_DAY, 0);
		calendarFirst.set(Calendar.MINUTE, 0);
		calendarFirst.set(Calendar.SECOND, 0);
		calendarFirst.set(Calendar.MILLISECOND, 0);
		
		Calendar calendarSecond = Calendar.getInstance();
		calendarSecond.setTime(second);
		calendarSecond.set(Calendar.HOUR_OF_DAY, 0);
		calendarSecond.set(Calendar.MINUTE, 0);
		calendarSecond.set(Calendar.SECOND, 0);
		calendarSecond.set(Calendar.MILLISECOND, 0);

		int cnt = 0;
		while (calendarFirst.compareTo(calendarSecond) != 0) {
			calendarFirst.add(Calendar.DATE, 1);
			cnt++;
		}
		return cnt;
	}
	
	// 当天日期的开始时间   2018-11-26 00:00:00
	public static Date getStartDate(Date date){
		Calendar calendarFirst = Calendar.getInstance();
		calendarFirst.setTime(date);
		calendarFirst.set(Calendar.HOUR_OF_DAY, 0);
		calendarFirst.set(Calendar.MINUTE, 0);
		calendarFirst.set(Calendar.SECOND, 0);
		calendarFirst.set(Calendar.MILLISECOND, 0);  // 毫秒
		return calendarFirst.getTime();
	}
	
	// 当天日期的结束日期  2018-11-26 23:59:59
	public static Date getEndDate(Date date){
		
		Calendar calendarFirst = Calendar.getInstance();
		calendarFirst.setTime(date);
		calendarFirst.set(Calendar.HOUR_OF_DAY, 0);
		calendarFirst.set(Calendar.MINUTE, 0);
		calendarFirst.set(Calendar.SECOND, 0);
		calendarFirst.set(Calendar.MILLISECOND, 0);
		
		calendarFirst.add(Calendar.DATE,1);
		calendarFirst.add(Calendar.SECOND, -1);
		return calendarFirst.getTime();
	}
	
	
	public static String dateyyyyMMddHHmmssFormat(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}
}
