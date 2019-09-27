package kr.co.shop.interfaces.module.member.model;

import lombok.Data;

@Data
public class CardHistory {

	/**
	 * 카드발급일자(card_issue_dt)
	 */
	private String cardIssueDate;

	/**
	 * 카드번호(card_no)
	 */
	private String cardNo;

	/**
	 * 카드플래그(card_flag)
	 */
	private String cardFlag;

	/**
	 * 상세내용(dtl_nm)
	 */
	private String detailName;

	/**
	 * 카드등록시간(card_time)
	 */
	private String cardTime;

}