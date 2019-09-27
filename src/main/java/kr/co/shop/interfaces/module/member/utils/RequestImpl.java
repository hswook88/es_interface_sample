package kr.co.shop.interfaces.module.member.utils;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import kr.co.shop.common.util.UtilsText;
import kr.co.shop.interfaces.module.member.model.BaseParameterFactory;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @Desc : HttpRequest구현 클래스
 * @FileName : RequestImpl.java
 * @Project : shop.interfaces
 * @Date : 2019. 2. 14.
 * @Author : 백인천
 */
@Slf4j
@Data
public class RequestImpl implements Request {

	/**
	 * httpMethod 기본값(GET)
	 */
	private String method = "GET";

	/**
	 * 보내게될 URL
	 */
	private Url url = null;

	/**
	 * 파라매터
	 */
	private List<RequestParameter> params = null;

	private String jsonParams = null;

	private Map<String, String> headers;

	/**
	 * 기본문자셋
	 */
	private String charset = StandardCharsets.UTF_8.toString();

	/**
	 * Response Charset
	 */
	private String responseCharset;

	/**
	 * 생성자
	 *
	 * @param url
	 */
	public RequestImpl(Url url) {
		this.url = url;
	}

	/**
	 * 생성자
	 *
	 * @param url
	 * @param charset
	 */
	public RequestImpl(Url url, String charset) {
		this.charset = charset;
		this.url = url;
	}

	/**
	 * 생성자
	 *
	 * @param url
	 * @param timeout
	 */
	public RequestImpl(Url url, int timeout) {
		this.url = url;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	/**
	 * request 후 response를 담아 리턴
	 */
	public Response execute() {
		log.debug("Method - " + this.method);

		CloseableHttpClient httpclient = HttpClientBuilder.create().setUserAgent("HttpComponents/1.1").build();
		RequestConfig requestConfig = BaseParameterFactory.getDefaulParameter();

		ResponseImpl response = null;

		List<NameValuePair> nvps = null;

		if ("POST".equals(method)) {
			if (this.params != null) {
				nvps = new ArrayList<NameValuePair>();
				for (int i = 0; i < this.params.size(); i++) {
					RequestParameter parameter = params.get(i);
					nvps.add(new BasicNameValuePair(parameter.getName(), parameter.getValue()));
				}
			}
		}

		try {
			if (log.isDebugEnabled()) {
				log.debug(UtilsText.concat("URL=", this.url.toString()));
			}

			if ("GET".equals(method)) {
				HttpGet httpget = new HttpGet(this.url.toString());
				httpget.setConfig(requestConfig);
				if (headers != null) {
					Set<String> keySet = headers.keySet();
					for (String key : keySet) {
						httpget.setHeader(new BasicHeader(key, headers.get(key)));
					}
				}

				HttpResponse httpResponse = httpclient.execute(httpget);
				response = new ResponseImpl(httpResponse.getEntity(), httpResponse.getAllHeaders(),
						httpResponse.getStatusLine().getStatusCode(), this.responseCharset);
				response.setHeaders(httpResponse.getAllHeaders());
			} else {
				HttpPost httpPost = new HttpPost(this.url.toString());
				httpPost.setConfig(requestConfig);
				if (headers != null) {
					Set<String> keySet = headers.keySet();
					for (String key : keySet) {
						httpPost.setHeader(new BasicHeader(key, headers.get(key)));
					}
				}

				if (nvps != null) {
					httpPost.setEntity(new UrlEncodedFormEntity(nvps, charset));
				}

				if (jsonParams != null) {
					StringEntity entity = new StringEntity(jsonParams, charset);
					entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
					httpPost.setEntity(entity);
				}

				log.debug("Request Line", httpPost.getRequestLine());
				for (Header h : httpPost.getAllHeaders()) {
					log.debug(h.getName() + " : " + h.getValue());
				}

				HttpEntity e = httpPost.getEntity();
				log.debug(Util.getString(e.getContent()));

				HttpResponse httpResponse = httpclient.execute(httpPost);
				response = new ResponseImpl(httpResponse.getEntity(), httpResponse.getAllHeaders(),
						httpResponse.getStatusLine().getStatusCode(), this.responseCharset);
				response.setHeaders(httpResponse.getAllHeaders());
			}
		} catch (Exception e) {
			log.debug(e.getMessage());
		}

		log.debug("response >> " + response.toString());

		return response;
	}

	public Url getLinkUrl() {
		return this.url;
	}

	public void setGet() {
		this.method = GET;
	}

	public void setPost() {
		this.method = POST;
	}

}
