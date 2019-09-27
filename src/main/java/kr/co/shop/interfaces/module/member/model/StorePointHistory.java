package kr.co.shop.interfaces.module.member.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import kr.co.shop.common.util.UtilsText;
import lombok.Data;

/**
 * @Desc : 포인트 적립이력 조회(memA920a)
 * @FileName : StorePointHistory.java
 * @Project : shop.interfaces
 * @Date : 2019. 2. 22.
 * @Author : 백인천
 */
@Data
public class StorePointHistory {

	/**
	 * save_date : 적립일자
	 */
	private String saveDate;

	/**
	 * store_cd : 점코드
	 */
	private String storeId;

	/**
	 * save_gubun : 적립/사용구분(가입적립,강제적립,매출적립,사용)
	 */
	private String saveGubun;

	/**
	 * save_point : 적립포인트
	 */
	private int savePoint;

	/**
	 * deal_no : 거래번호
	 */
	private String dealNumber;

	/**
	 * point_gubun : 적립/이벤트 구분 (신규)
	 */
	private String pointGubun;

	/**
	 * ins_time : 생성일자
	 */
	private String insTime;

	/**
	 * @Desc : 포인트 만료일자
	 * @Method Name : getBuyPointExtinctDate
	 * @Date : 2019. 3. 21.
	 * @Author : 백인천
	 * @return
	 */
	public Date getBuyPointExtinctDate() {
		if (UtilsText.isBlank(saveDate)) {
			return null;
		}

		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(new SimpleDateFormat("yyyyMMdd").parse(saveDate));
			cal.set(Calendar.MONTH, 11);
			cal.set(Calendar.DAY_OF_MONTH, 31);
			cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) + 2);
		} catch (ParseException e) {
			return null;
		}

		return cal.getTime();
	}

	/**
	 * @Desc :
	 * @Method Name : getValidateDate
	 * @Date : 2019. 3. 21.
	 * @Author : 백인천
	 * @return
	 */
	public int getValidateDate() {
		if (UtilsText.equals(pointGubun, "이벤트")) {
			return 0;
		}

		long startDate = new Date().getTime();
		long endDate = getBuyPointExtinctDate().getTime();
		if (endDate < startDate) {
			return 0;
		}

		int validateDate = (int) ((endDate - startDate) / (1000 * 60 * 60 * 24));
		return validateDate;
	}
}