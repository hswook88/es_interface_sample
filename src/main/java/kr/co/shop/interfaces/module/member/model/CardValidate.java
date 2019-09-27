package kr.co.shop.interfaces.module.member.model;

import lombok.Data;

/**
 * @Desc : 맴버십 카드 유효성 여부 확인
 * @FileName : CardValidate.java
 * @Project : shop.interfaces
 * @Date : 2019. 2. 26.
 * @Author : 백인천
 */
@Data
public class CardValidate {

	/**
	 * 카드상태(01 : 미발급, 02 : 정상, 03 : 분실, 04 : 탈퇴, 05 : 폐기)
	 */
	private String cardStat;

	/**
	 * 본인여부 (00 : 미등록, 01 : 본인등록, 02: 타인등록)
	 */
	private String safekeyCheck;
}