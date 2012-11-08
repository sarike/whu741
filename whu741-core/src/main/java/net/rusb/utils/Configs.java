package net.rusb.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Configs {
	private static Properties pros ;
	static{
		InputStream in = Configs.class.getResourceAsStream("/config.properties");
		pros = new Properties();
		try {
			pros.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static String get(String key){
		return pros.getProperty(key);
	}
	public static String get(String key,String defaultValue){
		return pros.getProperty(key, defaultValue);
	}
}
