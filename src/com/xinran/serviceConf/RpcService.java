package com.xinran.serviceConf;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;

import com.xinran.httpUtil.RequestService;
import com.xinran.httpUtil.XrHttpClient;

public class RpcService {

	public Logger logger = Logger.getLogger(RpcService.class);

	private static class Urls {
		/**
		 * "http://www.banbank.com" "http://192.168.11.114"
		 */
//		static final String ROOT_URL = "http://localhost";
		static final String ROOT_URL = "http://www.mengchengkeji.com";
		static final String INFO_CREATE_URL = ROOT_URL + "/xrsrv/info";
		static final String INFO_QUERY_URL = ROOT_URL + "/xrsrv/info/all/bg";
		static final String PRODUCT_UPLOAD_URL = ROOT_URL
				+ "/xrsrv/major-product/upload";
		static final String PRODUCT_SINGLE_UPLOAD_URL = ROOT_URL
				+ "/xrsrv/major-product/upload/single";
		static final String PRODUCT_QUERY_URL = ROOT_URL
				+ "/xrsrv/major-product/list/bg";
		
		static final String WORKER_UPLOAD_URL = ROOT_URL
				+ "/xrsrv/maintenance-worker/upload";
		static final String WORKER_SINGLE_UPLOAD_URL = ROOT_URL
				+ "/xrsrv/maintenance-worker/upload/single";
		static final String WORKER_QUERY_URL = ROOT_URL
				+ "/xrsrv/maintenance-worker/list/bg";
		
		static final String CAPTCHA_IMAGE_URL = ROOT_URL
				+ "/xrsrv/security/captcha-image";
		static final String LOGIN_URL = ROOT_URL + "/xrsrv/security/signin";
		static final String CUR_USER_URL = ROOT_URL + "/xrsrv/security/user";

	}

	/*
	 * public List<Map<String, String>> getClientMgrTreeDatas(Map<String,
	 * String> paramMap) { return
	 * RpcDataServiceParser.getInstance().queryDataWithTowLevel(false, "GET",
	 * Urls.INFO_CREATE_URL, paramMap); }
	 * 
	 * public List<Map<String, String>> getInvestors(Map<String, String>
	 * paramMap) { return
	 * RpcDataServiceParser.getInstance().queryDataWithTowLevel(false, "GET",
	 * Urls.PRODUCT_UPLOAD_URL, paramMap); }
	 */

