package com.ltchen.demo.dubbo.provider;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;

import com.ltchen.demo.dubbo.exception.HdfsRestApiException;
import com.ltchen.demo.util.http.HttpClientUtil;

/**
 * Hello world!
 *
 */
public class App {
	
    public static void main( String[] args ){
        System.out.println( "Hello World!" );
        Map headerMap = new HashMap<String,String>();
		headerMap.put("Accept", "application/json");
        String url = "http://192.168.0.121:50070/webhdfs/v1/tmp/LVS配置.txt?blocksize=134217728&op=create&user.name=ltchen&buffersize=8388608&overwrite=true";
        HttpResponse httpResponse = HttpClientUtil.doPut(url, "UTF-8", headerMap, "");
		String result = HttpClientUtil.parseHttpEntity(httpResponse.getEntity(), "UTF-8");
		if(httpResponse.getStatusLine().getStatusCode() == 307){
			System.out.println("Location:"+httpResponse.getFirstHeader("Location").getValue());
		}
		else{
			HdfsRestApiException e = new HdfsRestApiException("response code is:" + httpResponse.getStatusLine().getStatusCode() + 
					System.getProperty("line.separator") + result);
			e.printStackTrace();
		}
    }
}
