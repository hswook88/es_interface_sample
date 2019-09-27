package kr.co.shop.interfaces.module.member.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import kr.co.shop.common.util.UtilsText;
import lombok.Data;

@Data
public class PrivateReport {
	/**
	 * 총포인트, save_point
	 */
	private int totalPoint;

	/**
	 * 가능포인트, poss_point
	 */
	private int accessPoint;

	/**
	 * 소멸포인트, termi_point
	 */
	private int extinctPoint;

	/**
	 * 스탬프카운트, stamp_cnt
	 */
	private int stampCount;

	/**
	 * 전체 스탬프 카운트, tot_stamp_cnt
	 */
	private int totalStampCount;

	/**
	 * 적립예정포인트
	 */
	private int toDoSavePoint;

	/**
	 * 이벤트포인트
	 */
	private int eventPoint;

	/**
	 * 이벤트 만료일자
	 */
	private String eventDate;

	/**
	 * 적립예정포인트
	 */
	private int preSavePoint;

	// TODO : 포인트 변경 후 수정해야함
	public int getBuyPoint() {
		return getAccessPoint();
	}

	public Date getEventPointExtinctDate() {
		if (UtilsText.isBlank(eventDate)) {
			return null;
		}

		if (UtilsText.equals(eventDate, "1900-01-01")) {
			return null;
		}

		try {
			return new SimpleDateFormat("yyyy-MM-dd").parse(eventDate);
		} catch (Exception e) {
			return null;
		}
	}

	public int getEventValidateDate() {
		if (getEventPointExtinctDate() == null) {
			return -1;
		}

		long startDate = new Date().getTime();
		long endDate = getEventPointExtinctDate().getTime();
		if (endDate < startDate) {
			return -1;
		}

		int validateDate = (int) ((endDate - startDate) / (1000 * 60 * 60 * 24));
		return validateDate;
	}
}
