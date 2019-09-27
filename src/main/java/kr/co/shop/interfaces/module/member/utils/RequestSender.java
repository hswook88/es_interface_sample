package kr.co.shop.interfaces.module.member.utils;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpStatus;

import kr.co.shop.common.util.UtilsText;
import lombok.extern.slf4j.Slf4j;

/**
 * @Desc : 리퀘스트를 보내고 해당결과를 저장하는 클래스
 * @FileName : RequestSender.java
 * @Project : shop.interfaces
 * @Date : 2019. 2. 14.
 * @Author : 백인천
 */
@Slf4j
public class RequestSender {
	/**
	 * 호스트
	 */
	private String host;

	/**
	 * 패스
	 */
	private String path;

	/**
	 * 파라매터
	 */
	private final List<RequestParameter> parameters = new ArrayList<RequestParameter>();

	private String jsonParams;

	private final Map<String, String> headers = new HashMap<String, String>();

	/**
	 * response
	 */
	private Response response;

	/**
	 * 포트
	 */
	private int port = Url.DEFAULT_PORT;

	/**
	 * 스키마(프로토콜)
	 */
	private String schema = Url.DEFAULT_PROTOCOL;

	/**
	 * HttpMethod
	 */
	private String method = Request.GET;

	/**
	 * 문자셋
	 */
	private String charset = StandardCharsets.UTF_8.toString();

	/**
	 * Response Charset
	 *
	 * 문자열을 속이고 념겨주는 문제 리스판스
	 */
	private String responseCharset;

	public RequestSender(String uri) {
		Url url = new Url(uri);
		this.host = url.getHost();
		this.port = url.getPort();
		this.schema = url.getProtocol();
		this.path = url.getPath();
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void addParameter(RequestParameter parameter) {
		this.parameters.add(parameter);
	}

	public void addParameter(String key, String value) {
		this.parameters.add(new RequestParameter(key, value));
	}

	public void addParameter(List<RequestParameter> params) {
		for (RequestParameter param : params) {
			addParameter(param);
		}
	}

	public void addParameter(String jsonParams) {
		this.jsonParams = jsonParams;
	}

	public void addHeader(String key, String value) {
		this.headers.put(key, value);
	}

	/**
	 * <pre>
	 * 세션서버로 리퀘스트를 날린다.
	 * </pre>
	 *
	 * @throws HttpRequestException
	 */
	public void send() {
		if (Request.GET.equals(method)) {
			sendGetMethod();
		}
		if (Request.POST.equals(method)) {
			sendPostMethod();
		}
	}

	/**
	 * <pre>
	 * Post방식으로 리퀘스트를 날림
	 * </pre>
	 *
	 * @throws HttpRequestException
	 */
	private void sendPostMethod() {
		Url url = new Url(this.createUrl());
		Request request = new RequestImpl(url, this.charset);
		request.setPost();
		request.setHeaders(headers);
		request.setParams(parameters);
		request.setJsonParams(jsonParams);
		request.setResponseCharset(this.responseCharset);
		this.response = request.execute();
		if (response.getStatus() != HttpStatus.SC_OK) {
			log.error("Bad Request", response.getStatus());
		}
	}

	private String createUrl() {
		StringBuilder buffer = new StringBuilder(4096);
		buffer.append(this.schema);
		buffer.append("://");
		buffer.append(this.host);
		if (this.port != 80) {
			buffer.append(":");
			buffer.append(this.port);
		}

		if (this.path != null) {
			buffer.append(this.path);
		}

		return buffer.toString();
	}

	/**
	 * <pre>
	 * GET방식으로 리퀘스트를 날림
	 * </pre>
	 *
	 * @throws HttpRequestException
	 */
	private void sendGetMethod() {
		String urlString = this.createUrl();
		if (this.parameters.size() > 0) {
			urlString += "?";
			for (int i = 0; i < parameters.size(); i++) {
				RequestParameter p = parameters.get(i);
				urlString += p.toString();
				if (i + 1 < parameters.size()) {
					urlString += "&";
				}
			}
		}
		/*
		 * if (log.isDebugEnabled()) { log.debug(urlString); }
		 */

		Url url = new Url(urlString);
		Request request = new RequestImpl(url, this.charset);
		request.setHeaders(headers);
		request.setResponseCharset(this.responseCharset);
		this.response = request.execute();
		if (response.getStatus() != HttpStatus.SC_OK) {
			log.debug(UtilsText.concat("Bad Request=", String.valueOf(response.getStatus())));
		}
	}

	public String getResponseBody() {
		if (response == null) {
			return null;
		}

		return response.getResponseBody();
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getStatus() {
		if (response == null) {
			return 0;
		}

		return response.getStatus();
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String getResponseCharset() {
		return responseCharset;
	}

	public void setResponseCharset(String responseCharset) {
		this.responseCharset = responseCharset;
	}

	public Response getResponse() {
		return response;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public InputStream getInputStream() {
		return this.response.getInputStream();
	}

	public byte[] getBytes() {
		return this.response.getBytes();
	}
}