	public String queryMajorProductListForBg(Map<String, String> paramMap) {
		Map<String, String> header = new HashMap<String, String>();
		header.put("Accept-Encoding", "gzip, deflate");
		header.put("Accept", "application/json");
		header.put("ContentType",
				"application/x-www-form-urlencoded; charset=UTF-8");
		String jsonStr = null;
		try {
			jsonStr = RequestService.getInstance().getRequest(
					initParamsToList(paramMap), Urls.PRODUCT_QUERY_URL, header);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonStr;
		// return
		// RpcDataServiceParser.getInstance().queryDataWithTowLevel(false,
		// "GET", Urls.PRODUCT_QUERY_URL,
		// paramMap);
	}

	public String getCmNotices(Map<String, String> paramMap) {

		Map<String, String> header = new HashMap<String, String>();
		header.put("Accept-Encoding", "gzip, deflate");
		header.put("Accept", "application/json");
		header.put("ContentType",
				"application/x-www-form-urlencoded; charset=UTF-8");
		String jsonStr = null;
		try {
			jsonStr = RequestService.getInstance().getRequest(
					initParamsToList(paramMap), Urls.INFO_QUERY_URL, header);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonStr;

		/*
		 * return
		 * RpcDataServiceParser.getInstance().queryDataWithTowLevel(false,
		 * "GET", Urls.INFO_QUERY_URL, paramMap);
		 */
	}

	public String uploadProduct(Map<String, String> paramMap) {
		Map<String, String> header = new HashMap<String, String>();
		header.put("Accept-Encoding", "gzip, deflate");
		header.put("Accept", "application/json");
		header.put("ContentType",
				"application/x-www-form-urlencoded; charset=UTF-8");
		String jsonStr = null;
		try {
			jsonStr = RequestService.getInstance()
					.putRequest(initParamsToList(paramMap),
							Urls.PRODUCT_UPLOAD_URL, header);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonStr;

		// return RpcDataServiceParser.getInstance().queryData(false, "PUT",
		// Urls.PRODUCT_UPLOAD_URL, paramMap);
	}

	public String uploadSingleProduct(Map<String, String> paramMap) {
		Map<String, String> header = new HashMap<String, String>();
		header.put("Accept-Encoding", "gzip, deflate");
		header.put("Accept", "application/json");
		header.put("ContentType",
				"application/x-www-form-urlencoded; charset=UTF-8");
		String jsonStr = null;
		try {
			jsonStr = RequestService.getInstance().putRequest(
					initParamsToList(paramMap), Urls.PRODUCT_SINGLE_UPLOAD_URL,
					header);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonStr;

		// return RpcDataServiceParser.getInstance().queryData(false, "PUT",
		// Urls.PRODUCT_SINGLE_UPLOAD_URL, paramMap);
	}

	public String uploadWorker(Map<String, String> paramMap) {
		Map<String, String> header = new HashMap<String, String>();
		header.put("Accept-Encoding", "gzip, deflate");
		header.put("Accept", "application/json");
		header.put("ContentType",
				"application/x-www-form-urlencoded; charset=UTF-8");
		String jsonStr = null;
		try {
			jsonStr = RequestService.getInstance()
					.putRequest(initParamsToList(paramMap),
							Urls.WORKER_UPLOAD_URL, header);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonStr;

	}
	
	public String uploadSingleWorker(Map<String, String> paramMap) {
		Map<String, String> header = new HashMap<String, String>();
		header.put("Accept-Encoding", "gzip, deflate");
		header.put("Accept", "application/json");
		header.put("ContentType",
				"application/x-www-form-urlencoded; charset=UTF-8");
		String jsonStr = null;
		try {
			jsonStr = RequestService.getInstance().putRequest(
					initParamsToList(paramMap), Urls.WORKER_SINGLE_UPLOAD_URL,
					header);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonStr;

	}
	
	public String queryWorkerListForBg(Map<String, String> paramMap) {
		Map<String, String> header = new HashMap<String, String>();
		header.put("Accept-Encoding", "gzip, deflate");
		header.put("Accept", "application/json");
		header.put("ContentType",
				"application/x-www-form-urlencoded; charset=UTF-8");
		String jsonStr = null;
		try {
			jsonStr = RequestService.getInstance().getRequest(
					initParamsToList(paramMap), Urls.WORKER_QUERY_URL, header);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonStr;
	}
	
	public String login(Map<String, String> paramMap) {
		Map<String, String> header = new HashMap<String, String>();
		header.put("Accept-Encoding", "gzip, deflate");
		header.put("Accept", "application/json");
		header.put("ContentType",
				"application/x-www-form-urlencoded; charset=UTF-8");
		header.put(
				"Cookie",
				XrHttpClient.__COOKIE_VALUE__ == null ? XrHttpClient.__TEMP__COOKIE_VALUE__
						: XrHttpClient.__COOKIE_VALUE__);
		String jsonStr = null;
		try {
			jsonStr = RequestService.getInstance().postRequest(
					initParamsToList(paramMap), Urls.LOGIN_URL, header);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonStr;
		// return RpcDataServiceParser.getInstance().queryData(true, "POST",
		// Urls.LOGIN_URL, paramMap);
	}

	public String getCurUser(Map<String, String> paramMap) {
		Map<String, String> header = new HashMap<String, String>();
		header.put("Accept-Encoding", "gzip, deflate");
		header.put("Accept", "application/json");
		header.put("ContentType",
				"application/x-www-form-urlencoded; charset=UTF-8");
		String jsonStr = null;
		try {
			jsonStr = RequestService.getInstance().getRequest(
					initParamsToList(paramMap), Urls.CUR_USER_URL, header);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonStr;

		// return RpcDataServiceParser.getInstance().queryData(false, "GET",
		// Urls.CUR_USER_URL,
		// paramMap);
	}

	public byte[] getCaptchaImage(Map<String, String> paramMap) {
		return RpcDataServiceParser.getInstance().queryByteData("GET",
				Urls.CAPTCHA_IMAGE_URL, paramMap);
	}

	/**
	 * 初始化服务参数
	 * 
	 * @param paramMap
	 * @return
	 */
	private List<BasicNameValuePair> initParamsToList(
			Map<String, String> paramMap) {
		List<BasicNameValuePair> paramList = new ArrayList<BasicNameValuePair>();
		for (String key : paramMap.keySet()) {
			paramList.add(new BasicNameValuePair(key, paramMap.get(key)));
		}
		return paramList;
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException,
			URISyntaxException {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("user-name", "admin");
		paramMap.put("password", "12345678");
		new RpcService().login(paramMap);
		// List<Map<String, String>> list = new
		// RpcService().getClientMgrTreeDatas(paramMap);
		// System.out.println("ok###"+list);
	}

}
