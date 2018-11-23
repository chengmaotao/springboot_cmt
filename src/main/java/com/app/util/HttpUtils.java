package com.app.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpUtils {
	private static final int CONNECTION_TIMEOUT = 150000;
	private static final int SOCKET_TIMEOUT = 600000;
	private static final Logger logger = LoggerFactory.getLogger(HttpUtils.class);

	/**
	 * 不推荐
	 * @param body
	 *            json对象(传递的参数： 接收方 @RequestBody String str 这种方式来接收
	 *            ；request.getParameter 取不到值)
	 * @param url
	 * @param encoding
	 * @return
	 * @throws Exception
	 */
	public static String sendPostRequest(String body, String url, String encoding) throws Exception {

		StringEntity httpEntity = new StringEntity(body, encoding);

		// 创建默认的httpClient实例.
		CloseableHttpClient httpclient = HttpClients.createDefault();
		logger.trace("HttpClient方法创建！");
		// 创建httppost
		HttpPost httppost = new HttpPost(url);

		logger.trace("Post方法创建！");

		CloseableHttpResponse response = null;
		String resMes = null;
		try {
			logger.info("HTTP请求数据：{}", EntityUtils.toString(httpEntity, encoding));

			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(SOCKET_TIMEOUT).setConnectTimeout(CONNECTION_TIMEOUT).build();
			httppost.setConfig(requestConfig);
			httppost.setHeader("Content-Type", "application/json");
			httppost.setEntity(httpEntity);
			logger.info("HTTP请求URL:{}", httppost.getURI());
			response = httpclient.execute(httppost);

			HttpEntity entity = response.getEntity();
			if (entity != null) {
				resMes = EntityUtils.toString(entity, encoding);
				logger.info("HTTP返回数据:{}", resMes);
			}

		} finally {
			if (response != null) {
				response.close();
			}

			// 关闭连接,释放资源

			if (httpclient != null) {
				httpclient.close();
			}

		}

		return resMes;
	}

	/**
	 * 推荐这种写法 （request.getParameter  和   @RequestBody String str 这两种方式接收 都能接收到）
	 * @param params  
	 * @param url
	 * @param encoding
	 * @return
	 * @throws Exception
	 */
	public static String sendHttpPostRequest(Map<String, String> params, String url, String encoding) throws Exception {

		// 创建默认的httpClient实例.
		CloseableHttpClient httpclient = HttpClients.createDefault();

		// 创建httppost
		HttpPost httppost = new HttpPost(url);

		ArrayList<NameValuePair> reqParams = null;
		if (params != null && !params.isEmpty()) {
			reqParams = new ArrayList<NameValuePair>();
			for (Map.Entry<String, String> e : params.entrySet()) {
				reqParams.add(new BasicNameValuePair(e.getKey(), e.getValue()));
			}
		}

		CloseableHttpResponse response = null;
		String resMes = null;
		try {

			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(SOCKET_TIMEOUT).setConnectTimeout(CONNECTION_TIMEOUT).build();
			httppost.setConfig(requestConfig);
			if (reqParams != null) {
				httppost.setEntity(new UrlEncodedFormEntity(reqParams, encoding));
			}
			response = httpclient.execute(httppost);

			HttpEntity entity = response.getEntity();
			if (entity != null) {
				resMes = EntityUtils.toString(entity, encoding);
				logger.info("HTTP返回数据:{}", resMes);
			}

		} finally {
			if (response != null) {
				response.close();
			}

			// 关闭连接,释放资源

			if (httpclient != null) {
				httpclient.close();
			}

		}

		return resMes;
	}

	/**
	 * 
	 * @param url
	 *            请求路径
	 * @return
	 * @throws Exception
	 */
	public static String sendGetRequest(String url, String encoding) throws Exception {
		CloseableHttpClient httpclient = HttpClients.createDefault();

		HttpGet request = new HttpGet(url);
		request.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:39.0) Gecko/20100101 Firefox/39.0");

		CloseableHttpResponse response = httpclient.execute(request);
		String resMes = null;
		try {
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				resMes = EntityUtils.toString(entity, encoding);
				logger.info("HTTP返回数据:{}", resMes);
			}
		} finally {
			response.close();

			if (httpclient != null) {
				httpclient.close();
			}
		}
		return resMes;
	}

	public static void main(String[] args) {
		/*
		 * try { String sendGetRequest =
		 * sendGetRequest("http://www.baidu.com","UTF-8");
		 * System.out.println(sendGetRequest); } catch (Exception e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); }
		 */

		// String body =
		// "{\"actionCode\":\"100001\",\"language\":\"zh\",\"packet\":{\"name\":\"成\",\"mobile\":\"131\"}}";
		
		
		
/*		String body = "{\"actionCode\":\"100001\",\"language\":\"zh\"}";
		try {
			String sendPostRequest = sendPostRequest(body, "http://localhost:9999/1.0/endpoint", "UTF-8");
			System.out.println(sendPostRequest);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("actionCode", "100001");
		params.put("language", "zh");
		params.put("packet", "{\"name\":\"成\",\"mobile\":\"13121019741\"}");
		
		try {
			String sendHttpPostRequest = sendHttpPostRequest(params,"http://localhost:8888/1.0/endpoint","UTF-8");
			System.out.println(sendHttpPostRequest);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
