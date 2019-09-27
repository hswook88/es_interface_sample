package kr.co.shop.interfaces.zinterfacesdb.repository.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesSabangnetSetItem;
import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesSabangnetSetItem2;

@Mapper
public interface InterfacesSabangnetDao {

	public List<InterfacesSabangnetSetItem> selectAffltsSendItem() throws Exception;

	public List<InterfacesSabangnetSetItem2> selectAffltsSendItem2() throws Exception;

}
