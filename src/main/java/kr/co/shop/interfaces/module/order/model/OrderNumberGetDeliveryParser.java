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

import kr.co.shop.interfaces.module.member.model.BaseParser;
import kr.co.shop.interfaces.module.member.utils.MemberShipProcessException;
import kr.co.shop.interfaces.module.order.model.OrderNumberGetDelivery;
import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 * 클래스명: <code>DeliveryInfoForStockMergeParser</code>
 * </pre>
 * 
 * 
 * 2015.06.10 재고통합 주문별 배송 상태 조회(AS인경우)
 * RESULT : 성공여부, ORDER_NO : 주문번호, DELIVERY_NO : 배송번호, CHASU : 차수 
 * REASON1 : Drop 사유(1차), REASON_2 : Drop 사유(2차), MEMO : 주문메모 
 */
@Slf4j
public class OrderNumberGetDeliveryParser extends BaseParser {

  

    public OrderNumberGetDeliveryParser(String source) {
        super(source);
    }

    public OrderNumberGetDeliveryParser(InputStream is) {
        super(is);
    }

    /**
     * <?xml version="1.0" encoding="UTF-8"?>
     * <root>
     *   <result>SUCCESS</result>
     *       <row>
     *          <ord_no>2015052900001</ord_no>
     *          <delivery_no>0001</delivery_no>
     *          <chasu>3차</chasu>
     *          <reason_1>시간만료 Drop(1차)</reason_1>
     *          <reason_2>시간만료 Drop(2차)</reason_2>
     *          <memo>재고없음</memo>
     *       </row>
     *       <row>
     *          <ord_no>2015052900001</ord_no>
     *          <delivery_no>0002</delivery_no>
     *          <chasu>1차</chasu>
     *          <reason_1></reason_1>
     *          <reason_2></reason_2>
     *          <memo></memo>
     *       </row>
     * <root>
     */
    public List<OrderNumberGetDelivery> parse() {
        List<OrderNumberGetDelivery> results = null; 

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document xmlDoc = builder.parse(new ByteArrayInputStream(this.getSource().getBytes()));
            XPathFactory xPathFactory = XPathFactory.newInstance();
            XPath xPath = xPathFactory.newXPath();
            String success = getSingleValue(xPath, xmlDoc, "/root/result");

            if (!SUCCESS.equals(success)) {
                throw new MemberShipProcessException(String.format("result[%s]", success));
            } else {
                NodeList xmlitems = (NodeList)xPath.compile("/root/row").evaluate(xmlDoc, XPathConstants.NODESET);
                results = new ArrayList<OrderNumberGetDelivery>(xmlitems.getLength());
                
                for (int i = 0; i < xmlitems.getLength(); i++) {
                    Node xmlitem = xmlitems.item(i);
                    NodeList child = xmlitem.getChildNodes();

                    OrderNumberGetDelivery item = new OrderNumberGetDelivery();
                    results.add(item);

                    for (int j = 0; j < child.getLength(); j++) {
                        Node node = child.item(j);
                        if (this.property.contains(node.getNodeName())) {
                            propertyCopy(node, item);
                        }
                    }
                }
            }
        } catch (ParserConfigurationException e) {
            throw new MemberShipProcessException(e);
        } catch (SAXException e) {
            throw new MemberShipProcessException(e);
        } catch (IOException e) {
            throw new MemberShipProcessException(e);
        } catch (XPathExpressionException e) {
            throw new MemberShipProcessException(e);
        }

        return results;
    }

    private void propertyCopy(Node node, OrderNumberGetDelivery item) {
        if ("ord_no".equalsIgnoreCase(node.getNodeName())) {
            item.setOrderNum(node.getTextContent());
        } else if ("delivery_no".equalsIgnoreCase(node.getNodeName())) {
            item.setDlvyId(item.getOrderNum() + node.getTextContent());
        } else if ("chasu".equalsIgnoreCase(node.getNodeName())) {
            item.setChasu(node.getTextContent());
        } else if ("reason_1".equalsIgnoreCase(node.getNodeName())) {
            item.setDropReason1(node.getTextContent());
        } else if ("reason_2".equalsIgnoreCase(node.getNodeName())) {
            item.setDropReason2(node.getTextContent());
        } else if ("memo".equalsIgnoreCase(node.getNodeName())) {
            item.setMemo(node.getTextContent());
        }
    }

    @SuppressWarnings("serial")
    private final Set<String> property = new HashSet<String>(11) {
        {
            add("ord_no");
            add("delivery_no");
            add("chasu");
            add("reason_1");
            add("reason_2");
            add("memo");
        }
    };
}
