package kr.co.shop.interfaces.module.payment.nice.model;

import lombok.Data;

@Data
public class CommNiceRes<T> {
	private String resCode;
	private String resMsg;
	private String reqStr;
	private String resStr;
	private T resData;

	public CommNiceRes() {
	}

	public CommNiceRes(String resCode, String resMsg) {
		this.resCode = resCode;
		this.resMsg = resMsg;
	}

	public CommNiceRes(String resCode, String resMsg, T resData) {
		this.resCode = resCode;
		this.resMsg = resMsg;
		this.resData = resData;
	}
}
