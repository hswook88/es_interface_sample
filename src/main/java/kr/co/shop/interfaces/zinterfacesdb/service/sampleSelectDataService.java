package kr.co.shop.interfaces.zinterfacesdb.service;

import java.lang.reflect.Parameter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/*import kr.co.shop.interfaces.zinterfacesdb.model.master.sampleSelectData;*/
import kr.co.shop.interfaces.zinterfacesdb.repository.master.sampleSelectDataDao;

@Service
public class sampleSelectDataService {

	@Autowired
	sampleSelectDataDao SampleSelectDataDao;
	/*
	
	public List<sampleSelectData> sampleSelectData() throws Exception {
		return SampleSelectDataDao.sampleSelectData();
	}
	
	public List<sampleSelectData> insertSampleData(
			String CBCd,
			String MaejangCd,
			String SangPumFullCd,
			String Qty,
			String InputType,
			String RegDate,
			String ConDate,
			String ErrorStatus,
			String WorkDiv,
			String BeforeQty) 
					throws Exception {
		return SampleSelectDataDao.insertSampleData(
				
				CBCd,
				MaejangCd,
				SangPumFullCd,
				Qty,
				InputType,
				RegDate,
				ConDate,
				ErrorStatus,
				WorkDiv,
				BeforeQty);
	}*/
	
}
