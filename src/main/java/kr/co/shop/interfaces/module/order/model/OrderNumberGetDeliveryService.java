package kr.co.shop.interfaces.module.order.model;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;



import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import kr.co.shop.interfaces.module.member.MembershipInfo;
import kr.co.shop.interfaces.module.member.config.MembershipConfig;
import kr.co.shop.interfaces.module.member.model.BaseParser;
import kr.co.shop.interfaces.module.member.utils.HttpRequestException;
import kr.co.shop.interfaces.module.member.utils.MemberShipProcessException;
import kr.co.shop.interfaces.module.member.utils.RequestSender;
import kr.co.shop.interfaces.module.member.utils.Util;
import kr.co.shop.interfaces.module.order.model.OrderNumberGetDelivery;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @Desc : 오프라인 주문 조회 서비스
 * @FileName : OrderNumberGetDeliveryService.java
 * @Project : shop.interfaces
 * @Date : 2019. 2. 25.
 * @Author : 김영진
 */
@Slf4j
public class OrderNumberGetDeliveryService {

	public List<OrderNumberGetDelivery> getDeliveryInfoForStockMerge(String orderNum) {

        List<OrderNumberGetDelivery> dlvyInfo = new ArrayList<OrderNumberGetDelivery>(1);

//        if(this.systemService.getMembershipPolicyArticle().isStockMergeYn()) {          //재고통합 연동여부 확인
        String host = MembershipConfig.getMemberShipServer();
		String url = MembershipInfo.getMemberUrl("memB070a");
		RequestSender sender = new RequestSender(host + url);
        sender.addParameter("orderno", Util.encode(orderNum));

        OrderNumberGetDeliveryParser parser = null;

        try {
            sender.send();
            parser = new OrderNumberGetDeliveryParser(sender.getResponseBody());
            dlvyInfo = parser.parse();

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
//        }

        return dlvyInfo;
    }
}
