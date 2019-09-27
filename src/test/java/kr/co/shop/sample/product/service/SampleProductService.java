package kr.co.shop.sample.product.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.shop.sample.product.model.master.Sample1;
import kr.co.shop.sample.product.model.master.SamplePdProduct;
import kr.co.shop.sample.product.repository.master.Sample1Dao;

@Service
public class SampleProductService {

	@Autowired
	Sample1Dao sample1Dao;

	@Autowired
	private SampleProductInterfaceService productInterfaceService;

	public void insertProduct(SamplePdProduct product, String chnl) throws Exception {
		int num = (int) (Math.random() * 100000);
		Sample1 sample1 = new Sample1();
		sample1.setId(num);
		sample1.setName("interfaces-" + num);
		sample1Dao.insert(sample1);

		// this.productInterfaceService.sendErpProductRegistered(product, chnl);
	}
}