package kr.co.shop.interfaces.zinterfacesdb.model.master.cjmall;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamImplicit;

import kr.co.shop.interfaces.zinterfacesdb.model.master.base.InterfacesCJRes;
import lombok.Data;

/**
 * 미배송정보 등록 수신
 * 
 * @author 백인천
 *
 */
@Data
public class InterfacesCJRes0413 extends InterfacesCJRes {

	// 미배송정보목록
	@XStreamImplicit
	private List<CJDelivery> deliverys;

}
