package kr.co.shop.interfaces.module.payment.nice.model;

import lombok.Data;

@Data
public class TranHistory {
	private String tranType;
	private String tranDate;
	private String franchisee;
	private String catId;
	private String approvalNum;
	private Long tranAmount;
}
