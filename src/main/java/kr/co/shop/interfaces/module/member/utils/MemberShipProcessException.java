package kr.co.shop.interfaces.module.member.utils;

@SuppressWarnings("serial")
public class MemberShipProcessException extends RuntimeException {
	public MemberShipProcessException(String message) {
		super(message);
	}

	public MemberShipProcessException(Exception exception) {
		super(exception);
	}
}
