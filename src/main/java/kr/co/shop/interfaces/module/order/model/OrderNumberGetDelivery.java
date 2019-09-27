package kr.co.shop.interfaces.module.order.model;

import java.util.List;

import lombok.Data;

@Data
public class OrderNumberGetDelivery {

	
	 /**
     * 주문 번호
     */
    private String orderNum;
    
    /**
     * 배송 차수
     */
    private String chasu;
    
    /**
     * 1차 드랍사유
     */
    private String dropReason1;
    
    /**
     * 2차 드랍사유
     */
    private String dropReason2;
    
    /**
     * 주문메모
     */
    private String memo;
    
    /**
     * 배송 아이디
     */
    private String dlvyId;
    
    /******************************************************************* 
     * TODO : 매장픽업 (점간이동 매장 정보 정보 추가) 2017-12-20 JANGSIN 
     ********************************************************************/
    
    /** 원거래 매장 아이디 */
    private String branchStoreId;
    
    /** 원거래 매장 명 */
    private String branchStoreName;
    
    /** 점간 이동 매장 아이디 */
    private String branchMoveStoreId;
    
    /** 점간 이동 매장 명 */
    private String branchMoveStoreName;

	public List<OrderNumberGetDelivery> parse() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getSource() {
		// TODO Auto-generated method stub
		return null;
	}
}
