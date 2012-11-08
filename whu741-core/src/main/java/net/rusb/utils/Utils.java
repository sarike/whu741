package net.rusb.utils;

import java.util.Random;

public class Utils {
	public static boolean isEmpty(String s){
		return s==null||s.equalsIgnoreCase("");
	}
	/**
	 * 获取指定长度的一个随机字符串
	 * 字符串中包括数字、小写字母、大写字母
	 * @param length 想要生成的随机字符串的长度
	 * @return
	 */
	public static String getRandomUniqueStr(int length){
		
		String strSource = "0123456789" +
				"abcdefghigklmnopqrstuvwsyz" +
				"ABCDEFGHIJKLMNOPQRSTUVWSYZ";
		StringBuilder sBuilder = new StringBuilder();
		Random random = new Random();
		while(length>0){
			sBuilder.append(strSource.charAt(random.nextInt(62)));
			length--;
		}
		return sBuilder.toString();
	}
}

