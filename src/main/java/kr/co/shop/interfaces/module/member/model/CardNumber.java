package kr.co.shop.interfaces.module.member.model;

import lombok.Data;

/**
 * @Desc : 카드구분(PVC : 0406191, 온라인(모바일웹) : 0406192, 모바일(APP) : 0406194)
 * @FileName : CardNumber.java
 * @Project : shop.interfaces
 * @Date : 2019. 2. 20.
 * @Author : 백인천
 */
@Data
public class CardNumber {

	/**
	 * 카드번호(card_no)
	 */
	private String cardNumber;

	/**
	 * 카드구분코드(card_flag)
	 */
	private String cardGbnCode;

	/**
	 * pvc여부
	 */
	private boolean pvc;

	/**
	 * 카드발급일(card_issue_dt)
	 */
	private String cardIssueDate;

	public void setCardGbnCode(String cardGbnCode) {
		this.cardGbnCode = cardGbnCode;
		this.pvc = "0406191".equals(cardGbnCode);
	}

	public boolean isPvc() {
		return pvc;
	}

}
