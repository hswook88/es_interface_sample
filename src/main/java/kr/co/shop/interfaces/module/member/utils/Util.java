package kr.co.shop.interfaces.module.member.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;

import kr.co.shop.common.util.UtilsDate;
import kr.co.shop.common.util.UtilsText;
import kr.co.shop.interfaces.module.member.MembershipInfo;
import kr.co.shop.interfaces.module.member.config.MembershipConfig;

public class Util {

	/**
	 * @Desc : URI인코딩에서 공백에 대한 문자열 처리 이슈가 있음. 그래서 해당 처리방식을 RFC1738의 공식인 %20으로 변환.
	 *       %3B ? %3F / %2F : %3A # %23 & %24 = %3D + %2B $ %26 , %2C ' ' > '+' >
	 *       %20 % %25 < %3C > %3E ~ %7E % %25
	 * @Method Name : UrlEncoding
	 * @Date : 2019. 2. 18.
	 * @Author : 백인천
	 * @param url
	 * @param charset
	 * @return
	 * @throws Exception
	 */
	public static String UrlEncoding(String url, String charset) throws Exception {
		return ToRfc1738Encode(url, charset);
	}

	/**
	 * @Desc : UrlEncoding시에 공백에 대한 문자열을 +로 변환. 해당 URL을 실제 가져올수음. 추가로 +를 %20으로 변환.
	 * @Method Name : ToRfc1738Encode
	 * @Date : 2019. 2. 18.
	 * @Author : 백인천
	 * @param url
	 * @param charset
	 * @return
	 * @throws Exception
	 */
	private static String ToRfc1738Encode(String url, String charset) throws Exception {
		String encodedString = "";
		try {
			if (charset != null) {
				encodedString = URLEncoder.encode(url, charset);
			} else {
				encodedString = URLEncoder.encode(url, MembershipInfo.sysCharset);
			}
		} catch (UnsupportedEncodingException e) {
			encodedString = URLEncoder.encode(url, MembershipInfo.sysCharset);
		}

		StringBuilder buffer = new StringBuilder(512);
		for (int i = 0; i < encodedString.length(); i++) {
			char ch = encodedString.charAt(i);
			if (ch == '+') {
				buffer.append("%20");
			} else {
				buffer.append(ch);
			}
		}
		return buffer.toString();
	}

	public static String UrlEncoding(String url) throws Exception {
		return ToRfc1738Encode(url, null);
	}

	public static String UrlDecoding(String url, String charset) throws Exception {
		try {
			return URLDecoder.decode(url, charset);
		} catch (UnsupportedEncodingException e) {
			return URLDecoder.decode(url, MembershipInfo.sysCharset);
		}
	}

	public static String UrlDecoding(String url) throws Exception {
		return URLDecoder.decode(url, MembershipInfo.sysCharset);
	}

	/**
	 * @Desc : httpResponse해더에서 contentType을 분석해서 리턴한다.
	 * @Method Name : getContentType
	 * @Date : 2019. 2. 18.
	 * @Author : 백인천
	 * @param contentType
	 * @return
	 */
	public static String getContentType(String contentType) {
		if (contentType == null) {
			return null;
		}

		String replaceContentType = "";
		int index = UtilsText.indexOf(contentType, ';');
		if (index != -1) {
			replaceContentType = UtilsText.substring(contentType, 0, index);
		} else {
			replaceContentType = contentType;
		}

		return replaceContentType;
	}

	/**
	 * @Desc : inputStream to String
	 * @Method Name : getString
	 * @Date : 2019. 2. 18.
	 * @Author : 백인천
	 * @param inputStream
	 * @return
	 */
	public static String getString(InputStream inputStream) {
		StringBuffer out = new StringBuffer();
		byte[] b = new byte[4096];
		try {
			for (int n; (n = inputStream.read(b)) != -1;) {
				out.append(new String(b, 0, n));
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		return out.toString();
	}

	/**
	 * @Desc : 암호화
	 * @Method Name : encode
	 * @Date : 2019. 2. 14.
	 * @Author : 백인천
	 * @param param
	 * @return
	 */
	public static String encode(String param) {
		try {
			return URLEncoder.encode(param, MembershipConfig.DEFAULT_SITE_CHAR_SET);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @Desc : 현재시점의 년월일(YYYYMMDD)을 반환한다.
	 * @Method Name : getYyyymmdd
	 * @Date : 2019. 2. 18.
	 * @Author : 백인천
	 * @return
	 */
	public static String getYyyymmdd() {
		return new SimpleDateFormat(UtilsDate.DEFAULT_DATE_PATTERN_NOT_CHARACTERS).format(UtilsDate.getDate());
	}

}
