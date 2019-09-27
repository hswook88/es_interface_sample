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

import kr.co.shop.interfaces.provider.InterfacesContextProvider;
import kr.co.shop.interfaces.zinterfacesdb.repository.master.InterfacesProductDao;
import kr.co.shop.interfaces.zinterfacesdb.service.InterfacesProductService;
import kr.co.shop.sample.product.model.master.SamplePdProduct;
import kr.co.shop.sample.product.service.SampleProductInterfaceService;
import kr.co.shop.sample.product.service.SampleProductService;
import kr.co.shop.zconfiguration.datasource.DSConfigMaster;
import kr.co.shop.zconfiguration.datasource.DsConfig;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class,
		DataSourceTransactionManagerAutoConfiguration.class, FreeMarkerAutoConfiguration.class })
@SpringBootTest(webEnvironment = WebEnvironment.NONE, classes = {
		// START DB AND BeanContext Configuration ※ 고정
		DsConfig.class, DSConfigMaster.class, InterfacesContextProvider.class,
		// END DB AND BeanContext Configuration

		SampleProductService.class, SampleProductInterfaceService.class,InterfacesProductService.class,InterfacesProductDao.class })
		
@TestPropertySource(value = { "classpath:datasource.properties" })
@ImportResource("classpath*:transaction.xml")
public class DBLinkTransactionTests {

	// @Autowired
	// private InterfacesSampleService interfacesSampleService;

	@Autowired
	private SampleProductService productService;

	@Autowired
	private InterfacesProductService interfacesProductService;
	
	@Test
	public void testExample() throws Exception {

		String chnl = "0072";
		String getVndrPrdtNoText = "0000026";
		SamplePdProduct pdProduct = new SamplePdProduct();
		pdProduct.setVndrPrdtNoText(getVndrPrdtNoText);
		this.productService.insertProduct(pdProduct, chnl);

		this.interfacesProductService.updateProcedureErpBrandNoTrx(getVndrPrdtNoText, "2");
//		log.debug("{}", data);
//		assertNotNull(data);

	}

}