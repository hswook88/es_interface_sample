package kr.co.shop.interfaces.zinterfacesdb.model.master;

import lombok.Data;

@Data
public class InterfacesReturnProcedureAddr {
	
	/*B코드*/
	private String cbcd;
	
	/*매장코드*/
	private String maejangCd;
	
	/*배송아이디*/
	private String dlvyId;
	
	/*수취인 이름*/
	private String newRcvrName;
	
	/*수취인 우편번호*/
	private String newDlvyPostNum;
	
	/*신규 핸드폰번호1*/
	private String newHpNum;
	
	/*신규 핸드폰번호2 */
	private String newHpNum2;
	
	/*신규 핸드폰번호3 */
	private String newHpNum3;
	
	/*신규 전화번호 */
	private String newTelNum;
	
	/*신규 전화번호2 */
	private String newTelNum2;
	
	/*신규 전화번호3 */
	private String newTelNum3;
	
	/*신규 주소 */
	private String newAddr1;
	
	/*신규 주소2 */
	private String newAddr2;
	
	/*배송 메시지 */
	private String dlvyMessage;
}
