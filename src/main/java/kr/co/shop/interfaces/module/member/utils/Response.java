package kr.co.shop.interfaces.module.member.utils;

import java.io.InputStream;

import org.apache.http.Header;

public interface Response {
	public int getStatus();

	public long getContentLength();

	public String getContentType();

	public String getResponseBody();

	public String getCharset();

	public boolean isMove();

	public Header[] getHeaders();

	public InputStream getInputStream();

	byte[] getBytes();
}
