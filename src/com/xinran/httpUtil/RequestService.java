package com.xinran.httpUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hslf.blip.Bitmap;

public class RequestService {

	public Logger logger = Logger.getLogger(RequestService.class);
	public static Map<String,String> cookieMap = new HashMap<String,String>();

	public static final String TAG = "RequestService";
	public static final String SIGN_ERROR = "@@@ERROR@@@";
	public static final int OK = 200;
	public static Cookie extCookie = null;

	private static RequestService instance;

	private RequestService() {
	}

	public static RequestService getInstance() {
		if (instance == null) {
			instance = new RequestService();
		}
		return instance;
	}

	public native String doRequest(String ip, String req, int port);

	/**
	 * GET请求
	 */
	public String getRequest(List<BasicNameValuePair> params, String url,
			Map<String, String> header) throws Exception {

		String param = URLEncodedUtils.format(params, "UTF-8");

		HttpGet getMethod = new HttpGet(url + "?" + param);

		logger.info(TAG+",url:" + url + "?" + param);

		// 设置请求头
		for (Entry<String, String> str : header.entrySet()) {
			getMethod.addHeader(str.getKey(), str.getValue());
		}

		getMethod.getParams().setParameter(
				CoreConnectionPNames.CONNECTION_TIMEOUT, 4000);

		HttpContext localContext = new BasicHttpContext();
		CookieStore cookieStore = new BasicCookieStore();
		if (extCookie != null) {
			cookieStore.addCookie(extCookie);
		}
		localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);

		DefaultHttpClient httpClient = new DefaultHttpClient();
		httpClient.addRequestInterceptor(new HttpRequestInterceptor() {

			public void process(final HttpRequest request,
					final HttpContext context) throws HttpException,
					IOException {
				if (!request.containsHeader("Accept-Encoding")) {
					request.addHeader("Accept-Encoding", "gzip, deflate");
				}
				request.addHeader("Accept", "application/json");
			}

		});
		HttpResponse response = httpClient.execute(getMethod, localContext);
		String responseEntity = EntityUtils.toString(response.getEntity(),
				"utf-8");

