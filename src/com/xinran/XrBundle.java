package com.xinran;

import java.util.Locale;
import java.util.ResourceBundle;

public class XrBundle {

	private static Locale myLocale =Locale.CHINESE; //Locale.US;
	// 根据指定国家/语言环境加载资源文件
	public static ResourceBundle bundle = ResourceBundle.getBundle("conf", myLocale);
}
