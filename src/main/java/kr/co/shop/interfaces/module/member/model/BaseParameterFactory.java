package kr.co.shop.interfaces.module.member.model;

import org.apache.http.client.config.RequestConfig;

import kr.co.shop.interfaces.module.member.config.MembershipConfig;

/**
 * @Desc : 기본파라메터 사용. Request의 BaseParameter의 Http Parameter 사용여부에 따른 커스텀.
 * @FileName : BaseParameterFactory.java
 * @Project : shop.interfaces
 * @Date : 2019. 2. 14.
 * @Author : 백인천
 */
public class BaseParameterFactory {

	/**
	 * @Desc : Default Parameter 구성
	 *       (setSocketTimeout/setConnectTimeout/setExpectContinueEnabled)
	 * @Method Name : getDefaulParameter
	 * @Date : 2019. 2. 22.
	 * @Author : 백인천
	 * @return
	 */
	public static RequestConfig getDefaulParameter() {
		RequestConfig requestConf = RequestConfig.custom().setSocketTimeout(MembershipConfig.MAX_SOCKET_TIMEOUT)
				.setConnectTimeout(MembershipConfig.MAX_CONNECTION_TIMEOUT).setExpectContinueEnabled(true).build();

		return requestConf;
	}
}
