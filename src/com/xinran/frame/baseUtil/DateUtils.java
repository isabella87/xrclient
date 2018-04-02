package com.xinran.frame.baseUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

public class DateUtils {
	private static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 
	 * @param s
	 * @return
	 */
	public static Date parseDate(final String s) {
		return parseDate(s, DEFAULT_FORMAT);
	}

	/**
	 * 
	 * @param s
	 * @param format
	 * @return
	 */
	public static Date parseDate(String s, String format) {
		final SimpleDateFormat sf = new SimpleDateFormat(format);
		try {
			return sf.parse(s);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 
	 * @param d
	 * @return
	 */
	public static Date truncDate(final Date d) {
		if (d == null)
			return null;
		final Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		return c.getTime();
	}

	/**
	 * 获取指定日期前任意天数或者后任意天数日期的方法
	 * 
	 * @param date
	 *            指定日期，为空表示今天
	 * @param addDay
	 *            前任意天数或者后任意天数，正数为后，负数为前
	 * @return
	 */
	public static Date addDay(Date date, int addDay) {
		if (date == null) {
			date = new Date();
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, addDay);
		return cal.getTime();
	}

	/**
	 * 
	 * @param date
	 * @param addDay
	 * @return
	 */
	public static String addDayStr(final Date date, final int addDay) {
		return addDayStr(date, addDay, DEFAULT_FORMAT);
	}

	/**
	 * 
	 * @param date
	 * @param addDay
	 * @param format
	 * @return
	 */
	public static String addDayStr(final Date date, final int addDay, final String format) {
		return toDateStr(addDay(date, addDay), format);
	}

	/**
	 * 
	 * @param d
	 *            日期参数
	 * @return "yyyy-MM-dd HH:mm:ss"格式的标准日期字符串
	 */
	public static String toStandardStr(final Date d) {
		if (d == null) {
			return "";
		}
		final SimpleDateFormat sf = new SimpleDateFormat(DEFAULT_FORMAT);
		return sf.format(d);
	}

	/**
	 * 
	 * @param d
	 * @return
	 */
	public static String toFriendlyStr(final Date d) {
		return toStandardStr(d);
	}

	/**
	 * 
	 * @param date
	 * @return
	 */
	public static String toFriendlyStr(String date) {
		if (StringUtils.isBlank(date)) {
			return "";
		}

		Date d = parseDate(date);
		return toFriendlyStr(d);
	}

	/**
	 * 
	 * @param d
	 * @return
	 */
	public static String toDateStr(final Date d) {
		return toDayStr(d);
	}

	/**
	 * 
	 * @param d
	 * @param format
	 * @return
	 */
	public static String toDateStr(final Date d, final String format) {
		return toDayStr(d);
	}

	/**
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static String toDateStr(String date, String format) {
		if (StringUtils.isBlank(date)) {
			return "";
		}

		Date d = parseDate(date);
		return formatDate(d, format);
	}

	/**
	 * 和当前日期相差的天数
	 * 
	 * @param d
	 * @return
	 */
	public static int diffDays(Date d) {
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c2.clear();
		c2.setTime(d);
		int betweenYears = c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR);
		int betweenDays = c2.get(Calendar.DAY_OF_YEAR) - c1.get(Calendar.DAY_OF_YEAR);
		int yearDays = 0, sign = 1;
		if (betweenYears < 0) {
			betweenYears = -betweenYears;
			sign = -1;
		}
		for (int i = 0; i < betweenYears; i++) {
			c1.set(Calendar.YEAR, (c1.get(Calendar.YEAR) + 1));
			yearDays += c1.getMaximum(Calendar.DAY_OF_YEAR);
		}
		if (sign < 0) {
			yearDays = -yearDays;
		}
		return yearDays + betweenDays;
	}

	public static int diffDays(Date dStart, Date dEnd) {
		/*
		 * Calendar c1=Calendar.getInstance(); c1.clear(); c1.setTime(dStart);
		 * Calendar c2=Calendar.getInstance(); c2.clear(); c2.setTime(dEnd); int
		 * betweenYears = c2.get(Calendar.YEAR)-c1.get(Calendar.YEAR); int
		 * betweenDays =
		 * c2.get(Calendar.DAY_OF_YEAR)-c1.get(Calendar.DAY_OF_YEAR); int
		 * yearDays=0,sign=1; if (betweenYears<0){ betweenYears=-betweenYears;
		 * sign=-1; } for (int i = 0; i < betweenYears; i++) {
		 * c1.set(Calendar.YEAR, (c1.get(Calendar.YEAR) + 1)); yearDays+=
		 * c1.getMaximum(Calendar.DAY_OF_YEAR); } if (sign<0){
		 * yearDays=-yearDays; } return yearDays+betweenDays;
		 */
		long days = 0;
		days = dEnd.getTime() - dStart.getTime();
		Long ol = days / 1000 / 60 / 60 / 24;
		return ol.intValue();
	}

	public static int diffYears(Date d) {
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c2.clear();
		c2.setTime(d);
		return c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR);
	}