		if (response.getStatusLine().getStatusCode() == OK) {

			List<Cookie> cookies = cookieStore.getCookies();
			if (!cookies.isEmpty()) {
				for (int i = cookies.size(); i > 0; i--) {
					Cookie cookie = cookies.get(i - 1);
					if (cookie.getName().equalsIgnoreCase(Constants.__AUTH__)) {
						// 使用一个常量来保存这个cookie，用于做session共享之用
						extCookie = cookie;

						String cookieString = cookie.getName() + "="
								+ cookie.getValue();
						if (cookieMap!= null) {
							cookieMap.put(Constants.SAVED_COOKIE, cookieString);
						}
					}
					logger.info(TAG+","+ cookie.getName() + ":" + cookie.getValue());
				}
			}

			logger.info(TAG+ ",response:" + responseEntity);
			return responseEntity;
		} else {
			if (responseEntity != null && responseEntity.length() > 0) {
				return SIGN_ERROR + responseEntity;
			} else {
				logger.info(TAG+",responseCode:"
						+ response.getStatusLine().getStatusCode());
				throw new Exception();
			}
		}
	}

	
	/**
	 * POST请求
	 */
	public String postRequest(List<BasicNameValuePair> nameValuePairs,
			String url, Map<String, String> header) throws Exception {

		String param = URLEncodedUtils.format(nameValuePairs, "UTF-8");

		HttpClient httpClient = new DefaultHttpClient();

		logger.info(TAG+ ",url:" + url + "?" + param);

		httpClient.getParams().setParameter(
				CoreConnectionPNames.CONNECTION_TIMEOUT, 20000);
		HttpContext localContext = new BasicHttpContext();
		CookieStore cookieStore = new BasicCookieStore();

		if (extCookie != null) {
			cookieStore.addCookie(extCookie);
		}

		localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);

		HttpPost httpPost = new HttpPost(url);
		logger.info(TAG+ ",url:" + url);

		for (Entry<String, String> map : header.entrySet()) {
			httpPost.addHeader(map.getKey(), map.getValue());
		}

		httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "utf-8"));

		HttpResponse response = httpClient.execute(httpPost, localContext);
		String responseEntity = EntityUtils.toString(response.getEntity(),
				"utf-8");

		if (response.getStatusLine().getStatusCode() == OK) {

			List<Cookie> cookies = cookieStore.getCookies();
			if (!cookies.isEmpty()) {
				for (int i = cookies.size(); i > 0; i--) {
					Cookie cookie = cookies.get(i - 1);
					if (cookie.getName().equalsIgnoreCase(Constants.__AUTH__)) {
						// 使用一个常量来保存这个cookie，用于做session共享之用
						extCookie = cookie;

						String cookieString = cookie.getName() + "="
								+ cookie.getValue();
						if (cookieMap!= null) {
							cookieMap.put(Constants.SAVED_COOKIE, cookieString);
						}
					}
					logger.info(TAG+","+cookie.getName() + ":" + cookie.getValue());
				}
			}

			logger.info(TAG+",response:" + responseEntity);
			return responseEntity;
		} else {
			if (responseEntity != null && responseEntity.length() > 0) {
				return SIGN_ERROR + responseEntity;
			} else {
				logger.info(TAG+",responseCode:"
						+ response.getStatusLine().getStatusCode());
				throw new Exception();
			}
		}
	}

	/**
	 * POST请求
	 */
	public String postRaw(List<BasicNameValuePair> nameValuePairs,
			String url, Map<String, String> header) throws Exception {

		String param = URLEncodedUtils.format(nameValuePairs, "UTF-8");

		HttpClient httpClient = new DefaultHttpClient();

		logger.info(TAG+ ",url:" + url + "?" + param);

		httpClient.getParams().setParameter(
				CoreConnectionPNames.CONNECTION_TIMEOUT, 20000);
		HttpContext localContext = new BasicHttpContext();
		CookieStore cookieStore = new BasicCookieStore();

		if (extCookie != null) {
			cookieStore.addCookie(extCookie);
		}

		localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);

		HttpPost httpPost = new HttpPost(url);
		logger.info(TAG+ ",url:" + url);

		for (Entry<String, String> map : header.entrySet()) {
			httpPost.addHeader(map.getKey(), map.getValue());
		}

		httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "utf-8"));

		HttpResponse response = httpClient.execute(httpPost, localContext);
		String responseEntity = EntityUtils.toString(response.getEntity(),
				"utf-8");

		if (response.getStatusLine().getStatusCode() == OK) {

			List<Cookie> cookies = cookieStore.getCookies();
			if (!cookies.isEmpty()) {
				for (int i = cookies.size(); i > 0; i--) {
					Cookie cookie = cookies.get(i - 1);
					if (cookie.getName().equalsIgnoreCase(Constants.__AUTH__)) {
						// 使用一个常量来保存这个cookie，用于做session共享之用
						extCookie = cookie;

						String cookieString = cookie.getName() + "="
								+ cookie.getValue();
						if (cookieMap!= null) {
							cookieMap.put(Constants.SAVED_COOKIE, cookieString);
						}
					}
					logger.info(TAG+","+cookie.getName() + ":" + cookie.getValue());
				}
			}

			logger.info(TAG+",response:" + responseEntity);
			return responseEntity;
		} else {
			if (responseEntity != null && responseEntity.length() > 0) {
				return SIGN_ERROR + responseEntity;
			} else {
				logger.info(TAG+",responseCode:"
						+ response.getStatusLine().getStatusCode());
				throw new Exception();
			}
		}
	}

	
	/**
	 * PUT请求
	 */
	public String putRequest(List<BasicNameValuePair> nameValuePairs,
			String url, Map<String, String> header) throws Exception {

		String param = URLEncodedUtils.format(nameValuePairs, "UTF-8");

		HttpClient httpClient = new DefaultHttpClient();

		logger.info(TAG+",url:" + url + "?" + param);

		httpClient.getParams().setParameter(
				CoreConnectionPNames.CONNECTION_TIMEOUT, 20000);
		HttpContext localContext = new BasicHttpContext();
		CookieStore cookieStore = new BasicCookieStore();

		if (extCookie != null) {
			cookieStore.addCookie(extCookie);
		}

		localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);

		HttpPut httpPut = new HttpPut(url);
		logger.info(TAG+ ",url:" + url);

		for (Entry<String, String> map : header.entrySet()) {
			httpPut.addHeader(map.getKey(), map.getValue());
		}

		httpPut.setEntity(new UrlEncodedFormEntity(nameValuePairs, "utf-8"));

		HttpResponse response = httpClient.execute(httpPut, localContext);
		String responseEntity = EntityUtils.toString(response.getEntity(),
				"utf-8");

		if (response.getStatusLine().getStatusCode() == OK) {
			List<Cookie> cookies = cookieStore.getCookies();
			if (!cookies.isEmpty()) {
				for (int i = cookies.size(); i > 0; i--) {
					Cookie cookie = cookies.get(i - 1);
					if (cookie.getName().equalsIgnoreCase(Constants.__AUTH__)) {
						// 使用一个常量来保存这个cookie，用于做session共享之用
						extCookie = cookie;

						String cookieString = cookie.getName() + "="
								+ cookie.getValue();
						if (cookieMap!= null) {
							cookieMap.put(Constants.SAVED_COOKIE, cookieString);
						}
					}
					logger.info(TAG+","+cookie.getName() + ":" + cookie.getValue());
				}
			}

			logger.info(TAG+",response:" + responseEntity);
			return responseEntity;
		} else {
			if (responseEntity != null && responseEntity.length() > 0) {
				return SIGN_ERROR + responseEntity;
			} else {
				logger.info(TAG+",responseCode:"
						+ response.getStatusLine().getStatusCode());
				throw new Exception();
			}
		}
	}

	public Bitmap loadImage(String url) throws IOException {
		Bitmap bitmap = null;
		HttpClient client = new DefaultHttpClient();
		HttpResponse response = null;
		InputStream inputStream = null;
		try {
			HttpContext localContext = new BasicHttpContext();
			CookieStore cookieStore = new BasicCookieStore();

			if (RequestService.extCookie != null) {
				cookieStore.addCookie(RequestService.extCookie);
			}

			localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);

			response = client.execute(new HttpGet(url), localContext);
			HttpEntity entity = response.getEntity();

			if (response.getStatusLine().getStatusCode() == RequestService.OK) {

				List<Cookie> cookies = cookieStore.getCookies();
				if (!cookies.isEmpty()) {
					for (int i = cookies.size(); i > 0; i--) {
						Cookie cookie = cookies.get(i - 1);
						if (cookie.getName().equalsIgnoreCase(
								Constants.__AUTH__)) {
							RequestService.extCookie = cookie;
						}
					}
				}
			}

			inputStream = entity.getContent();
			
			/*if (inputStream != null) {
				try {
					BitmapFactory.Options opt = new BitmapFactory.Options();
					opt.inPreferredConfig = Bitmap.RGB_565;
					opt.inPurgeable = true;
					opt.inInputShareable = true;

					bitmap = BitmapFactory.decodeStream(inputStream, null, opt);
					return bitmap;
				} catch (Throwable ex) {
					ex.printStackTrace();
					if (ex instanceof OutOfMemoryError)
						return null;
				}

			}*/
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null)
				inputStream.close();
		}
		return bitmap;
	}

}
