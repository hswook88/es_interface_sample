package kr.co.shop.interfaces.module.member.utils;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.entity.ContentType;
import org.apache.http.util.EntityUtils;

import kr.co.shop.interfaces.module.member.config.MembershipConfig;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @Desc : Response구현
 * @FileName : ResponseImpl.java
 * @Project : shop.interfaces
 * @Date : 2019. 2. 14.
 * @Author : 백인천
 */
@Slf4j
@Data
public class ResponseImpl implements Response {

	/**
	 * 문자셋
	 */
	private final String charset;

	/**
	 * 컨텐츠 길이
	 */
	private final long contentLength;

	/**
	 * HttpStatus
	 */
	private final int status;

	/**
	 * 컨텐츠타입
	 */
	private final String contentType;

	/**
	 * 리스판스 해더
	 */
	private Header[] headers;

	/**
	 *
	 */
	private final HttpEntity entity;

	/**
	 * 생성자
	 * 
	 * @param entity
	 * @param headers
	 * @param status
	 * @throws IOException
	 */
	ResponseImpl(HttpEntity entity, Header[] headers, int status) throws IOException {
		this.status = status;
		this.entity = entity;
		this.contentLength = entity.getContentLength();
		this.contentType = getContentType(entity.getContentType().getValue());
		this.charset = ContentType.getOrDefault(entity).getCharset().toString();
		this.headers = headers;
	}

	/**
	 * 생성자
	 * 
	 * @param entity
	 * @param headers
	 * @param status
	 * @throws IOException
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	ResponseImpl(HttpEntity entity, Header[] headers, int status, String charset) {
		this.entity = entity;
		this.status = status;
		this.contentLength = entity.getContentLength();
		this.contentType = getContentType(entity.getContentType().getValue());
		this.charset = charset != null ? charset : ContentType.getOrDefault(entity).getCharset().toString();
		this.headers = headers;
	}

	/**
	 * @Desc :해더의 contentType에서 ';'을 때어내고 실제 컨텐츠타입으로 바꾼다.
	 * @Method Name : getContentType
	 * @Date : 2019. 2. 20.
	 * @Author : 백인천
	 * @param contentType
	 * @return
	 */
	private String getContentType(String contentType) {
		String setContentType = contentType;
		if (contentType == null) {
			return null;
		}

		String tempCT = Util.getContentType(contentType);
		if (!tempCT.equals("")) {
			setContentType = tempCT;
		}

		return setContentType;
	}

	/**
	 * 현재 리스판스가 redirect면 true를 리턴한다.
	 */
	public boolean isMove() {
		return this.getStatus() == HttpStatus.SC_MOVED_PERMANENTLY
				|| this.getStatus() == HttpStatus.SC_MOVED_TEMPORARILY;
	}

	public void setHeaders(Header[] allHeaders) {
		this.headers = allHeaders;
	}

	public String getCharset() {
		if (this.charset == null) {
			return MembershipConfig.DEFAULT_SITE_CHAR_SET;
		}
		return this.charset;
	}

	public String getResponseBody() {
		try {
			return EntityUtils.toString(entity, charset);
		} catch (Exception e) {
			log.error("getResponseBody Error", e);
			// throw new HttpRequestException(e);
			return "";
		}
	}

	public InputStream getInputStream() {
		try {
			return this.entity.getContent();
		} catch (Exception e) {
			log.error("getInputStream Error", e);
			throw new HttpRequestException(e);
		}
	}

	public byte[] getBytes() {
		try {
			return EntityUtils.toByteArray(entity);
		} catch (IOException e) {
			log.error("getBytes Error", e);
			throw new HttpRequestException(e);
		}
	}
}
