package kr.co.shop.interfaces.zinterfacesdb.model.master.cjmall;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import kr.co.shop.interfaces.zinterfacesdb.model.master.base.InterfacesCJReq;
import lombok.Data;

/**
 * 미배송정보 등록 송신
 * 
 * @author 백인천
 *
 */
@Data
@XStreamAlias("tns:ifRequest")
public class InterfacesCJReq0413 extends InterfacesCJReq {

	public InterfacesCJReq0413() {
		super("IF_04_13");
	}

	@XStreamImplicit
	private List<CJDeliveryDetail> deliveryDetail;

}
