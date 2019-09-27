package kr.co.shop.interfaces.zinterfacesdb.model.master.cjmall;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamImplicit;

import kr.co.shop.interfaces.zinterfacesdb.model.master.base.InterfacesCJRes;
import lombok.Data;

@Data
public class InterfacesCJRes0305 extends InterfacesCJRes {

	@XStreamImplicit
	private List<CJLtSupplyPlans> ltSupplyPlans;

}
