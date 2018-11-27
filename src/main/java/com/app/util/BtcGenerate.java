package com.app.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.bouncycastle.util.encoders.Base64;

import com.alibaba.fastjson.JSONObject;

public class BtcGenerate {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		JSONObject requestJson = new JSONObject();
		requestJson.put("jsonrpc", "2.0");
		requestJson.put("id", 1);
		requestJson.put("method", "generate");

		List<Object> params = new ArrayList<Object>();
		params.add(1);
		params.add(10000000);
		requestJson.put("params", params);

		String authorization = "btcuser" + ":" + "gGIeKaW0PADkQiyh";
		authorization = new String(Base64.encode(authorization.getBytes()));
		String basicAuth = "Basic " + authorization;

		try {
			while(true){
				String sendPostRequest = post("http://127.0.0.1:30088",basicAuth,requestJson.toString(), "UTF-8" );
				System.out.println(sendPostRequest);
				Thread.sleep(60000);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String post(String url,String baseAuth,String parameters, String encode) throws Exception {
		String response = null;
		InputStream bis = null;
        ByteArrayOutputStream bos = null;
        
		HttpURLConnection httpConnection = null;
		OutputStreamWriter outputStreamWriter = null;
		try {

			httpConnection = (HttpURLConnection) (new URL(url)).openConnection();

			httpConnection.setRequestMethod("POST");
			httpConnection.setRequestProperty("Content-Length",
					String.valueOf(parameters.length()));
			httpConnection.setRequestProperty("Content-Type",
					"application/json");
			httpConnection.setRequestProperty("Connection", "Keep-Alive");
			httpConnection.setConnectTimeout(10000);
			httpConnection.setDoOutput(true);
			httpConnection.setDoInput(true);
			
			if(baseAuth!=null){
				httpConnection.setRequestProperty("Authorization", baseAuth);
			}

			outputStreamWriter = new OutputStreamWriter(
					httpConnection.getOutputStream(),encode);
			outputStreamWriter.write(parameters);
			outputStreamWriter.flush();

			if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				bis = httpConnection.getInputStream();
                bos = new ByteArrayOutputStream();
                byte[] buffer = new byte[10240];
                int len = 0;
                while(-1 != (len = bis.read(buffer))){
                    bos.write(buffer,0,len);
                    bos.flush();
                }
				response = bos.toString(encode);
			}else{
				int code=httpConnection.getResponseCode();
				System.out.println(code);
			}
		}  catch (Exception e) {
			e.printStackTrace();
			int code=httpConnection.getResponseCode();

		} finally {
			if (outputStreamWriter != null) {
				try {
					outputStreamWriter.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (bis != null) {
				try {
					bis.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if(bos != null){
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
			if(httpConnection!=null)
				httpConnection.disconnect();
		}
		return response;

		
	}
}
