package process;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.TestPropertySource;

import kr.co.shop.interfaces.zinterfacesdb.service.InterfacesSabangnetService;
import kr.co.shop.zconfiguration.datasource.DSConfigMaster;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class,
		DataSourceTransactionManagerAutoConfiguration.class, FreeMarkerAutoConfiguration.class })
@SpringBootTest(webEnvironment = WebEnvironment.NONE, classes = { DSConfigMaster.class,
		InterfacesSabangnetService.class })
@TestPropertySource(value = { "classpath:datasource.properties" })
@ImportResource("classpath:transaction.xml")
public class MyControllerSabangnetTests {

	@Autowired
	private InterfacesSabangnetService interfacesSabangnetService;

	// @Autowired
	// private InterfacesMemberService interfacesMemberService;

	@Test
	public void testExample() throws Exception {
		String propCode = "001";

		// 상품등록 & 수정
		interfacesSabangnetService.makeSabangnetGoodsInfo();

		// 상품속성코드조쇠
		// interfacesSabangnetService.searchSabangnetPropertiesCode(propCode);

		// 상품요약수정
		// interfacesSabangnetService.makeSabangnetGoodsInfo2();

		// 카테고리조회
		// interfacesSabangnetService.getSabangnetCategoryList();

		// 송장등록
		// interfacesSabangnetService.setSabangnetDelivNumber();

	}

}