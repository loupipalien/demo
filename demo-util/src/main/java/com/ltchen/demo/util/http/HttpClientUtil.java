package com.ltchen.demo.util.http;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 1、since 4.3 不再使用 DefaultHttpClient
 * 2、UrlEncodedFormEntity()的形式比较单一，只能是普通的键值对，局限性相对较大;而StringEntity()的形式比较自由，
 * 只要是字符串放进去，不论格式都可以。 3、当请求体为Json字符串时发送请求时，需指定 Content
 * type：httpost.setHeader("Content-type", "application/json"); 否则默认使用 Content
 * type 'text/plain;charset=UTF-8'。
 */
@SuppressWarnings("deprecation")
public class HttpClientUtil {

	private static Logger LOG = LoggerFactory.getLogger(HttpClientUtil.class);

	// 默认字符集编码
	public static final String DEFAULT_CHARSET = "UTF-8";
	//默认的mime类型
	public static final String DEFAULT_MIMETYPE = "application/x-www-form-urlencoded";

	/**
	 * 私有化默认构造器
	 */
	private HttpClientUtil() {}

	/**
	 * 将url和请求参数paramMap拼接为一个新的url
	 * 
	 * @param url
	 * @param paramMap
	 * @return
	 */
	public static String spliceUrl(String url, Map<String, String> paramMap) {
		StringBuffer sb = new StringBuffer(url);
		if (paramMap != null && !paramMap.isEmpty()) {
			if (sb.indexOf("?") > 0) {
				sb.append("&");
			} else {
				sb.append("?");
			}
			for (String key : paramMap.keySet()) {
				sb.append(key).append("=").append(paramMap.get(key)).append("&");
			}
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}

	/**
	 * 为HttpRequestBase设置请求头headerMap
	 * 
	 * @param httpRequestBase
	 * @param headerMap
	 * @return
	 */
	private static HttpRequestBase setHeaders(HttpRequestBase httpRequestBase, Map<String, String> headerMap) {
		if (httpRequestBase != null && headerMap != null && !headerMap.isEmpty()) {
			for (String key : headerMap.keySet()) {
				httpRequestBase.setHeader(key, headerMap.get(key));
			}
		}
		return httpRequestBase;
	}

	/**
	 * 为HttpEntityEnclosingRequestBase设置请求体entityMap,
	 * 默认编码集为utf-8,可指定字符编码集charset,默认mime类型为application/x-www-form-urlencoded,可指定mime类型mimeType
	 * @param heerb
	 * @param entityMap
	 * @param charset
	 * @return
	 */
	private static HttpEntityEnclosingRequestBase setEntities(HttpEntityEnclosingRequestBase heerb,
			Map<String, String> entityMap, String charset,String mimeType) {
		charset = (charset == null) ? DEFAULT_CHARSET : charset;
		mimeType = (mimeType == null) ? DEFAULT_MIMETYPE: mimeType;
		StringEntity entity = null;
		List<NameValuePair> list = null;
		if (heerb != null && entityMap != null && !entityMap.isEmpty()) {
			list = new ArrayList<NameValuePair>();
			Iterator<Entry<String, String>> iterator = entityMap.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<String, String> elem = (Entry<String, String>) iterator.next();
				list.add(new BasicNameValuePair(elem.getKey(), elem.getValue()));
			}
			if (list.size() > 0) {
				entity = new StringEntity(URLEncodedUtils.format(list,charset),
				        ContentType.create(mimeType, charset));
				heerb.setEntity(entity);
			}
		}
		return heerb;
	}

	/**
	 * 为HttpEntityEnclosingRequestBase设置请求体entityStr,默认编码集为utf-8,可指定字符编码集charset
	 * @param heerb
	 * @param entityStr
	 * @param charset
	 * @return
	 */
	private static HttpEntityEnclosingRequestBase setEntities(HttpEntityEnclosingRequestBase heerb, String entityStr,
			String charset) {
		charset = (charset == null) ? DEFAULT_CHARSET : charset;
		StringEntity se = new StringEntity(entityStr, charset);
		heerb.setEntity(se);
		return heerb;
	}
	
