package kr.co.shop.interfaces.module.payment.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Desc : 결제모듈 factory
 * @FileName : PaymentServiceFactory.java
 * @Project : shop.interfaces
 * @Date : 2019. 1. 31.
 * @Author : KTH
 */
@Component
public class PaymentServiceFactory {

	@Autowired
	private List<IPaymentService> services;

	private static final Map<String, IPaymentService> serviceMap = new HashMap<>();

	@PostConstruct
	public void initPaymentService() throws Exception {
		for (IPaymentService service : services) {
			serviceMap.put(service.getPayMethodType(), service);
		}
	}

	public static IPaymentService getService(String payMethodType) throws PaymentException {
		IPaymentService service = serviceMap.get(payMethodType);
		if (service == null)
			throw new PaymentException("Unknown payment method type: " + payMethodType);
		return service;
	}

}
