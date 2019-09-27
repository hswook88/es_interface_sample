package kr.co.shop.interfaces.module.product.offlineproduct;

import java.util.ArrayList;
import java.util.List;

import kr.co.shop.interfaces.module.member.MembershipInfo;
import kr.co.shop.interfaces.module.member.config.MembershipConfig;
import kr.co.shop.interfaces.module.member.utils.HttpRequestException;
import kr.co.shop.interfaces.module.member.utils.MemberShipProcessException;
import kr.co.shop.interfaces.module.member.utils.RequestSender;
import kr.co.shop.interfaces.module.product.offlineproduct.model.ProductOfflineStockHttp;
import kr.co.shop.interfaces.module.product.offlineproduct.model.ProductOfflineStockParser;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @Desc : 오프라인 재고 조회 서비스
 * @FileName : ProductOfflineStockService.java
 * @Project : shop.interfaces
 * @Date : 2019. 2. 25.
 * @Author : 김영진
 */
@Slf4j
public class ProductOfflineStockService {

	public static List<ProductOfflineStockHttp> getProductOfflineStockFromHttp(String sangpumfullcd,
			boolean stockmergeyn) {
		String host = MembershipConfig.getMemberShipServer();
		String url = MembershipInfo.getMemberUrl("memB060a");
		RequestSender sender = new RequestSender(host + url);
		sender.addParameter("sangpumfullcd",sangpumfullcd);

		List<ProductOfflineStockHttp> stocks = null;
		ProductOfflineStockParser parser = null;

		try {

			/*
			 * as-is상에서의 소스 productsql.xml에서의 selectProductDetail selectProdcutDetail
			 * ProductCriteria criteria = new ProductCriteria();
			 * criteria.setPrdtCode(sangpumfullcd.substring(0, 7));
			 */
			// 상품의 재고통합 연동여부 확인
			if (stockmergeyn) {
				sender.send();
				parser = new ProductOfflineStockParser(sender.getResponseBody());
				stocks = parser.parse();
			}
		} catch (HttpRequestException e) {
			if (log.isErrorEnabled()) {
				log.error(e.getMessage(), e);
			}
		} catch (MemberShipProcessException e) {
			if (log.isErrorEnabled()) {
				log.error(e.getMessage(), e);
				if (parser != null) {
					log.error("XML>" + parser.getSource());
				}
			}
		}

		if (stocks == null) {
			stocks = new ArrayList<ProductOfflineStockHttp>(1);
		}

		return stocks;
	}
}