package kr.co.shop.interfaces.zinterfacesdb.model.master.cjmall;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import kr.co.shop.interfaces.zinterfacesdb.model.master.base.InterfacesCJReq;
import lombok.Data;

@Data
@XStreamAlias("tns:ifRequest")
public class InterfacesCJReq0411 extends InterfacesCJReq {

	public InterfacesCJReq0411() {
		super("IF_04_11");
	}

	@XStreamAlias("tns:contents")
	private CJOrderInput orderInput;

}
