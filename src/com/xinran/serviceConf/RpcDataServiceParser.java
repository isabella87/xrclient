package com.xinran.serviceConf;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;

import com.xinran.httpUtil.XrHttpClient;

public class RpcDataServiceParser {

	public Logger logger = Logger.getLogger(RpcDataServiceParser.class);

	private static RpcDataServiceParser ServiceDataParser = null;

	public static RpcDataServiceParser getInstance() {
		if (ServiceDataParser == null) {
			ServiceDataParser = new RpcDataServiceParser();
		}
		return ServiceDataParser;
	}

	public byte[] queryByteData(final String submitType, String loginUrl,
			Map<String, String> paramMap) {
		byte[] bytes = null;
		try {
			bytes = XrHttpClient.getInstance().sendByteRequest(submitType, loginUrl,
					initParams(paramMap));
		} catch (UnsupportedEncodingException e) {
			logger.debug(e);
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return bytes;
	}

	public String queryData(final boolean isLoginReq, final String submitType, String url,
			Map<String, String> paramMap) {
		String str = null;
		try {
			str = XrHttpClient.getInstance().sendRequest(isLoginReq, submitType, url,
					initParams(paramMap));
			logger.debug(str);
		} catch (UnsupportedEncodingException e) {
			logger.debug(e);
			e.printStackTrace();
		} catch (URISyntaxException e) {
			logger.debug(e);
			e.printStackTrace();
		}
		return str;
	}

	/**
	 * 
	 * @param url
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, String>> queryDataWithTowLevel(final boolean isLoginReq,
			final String submitType, String url, Map<String, String> paramMap) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		try {
			String str = XrHttpClient.getInstance().sendRequest(isLoginReq, submitType, url,
					initParams(paramMap));
			if (str.startsWith("[") && str.endsWith("]")) {
				str = str.substring(1, str.length() - 1);
			}
			if (str.length() > 2) {
				str = str.substring(1, str.length() - 1);
				str = str.replace("{", "");
				list = new ArrayList<Map<String, String>>();
				for (String s : str.split("},")) {
					Map<String, String> map = new HashMap<String, String>();
					for (String ss : s.split(",")) {
						if (ss.contains(":")) {
							String[] item = ss.split(":");
							String key = item[0].trim();
							if (key.startsWith("\"") && key.endsWith("\"")) {
								key = key.substring(1, key.length() - 1);
							}
							String value = item[1].trim();
							if (value.startsWith("\"") && value.endsWith("\"")) {
								if (value.length() > 2) {
									value = value.substring(1, value.length() - 1);
								} else {
									value = "";
								}
							}
							map.put(key, value);
						}
					}
					list.add(map);
				}
			}
		} catch (UnsupportedEncodingException | URISyntaxException e) {
			logger.debug(e);
			e.printStackTrace();
		}

		return list;
	}

	/**
	 * 初始化服务参数
	 * 
	 * @param paramMap
	 * @return
	 */
	private BasicNameValuePair[] initParams(Map<String, String> paramMap) {
		List<BasicNameValuePair> paramList = new ArrayList<BasicNameValuePair>();
		for (String key : paramMap.keySet()) {
			paramList.add(new BasicNameValuePair(key, paramMap.get(key)));
		}
		BasicNameValuePair[] parms = (BasicNameValuePair[]) paramList
				.toArray(new BasicNameValuePair[paramList.size()]);
		return parms;
	}

	public static void main(String[] args) {
		String ss = "\"department\":\"销售四部\"";

		if (ss.contains(":")) {
			String[] item = ss.split(":");
			String key = item[0];
			if (key.startsWith("\"") && key.endsWith("\"")) {
				key = key.substring(1, key.length() - 1);
			}
			String value = item[1];
			if (value.startsWith("\"") && value.endsWith("\"")) {
				if (value.length() > 2) {
					value = value.substring(1, value.length() - 1);
				} else {
					value = "";
				}
			}
			System.out.println("key:" + key);
			System.out.println("value:" + value);
		}
	}
}
