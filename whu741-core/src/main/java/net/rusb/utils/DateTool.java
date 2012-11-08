package net.rusb.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateTool {
	
	/**
	 * 获取指定时区的时间
	 * @param timeZoneID 时区ID
	 * @return
	 */
	public static Date getTime(String timeZoneID){
		TimeZone timeZone = TimeZone.getTimeZone(timeZoneID);
		//本地时区偏移量
//		long localTimeZoneOffSet = TimeZone.getDefault().getRawOffset();// 莫斯科真你妈蛋疼
		//当前时区的小时
		int localTimeZoneHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
		//指定时区偏移量
//		long dstTimzZoneOffSet = timeZone.getRawOffset();
		int dstTimeZoneHour = Calendar.getInstance(timeZone).get(Calendar.HOUR_OF_DAY);
		//两时区偏移之差
//		long diff = localTimeZoneOffSet - dstTimzZoneOffSet;
		long diff = (localTimeZoneHour - dstTimeZoneHour)*3600000;
		long dstTime = new Date().getTime() - diff;
		return new Date(dstTime);
	}
	/**
	 * 获得中国标准时间
	 * @return
	 */
	public static Date getCST(){
		return getTime("GMT+8");
	}
}
