package com.app.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

public class HttpUtils {
	private static final int CONNECTION_TIMEOUT = 150000;
	private static final int SOCKET_TIMEOUT = 600000;
	private static final Logger logger = LoggerFactory.getLogger(HttpUtils.class);

	/**
	 * 不推荐
	 * 
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

		// 创建httppost
		HttpPost httppost = new HttpPost(url);

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
	 * 推荐这种写法 （request.getParameter 和 @RequestBody String str 这两种方式接收 都能接收到）
	 * 
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
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(SOCKET_TIMEOUT).setConnectTimeout(CONNECTION_TIMEOUT).build();
		request.setConfig(requestConfig);
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
	
	/**
	 * post 上传文件
	 * @param url
	 * @param encoding
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static void fileUpload(String url, String encoding) throws ClientProtocolException, IOException{
	    CloseableHttpClient httpClient = HttpClientBuilder.create().build();
	    CloseableHttpResponse httpResponse = null;
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(SOCKET_TIMEOUT).setConnectTimeout(CONNECTION_TIMEOUT).build();
	    HttpPost httpPost = new HttpPost(url);
	    httpPost.setConfig(requestConfig);
	    MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
	    multipartEntityBuilder.setCharset(Charset.forName(encoding));
	          
	         
	    File file = new File("D:\\c.jpg");
	          
	  
	    //当设置了setSocketTimeout参数后，以下代码上传PDF不能成功，将setSocketTimeout参数去掉后此可以上传成功。上传图片则没有个限制
	    //multipartEntityBuilder.addBinaryBody("file",file,ContentType.create("application/octet-stream"),"abd.pdf");
	    multipartEntityBuilder.addBinaryBody("file",file);
	   

	    multipartEntityBuilder.addTextBody("comment", "其他的参数");

	    HttpEntity httpEntity = multipartEntityBuilder.build();
	    httpPost.setEntity(httpEntity);
	         
	    httpResponse = httpClient.execute(httpPost); // 对应后台接收： public String fileUpload(MultipartFile file, String comment) 
	    
	    
	    HttpEntity responseEntity = httpResponse.getEntity();
	    int statusCode= httpResponse.getStatusLine().getStatusCode();
	    if(statusCode == 200){
	    	String resMes = EntityUtils.toString(responseEntity, encoding);
	    	logger.info("上传文件返回:" + resMes);
	    }
	         
	    httpClient.close();
	    if(httpResponse!=null){
	        httpResponse.close();
	    }
	     
	}
	
	
	/**
     * @desc ：微信上传素材的请求方法
     *  
     * @param requestUrl  微信上传临时素材的接口url
     * @param file    要上传的文件
     * @return String  上传成功后，微信服务器返回的消息
     */
    public static String httpRequest(String requestUrl, File file) {  
        StringBuffer buffer = new StringBuffer();  
    
        try{
            //1.建立连接
            URL url = new URL(requestUrl);
            HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();  //打开链接
            
            //1.1输入输出设置
            httpUrlConn.setDoInput(true);
            httpUrlConn.setDoOutput(true);
            httpUrlConn.setUseCaches(false); // post方式不能使用缓存
            //1.2设置请求头信息
            httpUrlConn.setRequestProperty("Connection", "Keep-Alive");
            httpUrlConn.setRequestProperty("Charset", "UTF-8");
            //1.3设置边界
            String BOUNDARY = "----------" + System.currentTimeMillis();
            httpUrlConn.setRequestProperty("Content-Type","multipart/form-data; boundary="+ BOUNDARY);

            // 请求正文信息
            // 第一部分：
            //2.将文件头输出到微信服务器
            StringBuilder sb = new StringBuilder();
            sb.append("--"); // 必须多两道线
            sb.append(BOUNDARY);
            sb.append("\r\n");
            sb.append("Content-Disposition: form-data;name=\"media\";filelength=\"" + file.length()
                    + "\";filename=\""+ file.getName() + "\"\r\n");
            sb.append("Content-Type:application/octet-stream\r\n\r\n");
            byte[] head = sb.toString().getBytes("utf-8");
            // 获得输出流
            OutputStream outputStream = new DataOutputStream(httpUrlConn.getOutputStream());
            // 将表头写入输出流中：输出表头
            outputStream.write(head);

            //3.将文件正文部分输出到微信服务器
            // 把文件以流文件的方式 写入到微信服务器中
            DataInputStream in = new DataInputStream(new FileInputStream(file));
            int bytes = 0;
            byte[] bufferOut = new byte[1024];
            while ((bytes = in.read(bufferOut)) != -1) {
                outputStream.write(bufferOut, 0, bytes);
            }
            in.close();
            //4.将结尾部分输出到微信服务器
            byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线
            outputStream.write(foot);
            outputStream.flush();
            outputStream.close();

            
            //5.将微信服务器返回的输入流转换成字符串  
            InputStream inputStream = httpUrlConn.getInputStream();  
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");  
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);  
            
            String str = null;  
            while ((str = bufferedReader.readLine()) != null) {  
                buffer.append(str);  
            }  
            
            bufferedReader.close();  
            inputStreamReader.close();  
            // 释放资源  
            inputStream.close();  
            inputStream = null;  
            httpUrlConn.disconnect();  

            
        } catch (IOException e) {
            System.out.println("发送POST请求出现异常！" + e);
            e.printStackTrace();
        } 
        return buffer.toString();
    }

	public static void main(String[] args) throws Exception {

		// 担保中心获取图片验证码
		
		String url = "https://test.wecredit.io/m/white_operation";
		//String url = "http://127.0.0.1:9010/m/white_operation";
		
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("code", "4");
		jsonObject.put("order", "order201812242222");
		jsonObject.put("mobile", "99999999999");
		jsonObject.put("accountAddress", "CTj37NcVWPb4ToknGnLYCBtqVEWQ2tHiKm5a6b6f1f55284ac195e11a23026b60e7");
		jsonObject.put("country", "86");
		jsonObject.put("amount", new BigDecimal("160"));
		jsonObject.put("date", "19930230171636");
		
		// url = "http://localhost:8888/index";
		for(int i = 0 ;i < 20000; i++){
			if(i % 2 == 0){
				jsonObject.put("code", "4");
				jsonObject.put("order", "order_csgjnihao" + i);
			}else{
				jsonObject.put("code", "100");
				jsonObject.put("order", "order_flbwohao" + i);
			}
			
			String sendHttpPostRequest = sendPostRequest(jsonObject.toJSONString(),url,"UTF-8");
			System.out.println(sendHttpPostRequest);
		}
		
		//String sendHttpPostRequest = sendPostRequest(jsonObject.toJSONString(),url,"UTF-8");
		
		
		
		
	}
}