	/**
	 * 为HttpEntityEnclosingRequestBase设置请求体file,默认contentType为application/octet-stream
	 * @param heerb
	 * @param file
	 * @return
	 */
	private static HttpEntityEnclosingRequestBase setEntities(HttpEntityEnclosingRequestBase heerb, File file) {
		FileEntity fe = new FileEntity(file, ContentType.DEFAULT_BINARY);
		heerb.setEntity(fe);
		return heerb;
	}

	/**
	 * 为HttpEntityEnclosingRequestBase设置请求体in,默认contentType为application/octet-stream
	 * @param heerb
	 * @param file
	 * @return
	 */
	private static HttpEntityEnclosingRequestBase setEntities(HttpEntityEnclosingRequestBase heerb, InputStream in) {
		InputStreamEntity ise = new InputStreamEntity(in, ContentType.DEFAULT_BINARY);
		heerb.setEntity(ise);
		return heerb;
	}
	
	/**
	 * 为HttpEntityEnclosingRequestBase设置请求体paramMap和fileMap,默认编码集为utf-8,可指定字符编码集charset,mime类型mimeType
	 * 默认编码集为utf-8,可指定字符编码集charset,默认mime类型为application/x-www-form-urlencoded,可指定mime类型mimeType
	 * @param heerb
	 * @param paramMap
	 * @param fileMap
	 * @param charset
	 * @param mimeType
	 * @return
	 */
	private static HttpEntityEnclosingRequestBase setEntities(HttpEntityEnclosingRequestBase heerb,
			Map<String,String> paramMap,Map<String, File> fileMap,String charset,String mimeType) {
		HttpEntity entity = null;
		List<NameValuePair> list = null;
		if (heerb != null) {
			if(paramMap != null && !paramMap.isEmpty()){
				list = new ArrayList<NameValuePair>();
				Iterator<Entry<String, String>> iterator = paramMap.entrySet().iterator();
				while (iterator.hasNext()) {
					Entry<String, String> elem = (Entry<String, String>) iterator.next();
					list.add(new BasicNameValuePair(elem.getKey(), elem.getValue()));
				}
			}
			entity = buildMultipartEntity(list, fileMap, charset, mimeType);
			heerb.setEntity(entity);
		}
		return heerb;
	}
	
