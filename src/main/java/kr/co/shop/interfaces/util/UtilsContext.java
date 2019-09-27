package kr.co.shop.interfaces.util;

import org.springframework.context.ApplicationContext;

import kr.co.shop.interfaces.provider.InterfacesContextProvider;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UtilsContext {

	public static ApplicationContext getContext() {
		return InterfacesContextProvider.getApplicationContext();
	}

	public static Object getBean(String beanName) {
		ApplicationContext applicationContext = getContext();
		return applicationContext.getBean(beanName);
	}

	public static <T> T getBean(Class<?> clazz) {
		ApplicationContext applicationContext = getContext();
		log.debug("## applicationContext {}", applicationContext);
		return (T) applicationContext.getBean(clazz);
	}

}