	public static int diffYears(Date dStart, Date dEnd) {
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(dStart);
		c2.setTime(dEnd);
		return c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR);
	}

	public static int diffMonths(Date d) {
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c2.clear();
		c2.setTime(d);
		return c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH);
	}

	public static String printDiffDay(int diff) {
		if (diff == 0)
			return "今天";
		if (diff == -1)
			return "昨天";
		if (diff == -2)
			return "前天";
		if (diff == 1)
			return "明天";
		if (diff == 2)
			return "后天";
		if (diff >= 3 && diff <= 365)
			return Integer.toString(diff) + "天后";
		if (diff >= -365 && diff <= -3)
			return Integer.toString(-diff) + "天前";

		if (diff > 0)
			return Integer.toString(diff) + "天后";
		if (diff < 0)
			return Integer.toString(-diff) + "天前";
		return "";
	}

	/**
	 * 日期转换为标准日期字符串
	 * 
	 * @param d
	 *            输入的日期
	 * @return "yyyy-MM-dd"格式的日期字符串
	 */
	public static String toDayStr(Date d) {
		return formatDate(d, "yyyy-MM-dd");
	}

	public static String toDayStr(Date d, String format) {
		return formatDate(d, format);
	}

	public static String toShortDayStr(Date d) {
		return formatDate(d, "M'月'd'日'");
	}

	public static String toYMDDayStr(Date d) {
		return formatDate(d, "yyyy'年'M'月'd'日'");
	}

	/**
	 * 得到日期的标准时间字符串
	 * 
	 * @param d
	 *            输入的日期
	 * @return "HH:mm:ss"格式的时间字符串
	 */
	public static String toTimeStr(Date d) {
		return formatDate(d, "HH:mm:ss");
	}

	public static String toShortTimeStr(Date d) {
		return formatDate(d, "HH:mm");
	}

	/**
	 * 
	 * @param d
	 *            日期参数
	 * @param format
	 *            日期格式
	 * @return 按format格式返回日期字符串
	 */
	public static String formatDate(final Date d, final String format) {
		if (d == null) {
			return "";
		}
		final SimpleDateFormat formater = new SimpleDateFormat(format);
		return formater.format(d);
	}

	/**
	 * 
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static String seconds(final Date beginDate, final Date endDate) {
		if (beginDate == null || endDate == null)
			return "";
		long diff = endDate.getTime() - beginDate.getTime();
		return Long.toString(diff / 1000);
	}

	/**
	 * 
	 * @param ss
	 * @return
	 */
	public static String getTime(int ss) {
		int hour = ss / 3600; // 小时
		int minute = ss % 3600 / 60; // 分钟
		int second = ss % 60; // 秒
		String b = "";
		String c = "";
		String d = "";
		if (second < 10)
			b = "0";
		if (minute < 10)
			c = "0";
		if (hour < 10)
			d = "0";
		return d + hour + ":" + c + minute + ":" + b + second;
	}

	/**
	 * 
	 * @param ss
	 * @return
	 */
	public static String getTimeText(int ss) {
		int hour = ss / 3600; // 小时
		int minute = ss % 3600 / 60; // 分钟
		int second = ss % 60; // 秒
		String b = "";
		String c = "";
		String d = "";
		if (second < 10)
			b = "0" + second;
		else
			b = String.valueOf(second);
		if (minute < 10)
			c = "0" + minute;
		else
			c = String.valueOf(minute);
		if (hour < 10)
			d = "0" + hour;
		else
			d = String.valueOf(hour);
		// return d+hour + ":" +c+minute + ":" + b+second;
		StringBuffer out = new StringBuffer();
		if (0 != hour) {
			out.append(String.format("%s小时", d));
		}
		if (0 != minute) {
			out.append(String.format("%s分钟", c));
		}
		if (0 != second) {
			out.append(String.format("%s秒", b));
		}
		return out.toString();
	}

	/**
	 * 输入秒数返回HH小时mm分钟
	 * 
	 * @param ss
	 * @return
	 */
	public static String getTimeHHmm(int ss) {
		int hour = ss / 3600; // 小时
		int minute = ss % 3600 / 60; // 分钟
		// int second = ss%60; //秒
		String mm = "";
		String hh = "";
		// if(second< 10) b = "0" + second; else b= String.valueOf(second);
		if (minute < 10)
			mm = "0" + minute;
		else
			mm = String.valueOf(minute);
		if (hour < 10)
			hh = "0" + hour;
		else
			hh = String.valueOf(hour);
		// return d+hour + ":" +c+minute + ":" + b+second;
		StringBuffer out = new StringBuffer();
		if (0 != hour) {
			out.append(String.format("%s小时", hh));
		}
		if (0 != minute) {
			out.append(String.format("%s分钟", mm));
		}
		/*
		 * if(0 != second){ out.append(String.format("%s秒", b)); }
		 */
		return out.toString();
	}

	/**
	 * 输入秒数返回DD天HH小时mm分
	 * 
	 * @param ss
	 * @return
	 */
	public static String getTimeDDHHmm(int ss) {
		int day = ss / (3600 * 24); // 天
		ss = ss % (3600 * 24);
		int hour = ss / 3600; // 小时
		ss = ss % 3600;
		int minute = ss / 60; // 分钟
		StringBuffer out = new StringBuffer();
		if (0 != day) {
			out.append(String.format("%s天", day));
		}
		if (0 != hour) {
			out.append(String.format("%s小时", hour));
		}
		if (0 != minute) {
			out.append(String.format("%s分", minute));
		}
		return out.toString();
	}

	public static void main(String[] args) {
		try {
			Date d = parseDate("2012-8-3 00:00:00");
			System.out.println(getTime(1789));
			System.out.println(printDiffDay(diffDays(d)));
			System.out.println(toFriendlyStr("2010-12-15 13:10:00"));
			System.out.println(toFriendlyStr("2010-6-3 13:10:00"));
			System.out.println(toFriendlyStr("2010-6-5 13:10:00"));
			System.out.println(toFriendlyStr("2012-6-5 13:10:00"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static int getDays(int year, int month) {
		int days = 0;
		if (month != 2) {
			switch (month) {
			case 1:
			case 3:
			case 5:
			case 7:
			case 8:
			case 10:
			case 12:
				days = 31;
				break;
			case 4:
			case 6:
			case 9:
			case 11:
				days = 30;
			}
		} else {
			if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
				days = 29;
			} else {
				days = 28;
			}
		}
		return days;
	}

	public static String getWeekDay(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int weekday = cal.get(Calendar.DAY_OF_WEEK);
		String days = "未知";
		switch (weekday) {
		case 1:
			days = "星期日";
			break;
		case 2:
			days = "星期一";
			break;
		case 3:
			days = "星期二";
			break;
		case 4:
			days = "星期三";
			break;
		case 5:
			days = "星期四";
			break;
		case 6:
			days = "星期五";
			break;
		case 7:
			days = "星期六";
			break;
		}
		return days;
	}

	public static int getWeekDayNum(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int weekday = cal.get(Calendar.DAY_OF_WEEK);
		int days = 0;
		switch (weekday) {
		case 1:
			days = 7;
			break;
		case 2:
			days = 1;
			break;
		case 3:
			days = 2;
			break;
		case 4:
			days = 3;
			break;
		case 5:
			days = 4;
			break;
		case 6:
			days = 5;
			break;
		case 7:
			days = 6;
			break;
		}
		return days;
	}

	public static String getFriendTime(Date date1) {
		String returnValue = "";
		Date date2 = new Date();
		long diff = (date2.getTime() - date1.getTime()) / 1000;
		long day_diff = (long) Math.floor(diff / 86400);
		long diffMonths = (long) Math.floor(day_diff / 31);
		long diffYears = (long) Math.floor(day_diff / 365);
		if (diff < 60) {
			return "刚才";
		} else if (diff < 120) {
			return "1分钟前";
		} else if (diff < 3600) {
			return (int) Math.floor(diff / 60) + "分钟前";
		} else if (diff < 7200) {
			return "1小时前";
		} else if (diff < 86400) {
			return (int) Math.floor(diff / 3600) + "小时前";
		} else if (day_diff == 1) {
			return "昨天";
		} else if (day_diff < 7) {
			return (int) day_diff + "天前";
		} else if (day_diff < 31) {
			return (int) Math.ceil(day_diff / 7) + "星期前";
		}
		if (diffYears >= 1) {
			return diffYears + "年前";
		} else if (diffYears < 1) {
			if (diffMonths == 1) {
				return "1月前";
			} else if (diffMonths >= 1 && diffMonths <= 12) {
				return (int) diffMonths + "月前";
			}
		}
		return returnValue;
	}

	/**
	 * 
	 * @param day
	 * @return
	 */
	public static Date getDay(final int day) {
		Date myDate = new Date();
		long myTime = (myDate.getTime() / 1000) + 60 * 60 * 24 * day;
		myDate.setTime(myTime * 1000);
		return myDate;
	}
}
