package kr.co.shop.interfaces.module.payment.nice.model;

import lombok.Data;

@Data
public class QueryString {
	private String name;
	private String value;

	public QueryString(String name, String value) {
		this.name = name;
		this.value = value;
	}
}
