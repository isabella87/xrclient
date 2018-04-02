package com.xinran.httpUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.message.BasicNameValuePair;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class HttpUtils {
	
	/** 设置参数 */
	public static List<BasicNameValuePair> setParams(
			Map<String, String> paramsMap) {
		List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
		BasicNameValuePair bnv = null;

		for (Entry<String, String> a : paramsMap.entrySet()) {
			bnv = new BasicNameValuePair(a.getKey(), a.getValue());
			params.add(bnv);
		}
		return params;
	}
	
	public static org.json.JSONObject jsonToObj2(String jsonContent) throws org.json.JSONException{
		return new org.json.JSONObject(jsonContent);
	}
	
   /* public static void synCookies(Context context, String url, String cookies, boolean isLogin) {  
        CookieSyncManager.createInstance(context);  
        CookieManager cookieManager = CookieManager.getInstance();  
        cookieManager.setAcceptCookie(true);  
//        cookieManager.removeSessionCookie();  
        cookieManager.removeAllCookie();
        cookieManager.setCookie(url, cookies);
        CookieSyncManager.getInstance().sync();  
    }  */

    /**
	 * 将JSON字符串转换为字典
	 * 
	 * @param json
	 *            JSON字符串
	 * @param map
	 *            字典, 不包含子包
	 * @param subpacks
	 *            包含子包的字典
	 * 
	 * @throws NullPointerException
	 *             如果参数{@code map}是{@code null}。
	 */
	public static void parseJson(final String json, final java.util.Map<String, String> map,
			final Collection<java.util.Map<String, String>> subpacks) {
		if (map == null) {
			throw new NullPointerException("map");
		}
		if (!StringUtils.isBlank(json)) {
			final JSONObject jo = JSON.parseObject(json);
			for (final Map.Entry<String, Object> entry : jo.entrySet()) {
				final Object v = entry.getValue();
				if (!"SUBPACKS".equals(entry.getKey())) {
					// 字典
					map.put(entry.getKey(), v == null ? "" : String.valueOf(v));
				} else {
					// 子包列表
					if (subpacks == null) {
						continue;
					}

					map.put("SUBPACKS", "");
					final JSONArray ja = jo.getJSONArray("SUBPACKS");
					for (int i = 0; i < ja.size(); ++i) {
						final Map<String, String> subpack = new TreeMap<String, String>();
						final JSONObject jao = ja.getJSONObject(i);
						for (final Map.Entry<String, Object> jae : jao.entrySet()) {
							final Object ev = jae.getValue();
							subpack.put(jae.getKey(), ev == null ? "" : ev.toString());
						}
						subpacks.add(subpack);
					}
				}
			}
		}
	}
}

