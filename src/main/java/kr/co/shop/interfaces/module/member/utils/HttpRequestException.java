package kr.co.shop.interfaces.module.member.utils;

public class HttpRequestException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public HttpRequestException() {
		super();
	}

	public HttpRequestException(Exception e) {
		super(e);
	}

	public HttpRequestException(String string) {
		super(string);
	}
}