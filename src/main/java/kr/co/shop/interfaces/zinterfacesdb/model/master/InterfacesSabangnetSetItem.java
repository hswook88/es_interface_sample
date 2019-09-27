package kr.co.shop.interfaces.zinterfacesdb.model.master;

import lombok.Data;

@Data
public class InterfacesSabangnetSetItem {

	/** 상품명(필수) */
	private String goodsNm;

	/** 상품약어 */
	private String goodsKeyword;

	/** 모델명 */
	private String modelNm;

	/** 모델No */
	private String modelNo;

	/** 브랜드명 */
	private String brandNm;

	/** 자체상품코드(필수) */
	private String companyGoodsCd;

	/** 사이트검색어 */
	private String goodsSearch;

	/** 상품구분(필수) */
	private String goodsGubun;

	/** 대분류코드(필수) */
	private String classCd1;

	/** 중분류코드(필수) */
	private String classCd2;

	/** 소분류코드(필수) */
	private String classCd3;

	/** 세분류코드 */
	private String classCd4;

	/** 매입처ID */
	private String partnerId;

	/** 물류처ID */
	private String dPartnerId;

	/** 제조사 */
	private String maker;

	/** 원산지(제조국)(필수) */
	private String origin;

	/** 생산연도 */
	private String makeYear;

	/** 제조일자 */
	private String makeDm;

	/** 시즌(필수) */
	private String goodsSeason;

	/** 남녀구분(필수) */
	private String sex;

	/** 상품상태(필수) */
	private String status;

	/** 판매지역 */
	private String delivAbleRegion;

	/** 세금구분(필수) */
	private String taxYn;

	/** 배송비구분(필수) */
	private String delvType;

	/** 배송비 */
	private String delvCost;

	/** 반품지구분 */
	private String banpumArea;

	/** 원가(필수) */
	private String goodsCost;

	/** 판매가(필수) */
	private String goodsPrice;

	/** TAG가(소비자가)(필수) */
	private String goodsConsumerPrice;

	/** 옵션제목(1) */
	private String char1Nm;

	/** 옵션상세명칭(1) */
	private String char1Val;

	/** 옵션제목(2) */
	private String char2Nm;

	/** 옵션상세명칭(2) */
	private String char2Val;

	/** 대표이미지(필수) */
	private String imgPath;

	/** 종합몰(JPG)이미지(필수) */
	private String imgPath1;

	/** 부가이미지2 */
	private String imgPath2;

	/** 부가이미지3(필수) */
	private String imgPath3;

	/** 부가이미지4 */
	private String imgPath4;

	/** 부가이미지5 */
	private String imgPath5;

	/** 부가이미지6 */
	private String imgPath6;

	/** 부가이미지7 */
	private String imgPath7;

	/** 부가이미지8 */
	private String imgPath8;

	/** 부가이미지9 */
	private String imgPath9;

	/** 부가이미지10 */
	private String imgPath10;

	/** 부가이미지11 */
	private String imgPath11;

	/** 부가이미지12 */
	private String imgPath12;

	/** 부가이미지13 */
	private String imgPath13;

	/** 부가이미지14 */
	private String imgPath14;

	/** 부가이미지15 */
	private String imgPath15;

	/** 부가이미지16 */
	private String imgPath16;

	/** 부가이미지17 */
	private String imgPath17;

	/** 부가이미지18 */
	private String imgPath18;

	/** 부가이미지19 */
	private String imgPath19;

	/** 부가이미지20 */
	private String imgPath20;

	/** 부가이미지21 */
	private String imgPath21;

	/** 부가이미지22 */
	private String imgPath22;

	/** 인증서이미지 */
	private String imgPath23;

	/** 수입면장이미지 */
	private String imgPath24;

	/** 상품상세설명(필수) */
	private String goodsRemarks;

	/** 인증번호 */
	private String certno;

	/** 인증유효 시작일 */
	private String avlstDm;

	/** 인증유효 마지막일 */
	private String avledDm;

	/** 발급일자 */
	private String issuedate;

	/** 인증일자 */
	private String certdate;

	/** 인증기관 */
	private String certAgency;

	/** 인증분야 */
	private String certfield;

	/** 식품재료/원산지 */
	private String material;

	/** 재고관리사용여부(필수) */
	private String stockUseYn;

	/** 옵션수정여부(9:옵션의 내용을 지우지않는다./2:등록된 옵션의 내용을 모두 지우고 새로 구성한다.) */
	private String optType;

	/** 속성수정여부 */
	private String propEditYn;

	/** 속성분류코드 */
	private String prop1Cd;

	/** 속성값1 */
	private String propVal1;

	/** 속성값2 */
	private String propVal2;

	/** 속성값3 */
	private String propVal3;

	/** 속성값4 */
	private String propVal4;

	/** 속성값5 */
	private String propVal5;

	/** 속성값6 */
	private String propVal6;

	/** 속성값7 */
	private String propVal7;

	/** 속성값8 */
	private String propVal8;

	/** 속성값9 */
	private String propVal9;

	/** 속성값10 */
	private String propVal10;

	/** 속성값11 */
	private String propVal11;

	/** 속성값12 */
	private String propVal12;

	/** 속성값13 */
	private String propVal13;

	/** 속성값14 */
	private String propVal14;

	/** 속성값15 */
	private String propVal15;

	/** 속성값16 */
	private String propVal16;

	/** 속성값17 */
	private String propVal17;

	/** 속성값18 */
	private String propVal18;

	/** 속성값19 */
	private String propVal19;

	/** 속성값20 */
	private String propVal20;

	/** 속성값21 */
	private String propVal21;

	/** 속성값22 */
	private String propVal22;

	/** 속성값23 */
	private String propVal23;

	/** 속성값24 */
	private String propVal24;

	/** 속성값25 */
	private String propVal25;

	/** 속성값26 */
	private String propVal26;

	/** 속성값27 */
	private String propVal27;

	/** 속성값28 */
	private String propVal28;

	/** 추가상품그룹코드 */
	private String packCodeStr;

	/** 영문 상품명 */
	private String goodsNmEn;

	/** 출력 상품명 */
	private String goodsNmPr;

	/** 추가 상품상세설명_1 */
	private String goodsRemarks2;

	/** 추가 상품상세설명_2 */
	private String goodsRemarks3;

	/** 추가 상품상세설명_3 */
	private String goodsRemarks4;

	/** 수입신고번호 */
	private String importno;

	/** 원가2 */
	private String goodsCost2;

	/** 원산지 상세지역 */
	private String origin2;

	/** 유효일자 */
	private String expireDm;

	/** 합포제외여부 */
	private String supplySaveYn;

	/** 관리자메모 */
	private String descrition;

}
