package kr.co.shop.interfaces.zinterfacesdb.model.master.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

import lombok.Data;

/**
 * CJmall 송신 공통모델
 * 
 * @author 백인천
 *
 */
@Data
public class InterfacesCJReq {

	@Autowired
	private Environment env;

	protected InterfacesCJReq(String ifId) {
		this.ifId = ifId;
		this.schemaLocation = "http://www.example.org/ifpa ../" + ifId + ".xsd";
	}

	@XStreamAsAttribute
	@XStreamAlias("xmlns:tns")
	private String tns = "http://www.example.org/ifpa";

	@XStreamAsAttribute
	@XStreamAlias("tns:ifId")
	public String ifId;

	@XStreamAsAttribute
	@XStreamAlias("xmlns:xsi")
	private String xsi = "http://www.w3.org/2001/XMLSchema-instance";

	@XStreamAsAttribute
	@XStreamAlias("xsi:schemaLocation")
	private String schemaLocation;

	@XStreamAlias("tns:vendorId")
	private String vendorId;

	@XStreamAlias("tns:vendorCertKey")
	private String vendorCertKey;

}
