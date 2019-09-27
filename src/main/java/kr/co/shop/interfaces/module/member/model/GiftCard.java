package kr.co.shop.interfaces.module.member.model;

import lombok.Data;

@Data
public class GiftCard {

	/**
	 * 카드번호(card_no)
	 */
	private String giftNo;

	/**
	 * 남은금액(balance)
	 */
	private int balance;

}