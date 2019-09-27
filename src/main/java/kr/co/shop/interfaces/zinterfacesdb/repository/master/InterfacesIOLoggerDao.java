package kr.co.shop.interfaces.zinterfacesdb.repository.master;

import org.apache.ibatis.annotations.Mapper;

import kr.co.shop.interfaces.zinterfacesdb.model.master.InterfacesIOLogger;

@Mapper
public interface InterfacesIOLoggerDao {

	public void insertLogger(InterfacesIOLogger loggerParams) throws Exception;

}