	/**
	 * 构造MultipartEntity
	 * @param params
	 * @param fileMap
	 * @param charset
	 * @param mimeType
	 * @return
	 */
	private static HttpEntity buildMultipartEntity(List<NameValuePair> params, final Map<String, File> fileMap,String charset,String mimeType) {
		charset = (charset == null) ? DEFAULT_CHARSET : charset;
		mimeType = (mimeType == null) ? DEFAULT_MIMETYPE: mimeType;
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		// 如果有SocketTimeoutException等情况，可修改这个枚举
		builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE); 
		//不要用这个，会导致服务端接收不到参数
		// builder.setCharset(Charset.forName("UTF-8"));
		if (params != null && params.size() > 0) {
			for (NameValuePair p : params) {
				builder.addTextBody(p.getName(), p.getValue(), ContentType.TEXT_PLAIN.withCharset(charset));
			}
		}
		if (fileMap != null && fileMap.size() > 0) {
			Set<Entry<String, File>> entries = fileMap.entrySet();
			for (Entry<String, File> entry : entries) {
				// builder.addPart(entry.getKey(), new FileBody(entry.getValue()));
				//builder.addBinaryBody(entry.getKey(), entry.getValue());
				builder.addBinaryBody(entry.getKey(), entry.getValue(),ContentType.create(mimeType, charset), entry.getKey());
			}
		}
		return builder.build();
	}
	
	/**
	 * 使用closeableHttpClient执行httpRequestBase请求,返回以charset编码的响应体
	 * 
	 * @param closeableHttpClient
	 * @param httpRequestBase
	 * @param charset
	 * @return
	 */
	private static String execute(CloseableHttpClient closeableHttpClient, HttpRequestBase httpRequestBase,
			String charset) {
		CloseableHttpResponse closeableHttpResponse = null;
		String entityStr = null;
		try {
			closeableHttpResponse = closeableHttpClient.execute(httpRequestBase);
			if (closeableHttpResponse != null) {
				// 响应码以2(200,201,202,203,204,205,206)开头，即认为成功
				if ((closeableHttpResponse.getStatusLine().getStatusCode() + "").startsWith("2")) {
					HttpEntity httpEntity = new BufferedHttpEntity(closeableHttpResponse.getEntity());
					entityStr = EntityUtils.toString(httpEntity, charset);
				} else {
					LOG.info(closeableHttpResponse.getStatusLine().toString());
					LOG.info(closeableHttpResponse.getAllHeaders().toString());
					LOG.info(closeableHttpResponse.getEntity().toString());
				}
			}
		} catch (IOException e) {
			LOG.error("IOException was catched:", e);
		} finally {
			if (closeableHttpResponse != null) {
				try {
					closeableHttpResponse.close();
				} catch (IOException e) {
					LOG.error("CloseableHttpResponse close failed:", e);
				}
			}
			if (closeableHttpClient != null) {
				try {
					closeableHttpClient.close();
				} catch (IOException e) {
					LOG.error("CloseableHttpClient close failed:", e);
				}
			}
		}
		return entityStr;
	}

	/**
	 * http get 方法(不建议使用,HttpClient将不会被关闭,会造成资源泄露)
	 * 
	 * @param url
	 * @param paramMap
	 * @param headerMap
	 * @return
	 */
	@SuppressWarnings("resource")
	public static HttpResponse doGet(String url, Map<String, String> paramMap, Map<String, String> headerMap) {
		LOG.info("http get return HttpResponse,the url is: " + url);
		HttpClient httpClient = new DefaultHttpClient();
		HttpResponse httpResponse = null;
		String newUrl = spliceUrl(url, paramMap);
		// 获取一个HttpGet对象
		HttpGet httpGet = new HttpGet(newUrl);
		// 设置请求头
		httpGet = (HttpGet) setHeaders(httpGet, headerMap);
		try {
			httpResponse = httpClient.execute(httpGet);
		} catch (ClientProtocolException e) {
			LOG.error("ClientProtocolException:", e);
		} catch (IOException e) {
			LOG.error("IOException:", e);
		}
		return httpResponse;
	}

	/**
	 * http get 方法
	 * 
	 * @param url
	 * @param headerMap
	 * @param paramMap
	 * @param charset
	 * @return
	 */
	public static String doGet(String url, String charset, Map<String, String> paramMap,
			Map<String, String> headerMap) {
		LOG.info("http get return String,the url is: " + url);
		CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
		// 拼接url参数
		String newUrl = spliceUrl(url, paramMap);
		// 获取一个HttpGet对象
		HttpGet httpGet = new HttpGet(newUrl);
		// 设置请求头
		httpGet = (HttpGet) setHeaders(httpGet, headerMap);
		// 获得响应体
		return execute(closeableHttpClient, httpGet, charset);
	}

	/**
	 * http post 方法(不建议使用,HttpClient将不会被关闭,会造成资源泄露)
	 * 
	 * @param url
	 * @param charset
	 * @param headerMap
	 * @param entityMap
	 * @return
	 */
	@SuppressWarnings("resource")
	public static HttpResponse doPost(String url, String charset, Map<String, String> headerMap,
			Map<String, String> entityMap,String mimeType) {
		LOG.info("http post return HttpResponse,the url is: " + url);
		HttpClient httpClient = new DefaultHttpClient();
		HttpResponse httpResponse = null;
		// 获取一个HttpPost对象
		HttpPost httpPost = new HttpPost(url);
		// 设置请求头
		httpPost = (HttpPost) setHeaders(httpPost, headerMap);
		// 设置请求体
		httpPost = (HttpPost) setEntities(httpPost, entityMap, charset, mimeType);
		try {
			httpResponse = httpClient.execute(httpPost);
		} catch (ClientProtocolException e) {
			LOG.error("ClientProtocolException:", e);
		} catch (IOException e) {
			LOG.error("IOException:", e);
		}
		return httpResponse;
	}

	/**
	 * http post 方法
	 * 
	 * @param url
	 * @param headerMap
	 * @param entityMap
	 * @param charset
	 * @return
	 */
	public static String doPost(String url, Map<String, String> headerMap, Map<String, String> entityMap,
			String charset,String mimeType) {
		LOG.info("http post return String,the url is: " + url);
		CloseableHttpClient closeableHttpClient = HttpClientBuilder.create().build();
		// 获取一个HttpPost对象
		HttpPost httpPost = new HttpPost(url);
		// 设置请求头
		httpPost = (HttpPost) setHeaders(httpPost, headerMap);
		// 设置请求体
		httpPost = (HttpPost) setEntities(httpPost, entityMap, charset, mimeType);
		// 获得响应体
		return execute(closeableHttpClient, httpPost, charset);
	}

	/**
	 * http post 方法(不建议使用,HttpClient将不会被关闭,会造成资源泄露)
	 * 
	 * @param url
	 * @param charset
	 * @param headerMap
	 * @param entityStr
	 * @return
	 */
	@SuppressWarnings("resource")
	public static HttpResponse doPost(String url, String charset, Map<String, String> headerMap, String entityStr) {
		LOG.info("http post return HttpResponse,the url is: " + url);
		HttpClient httpClient = new DefaultHttpClient();
		HttpResponse httpResponse = null;
		// 获取一个HttpPost对象
		HttpPost httpPost = new HttpPost(url);
		// 设置请求头
		httpPost = (HttpPost) setHeaders(httpPost, headerMap);
		// 设置请求体
		httpPost = (HttpPost) setEntities(httpPost, entityStr, charset);
		try {
			httpResponse = httpClient.execute(httpPost);
		} catch (ClientProtocolException e) {
			LOG.error("ClientProtocolException:", e);
		} catch (IOException e) {
			LOG.error("IOException:", e);
		}
		return httpResponse;
	}

	/**
	 * http post 方法
	 * 
	 * @param url
	 * @param headerMap
	 * @param entityMap
	 * @param charset
	 * @return
	 */
	public static String doPost(String url, Map<String, String> headerMap, String entityStr, String charset) {
		LOG.info("http post return String,the url is: " + url);
		CloseableHttpClient closeableHttpClient = HttpClientBuilder.create().build();
		// 获取一个HttpPost对象
		HttpPost httpPost = new HttpPost(url);
		// 设置请求头
		httpPost = (HttpPost) setHeaders(httpPost, headerMap);
		// 设置请求体
		httpPost = (HttpPost) setEntities(httpPost, entityStr, charset);
		// 获得响应体
		return execute(closeableHttpClient, httpPost, charset);
	}

	/**
	 * http put 方法(不建议使用,HttpClient将不会被关闭,会造成资源泄露)
	 * 
	 * @param url
	 * @param charset
	 * @param headerMap
	 * @param entityMap
	 * @return
	 */
	@SuppressWarnings("resource")
	public static HttpResponse doPut(String url, String charset, Map<String, String> headerMap,
			Map<String, String> entityMap,String mimeType) {
		LOG.info("http put return HttpResponse,the url is: " + url);
		HttpClient httpClient = new DefaultHttpClient();
		HttpResponse httpResponse = null;
		// 获取一个HttpPost对象
		HttpPut httpPut = new HttpPut(url);
		// 设置请求头
		httpPut = (HttpPut) setHeaders(httpPut, headerMap);
		// 设置请求体
		httpPut = (HttpPut) setEntities(httpPut, entityMap, charset, mimeType);
		try {
			httpResponse = httpClient.execute(httpPut);
		} catch (ClientProtocolException e) {
			LOG.error("ClientProtocolException:", e);
		} catch (IOException e) {
			LOG.error("IOException:", e);
		}
		return httpResponse;
	}

	/**
	 * http put 方法
	 * 
	 * @param url
	 * @param headerMap
	 * @param entityMap
	 * @param charset
	 * @return
	 */
	public static String doPut(String url, Map<String, String> headerMap, Map<String, String> entityMap,
			String charset,String mimeType) {
		LOG.info("http put return String,the url is: " + url);
		CloseableHttpClient closeableHttpClient = HttpClientBuilder.create().build();
		// 获取一个HttpPost对象
		HttpPut httpPut = new HttpPut(url);
		// 设置请求头
		httpPut = (HttpPut) setHeaders(httpPut, headerMap);
		// 设置请求体
		httpPut = (HttpPut) setEntities(httpPut, entityMap, charset, mimeType);
		// 获得响应体
		return execute(closeableHttpClient, httpPut, charset);
	}

	/**
	 * http put 方法(不建议使用,HttpClient将不会被关闭,会造成资源泄露)
	 * 
	 * @param url
	 * @param charset
	 * @param headerMap
	 * @param entityStr
	 * @return
	 */
	@SuppressWarnings("resource")
	public static HttpResponse doPut(String url, String charset, Map<String, String> headerMap, String entityStr) {
		LOG.info("http put return HttpResponse,the url is: " + url);
		HttpClient httpClient = new DefaultHttpClient();
		HttpResponse httpResponse = null;
		// 获取一个HttpPost对象
		HttpPut httpPut = new HttpPut(url);
		// 设置请求头
		httpPut = (HttpPut) setHeaders(httpPut, headerMap);
		// 设置请求体
		httpPut = (HttpPut) setEntities(httpPut, entityStr, charset);
		try {
			httpResponse = httpClient.execute(httpPut);
		} catch (ClientProtocolException e) {
			LOG.error("ClientProtocolException:", e);
		} catch (IOException e) {
			LOG.error("IOException:", e);
		}
		return httpResponse;
	}

	/**
	 * http put 方法
	 * 
	 * @param url
	 * @param charset
	 * @param headerMap
	 * @param entityStr
	 * @return
	 */
	public static String doPut(String url, Map<String, String> headerMap, String entityStr, String charset) {
		LOG.info("http put return String,the url is: " + url);
		CloseableHttpClient closeableHttpClient = HttpClientBuilder.create().build();
		// 获取一个HttpPost对象
		HttpPut httpPut = new HttpPut(url);
		// 设置请求头
		httpPut = (HttpPut) setHeaders(httpPut, headerMap);
		// 设置请求体
		httpPut = (HttpPut) setEntities(httpPut, entityStr, charset);
		// 获得响应体
		return execute(closeableHttpClient, httpPut, charset);
	}

	/**
	 * http delete 方法(不建议使用,HttpClient将不会被关闭,会造成资源泄露)
	 * 
	 * @param url
	 * @param headerMap
	 * @param paramMap
	 * @param charset
	 * @return
	 */
	@SuppressWarnings("resource")
	public static HttpResponse doDelete(String url, Map<String, String> headerMap, Map<String, String> paramMap) {
		LOG.info("http delete return HttpResponse,the url is: " + url);
		HttpClient httpClient = new DefaultHttpClient();
		HttpResponse httpResponse = null;
		// 拼接url
		String newUrl = spliceUrl(url, paramMap);
		// 获取一个HttpGet对象
		HttpDelete httpDelete = new HttpDelete(newUrl);
		// 设置请求头
		httpDelete = (HttpDelete) setHeaders(httpDelete, headerMap);
		try {
			httpResponse = httpClient.execute(httpDelete);
		} catch (ClientProtocolException e) {
			LOG.error("ClientProtocolException:", e);
		} catch (IOException e) {
			LOG.error("IOException:", e);
		}
		return httpResponse;
	}

	/**
	 * http delete 方法
	 * 
	 * @param url
	 * @param headerMap
	 * @param paramMap
	 * @param charset
	 * @return
	 */
	public static String doDelete(String url, Map<String, String> headerMap, Map<String, String> paramMap,
			String charset) {
		LOG.info("http delete return String,the url is: " + url);
		CloseableHttpClient closeableHttpClient = HttpClientBuilder.create().build();
		// 拼接url
		String newUrl = spliceUrl(url, paramMap);
		// 获取一个HttpGet对象
		HttpDelete httpDelete = new HttpDelete(newUrl);
		// 设置请求头
		httpDelete = (HttpDelete) setHeaders(httpDelete, headerMap);
		// 获得响应体
		return execute(closeableHttpClient, httpDelete, charset);
	}

	/**
	 * 将HttpEntity用charset转换为字符串
	 * 
	 * @param httpEntity
	 * @param charset
	 * @return String
	 */
	public static String parseHttpEntity(HttpEntity httpEntity, String charset) {
		charset = (charset == null) ? DEFAULT_CHARSET : charset;
		String entityStr = null;
		if(httpEntity == null){
			return entityStr;
		}
		try {
			entityStr = EntityUtils.toString(httpEntity, charset);
		} catch (ParseException e) {
			LOG.error("ParseException :", e);
		} catch (IOException e) {
			LOG.error("IOException :", e);
		}
		LOG.info("parse HttpEntity,the result is: " + entityStr);
		return entityStr;
	}

	@SuppressWarnings("resource")
	public static HttpResponse filePut(String url,Map<String,String> headerMap, File file){
		LOG.info("http put return HttpResponse,the url is: " + url);
		HttpClient httpClient = new DefaultHttpClient();
		HttpResponse httpResponse = null;
		// 获取一个HttpPost对象
		HttpPut httpPut = new HttpPut(url);
		// 设置请求头
		httpPut = (HttpPut) setHeaders(httpPut, headerMap);
		// 设置请求体
		httpPut = (HttpPut) setEntities(httpPut, file);
		try {
			httpResponse = httpClient.execute(httpPut);
		} catch (ClientProtocolException e) {
			LOG.error("ClientProtocolException:", e);
		} catch (IOException e) {
			LOG.error("IOException:", e);
		}
		return httpResponse;
	}
	
	@SuppressWarnings("resource")
	public static HttpResponse filePut(String url,Map<String,String> headerMap, InputStream in){
		LOG.info("http put return HttpResponse,the url is: " + url);
		HttpClient httpClient = new DefaultHttpClient();
		HttpResponse httpResponse = null;
		// 获取一个HttpPost对象
		HttpPut httpPut = new HttpPut(url);
		// 设置请求头
		httpPut = (HttpPut) setHeaders(httpPut, headerMap);
		// 设置请求体
		httpPut = (HttpPut) setEntities(httpPut, in);
		try {
			httpResponse = httpClient.execute(httpPut);
		} catch (ClientProtocolException e) {
			LOG.error("ClientProtocolException:", e);
		} catch (IOException e) {
			LOG.error("IOException:", e);
		}
		return httpResponse;
	}
	
	
	@SuppressWarnings("resource")
	public static HttpResponse filePut(String url,Map<String,String> headerMap,Map<String,String> paramMap,
			Map<String, File> fileMap,String charset,String mimeType){
		LOG.info("http put return HttpResponse,the url is: " + url);
		HttpClient httpClient = new DefaultHttpClient();
		HttpResponse httpResponse = null;
		// 获取一个HttpPost对象
		HttpPut httpPut = new HttpPut(url);
		// 设置请求头
		httpPut = (HttpPut) setHeaders(httpPut, headerMap);
		// 设置请求体
		httpPut = (HttpPut) setEntities(httpPut, paramMap, fileMap, charset,mimeType);
		try {
			httpResponse = httpClient.execute(httpPut);
		} catch (ClientProtocolException e) {
			LOG.error("ClientProtocolException:", e);
		} catch (IOException e) {
			LOG.error("IOException:", e);
		}
		return httpResponse;
	}
	
	public static String filePost(String url, Map<String, String> map, Map<String, File> fileMap, String charset, String mimeType) {
		HttpClient httpClient = null;
		HttpPost httpPost = null;
		String result = null;
		try {
			httpClient = new DefaultHttpClient();
			httpPost = new HttpPost(url);
			// 设置参数
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			Iterator<Entry<String, String>> iterator = map.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<String, String> elem = (Entry<String, String>) iterator.next();
				params.add(new BasicNameValuePair(elem.getKey(), elem.getValue()));
			}
			HttpEntity entity = buildMultipartEntity(params, fileMap, charset, mimeType);
			httpPost.setEntity(entity);
			HttpResponse response = httpClient.execute(httpPost);
			if (response != null) {
				HttpEntity resEntity = response.getEntity();
				if (resEntity != null) {
					result = EntityUtils.toString(resEntity, charset);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			;
		}
		return result;
	}

}
