package com.xinran.httpUtil;


public class Constants {
	public static final int UPDATE_NONEED = 0;
	public static final int UPDATE_CLIENT = 1;
	public static final int GET_UPDATEINFO_ERROR = 2;
	public static final int DOWN_ERROR = 3;
	public static final int GET_PORTAL_ENTS = 4;
	public static final int COUNTDOWN_PORTAL_ENTS = 5;
	public static final int NETWORK_ERROR = 6;
	public static final int LOGIN_SUCCESS = 7;
	public static final int LOGIN_FAILED = 8;
	public static final int LOGOUT_SUCCESS  = 9;
	public static final int REGISTER_ALREADY  = 10;
	public static final int REGISTER_NOT_YET  = 11;
	public static final int MOBILE_CODE_VALID  = 12;
	public static final int MOBILE_CODE_INVALID  = 13;
	public static final int REGISTER_SUCCESS  = 14;
	public static final int REGISTER_FAILED  = 15;
	public static final int SEND_MOBILE_CODE_SUCCESS  = 16;
	public static final int SEND_MOBILE_CODE_FAILED  = 17;
	public static final int GET_PORTAL_BHBS = 18;
	public static final int GET_PORTAL_CAS = 19;
	public static final int GET_ACC = 20;
	public static final int GET_ACC_SURVEY = 21;
	public static final int GET_PERSONAL_INFO = 22;
	public static final int GET_ACC_INCOME = 23;
	public static final int RESULT_BACK_INDEX = 24;
	public static final int GET_ACC_INCOME_TOTAL_OF_INVEST = 25;
	public static final int GET_ACC_INCOME_TOTAL_OF_REPAY = 26;
	public static final int GET_ACC_INCOME_TOTAL_OF_EXPENDITURE = 27;
	public static final int GET_ACC_INCOME_TOTAL_OF_BONUS = 28;
	public static final int GET_ACC_INCOME_TOTAL_OF_RECHARGE = 29;
	public static final int GET_ACC_INCOME_TOTAL_OF_WITHDRAW = 30;
	public static final int GET_INVEST_ENTS = 31;
	public static final int GET_INVEST_BHB = 32;
	public static final int GET_INVEST_CA = 33;
	public static final int RESULT_TO_LOGIN = 34;
	public static final int GET_PRJ_ENT_DETAIL = 35;
	public static final int CA_APPLY_SUCCESS  = 36;
	public static final int CA_APPLY_FAILED  = 37;
	public static final int RESULT_RESTART_SELF = 38;
	public static final int CA_CANCEL_SUCCESS  = 39;
	public static final int CA_CANCEL_FAILED  = 40;
	public static final int GET_PRJ_BHB_DETAIL = 41;
	public static final int RESULT_INVEST_SUCCESS = 42;
	public static final int GET_CRYPT_MOBILE_SUCCESS = 43;
	public static final int GET_INVEST_ENT_DETAIL = 44;
	public static final int GET_NOTIFICATION = 45;
	public static final int GET_UNREAD_MESSAGE_SUCCESS = 46;
	public static final int RESULT_SHOW_MSG_SUCCESS = 47;
	public static final int GET_UNREAD_MESSAGE_COUNT_SUCCESS = 48;
	public static final int RESULT_WITHDRAW_SUCCESS = 49;
	public static final int GET_CP_DATA_SUCCESS = 50;
	public static final int GET_BALANCE_SUCCESS = 51;
	public static final int JX_OPEN_ACCOUNT_SUCCESS = 52;
	public static final int JX_BIND_CARD_SUCCESS = 53;
	public static final int JX_SET_TRADE_PWD_SUCCESS = 54;
	public static final int GET_JXPAY_INFO_SUCCESS = 55;
	public static final int RESULT_RECHARGE_SUCCESS = 56;
	public static final int RESULT_MONEY_SHORT = 57;
	public static final int JX_OPEN_ACCOUNT_FAILED = 58;
	public static final int JX_BIND_CARD_FAILED = 59;
	public static final int RESULT_TO_JX = 60;
	public static final int RESULT_RECHARGE_FAILED = 61;
	public static final int AUTO_BANNER = 62;
	
	public static final int JX_STATUS_NOT_OPEN = 1001;
	public static final int JX_STATUS_NOT_BIND_CARD = 1002;
	public static final int JX_STATUS_NO_PWD = 1003;
	public static final int JX_STATUS_PASS = 1004;
	
	public static final String __AUTH__ = "__auth__";
	public static final String domainPath = "midsrv/";
	public static final double const_latitude = 31.3132700000;
	public static final double const_longitude = 121.5190160000;
	public static final String forwardSlash = "%2F";
	
	public static final String url = "www.banbank.com";

	public static final String accsrvUrl = "http://www.banbank.com/accsrv/";
	public static final String p2psrvUrl = "http://www.banbank.com/p2psrv/";
	public static final String mobileUrl = "http://www.banbank.com/m/";
	
/*	public static final String accsrvUrl = "http://192.168.11.114:8888/accsrv/";
	public static final String p2psrvUrl = "http://192.168.11.114:8888/p2psrv/";
	public static final String mobileUrl = "http://192.168.11.114:8888/m/";*/
	
	/*public static final String accsrvUrl = "http://192.168.11.11:8082/accsrv/";
	public static final String p2psrvUrl = "http://192.168.11.11:8082/p2psrv/";
	public static final String mobileUrl = "http://192.168.11.11:8082/m/";*/
	
	public static final String SERVICE_FAILURE = "{info:" + "网络异常" + "}";
	public static final String SP_NAME = "banhuitong";
	public static final int CALL_BACK_SUCCESS = 200;
	public static final int CALL_BACK_FAILURE = 100;
	public static final String USER_NAME = "username";// 用户名
	public static final String USER_PASS = "password";// 登录密码
	public static final String MEMORY_USERNAME = "memory_username";
	public static final String IS_NOT_FIRST_IN = "Y";
	public static final String PATTERN_MOBILE = "0?1[3|4|5|7|8][0-9]\\d{8}";
	public static final String PATTERN_LETTER_NUMBER = "[a-zA-Z0-9]*"; 
	public static final String ACTION_LOG_OUT = "_logout_";
	public static final String ACTION_LOG_IN = "_login_";
	public static final String ACTION_START_PUSH_SERVICE = "ACTION_START_PUSH_SERVICE";
	public static final String ACTION_START_BADGE_SERVICE = "ACTION_START_BADGE_SERVICE";
	public static final String MAIN_PAGE_A = "MAIN_PAGE_A";
	public static final String MAIN_PAGE_B = "MAIN_PAGE_B";
	public static final String MAIN_PAGE_C = "MAIN_PAGE_C";
	public static final String MAIN_PAGE_D = "MAIN_PAGE_D";
	public static final String SAVED_COOKIE = "SAVED_COOKIE";
	public static final String AM_ID = "AM_ID";
	public static final String GESTURE_LOCK = "gesture_lock";
	
}
