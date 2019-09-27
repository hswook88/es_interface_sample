package kr.co.shop.interfaces.common.cjmall;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.mapper.CannotResolveClassException;
import com.thoughtworks.xstream.mapper.MapperWrapper;

import kr.co.shop.common.util.UtilsText;
import kr.co.shop.interfaces.zinterfacesdb.model.master.cjmall.CJDelivery;
import kr.co.shop.interfaces.zinterfacesdb.model.master.cjmall.CJResult;
import kr.co.shop.interfaces.zinterfacesdb.model.master.cjmall.InterfacesCJRes0411;
import kr.co.shop.interfaces.zinterfacesdb.model.master.cjmall.InterfacesCJRes0413;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UtilsCJmall {

	/**
	 * Java Object to XML String
	 * 
	 * @param obj
	 * @param charset
	 * @param logDir
	 * @return
	 * @throws Exception
	 */
	public static String createXML(Object obj, String charset, String logDir) throws Exception {
		String packageName = obj.getClass().getName();
		String className = packageName.substring(packageName.lastIndexOf(".") + 1, packageName.length());

		XStream xStream = new XStream(new DomDriver() {

			public HierarchicalStreamWriter createWriter(Writer out) {
				return new PrettyPrintWriter(out) {
					boolean cdata = false;

					@SuppressWarnings("rawtypes")
					public void startNode(String name, Class clazz) {
						super.startNode(name, clazz);
					}

					protected void writeText(QuickWriter writer, String text) {
						if (cdata) {
							writer.write("<![CDATA[");
							writer.write(text);
							writer.write("]]>");
						} else {
							writer.write(convertToXml(text));
							// writer.write(text);
						}
					}
				};
			}
		});
		xStream.autodetectAnnotations(true);

		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"" + charset + "\"?>" + "\n");
		sb.append(xStream.toXML(obj).replace("__", "_"));

		recordsXML(className, sb.toString(), logDir);

		// log.debug("=-= 송신할XML =\n" + sb.toString());

		return sb.toString();
	}

	/**
	 * XML 전송을 위한 특정문차 치환
	 * 
	 * @param str
	 * @return
	 */
	public static String convertToXml(String str) {
		if (str == null || str.equals("")) {
			return "";
		}
		String ret = str;
		// & -> &amp;
		ret = replace(ret, "&", "&amp;");
		// < -> &lt;
		ret = replace(ret, "<", "&lt;");
		// > -> &gt;
		ret = replace(ret, ">", "&gt;");
		// " 변환처리.처리
		ret = replace(ret, "\"", "&quot;");
		// ' 변환처리.처리
		ret = replace(ret, "'", "&apos;");
		// % 변환처리
		ret = replace(ret, "%", "&#37;");

		return ret;
	}

	/**
	 * 문자열 치환
	 * 
	 * @param word
	 * @param fromStr
	 * @param toStr
	 * @return
	 */
	public static String replace(String word, String fromStr, String toStr) {
		return replace(word, fromStr, toStr, true);
	}

	public static String replace(String word, String fromStr, String toStr, boolean caseSensitive) {

		if (word.equals(""))
			return "";

		StringBuffer mainSb = new StringBuffer(word);

		if (!caseSensitive) {
			word = word.toUpperCase();
			fromStr = fromStr.toUpperCase();
		}

		int i = word.lastIndexOf(fromStr);
		if (i < 0)
			return mainSb.toString();

		while (i >= 0) {
			mainSb.replace(i, (i + fromStr.length()), toStr);
			i = word.lastIndexOf(fromStr, i - 1);
		}
		return mainSb.toString();
	}

	public static void recordsXML(String ifName, String ifText, String logDir) throws Exception {

		// XML 전문이 저장될 메인디렉토리
		String defaultFolder = logDir;
		;

		if (!defaultFolder.equals("")) {

			String year = new SimpleDateFormat("yyyy").format(new Date());
			String month = new SimpleDateFormat("MM").format(new Date());
			String day = new SimpleDateFormat("dd").format(new Date());
			String time = new SimpleDateFormat("HHmmssSSS").format(new Date());

			// 저장경로 = 메인디렉토리/년/월/일/
			String filePath = defaultFolder + "/" + year + "/" + month + "/" + day + "/";
			// 파일명 = 송수신구분_인터페이스명_년월일_시분초밀리초.txt
			String fileName = ifName + "_" + year + month + day + "_" + time + ".xml";

			File folder = new File(filePath);
			if (!folder.exists()) {
				folder.mkdirs();
			}

			File file = new File(filePath + fileName);
			FileWriter fw = new FileWriter(file);
			fw.write(ifText);
			fw.close();

		}
	}

	/**
	 * CJ쇼핑 인터페이스 XML전문 송수신
	 * 
	 * @param resXML
	 * @param URL
	 * @param charset
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public static String transferXML(String resXML, String URL, String charset) throws Exception {

		log.debug("resXML - {}", resXML);

		CloseableHttpClient httpclient = HttpClientBuilder.create().setUserAgent("HttpComponents/1.1").build();
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(1000 * 180).setConnectTimeout(1000 * 60)
				.setExpectContinueEnabled(true).build();

		HttpPost httpPost = new HttpPost(URL);
		httpPost.setConfig(requestConfig);

		StringEntity input = new StringEntity(resXML, charset);
		if (resXML != null && !resXML.isEmpty()) {
			input.setContentType("application/xml");
		}

		httpPost.setHeader("Content-Type", UtilsText.concat("application/xml;charset=", charset));
		httpPost.setHeader("Content-Length", Integer.toString(resXML.getBytes().length));
		httpPost.setEntity(input);
		log.debug("content-type : " + httpPost.getEntity());

		int resultCode = 0;

		String returnXML = "";

		try {

			HttpResponse httpResponse = httpclient.execute(httpPost);
			resultCode = httpResponse.getStatusLine().getStatusCode();
			returnXML = httpResponse.getEntity().toString();

			log.debug("httpResponse {}", httpResponse.getStatusLine().toString());
		} catch (Exception e) {
			// 접속중 발생할 수 있는 네트워크관련 오류 체크
			log.debug("=-= CJ몰 인터페이스 접속중 네트워크오류 발생 : " + e.toString());
			throw new Exception("CJ몰 인터페이스 접속중 네트워크오류 발생 : " + e.toString() + "\n해당전문\n" + resXML);
		} finally {
			httpPost.releaseConnection();
			httpclient = null;
		}

		// 접속은 수행하였으나 http관련 오류 체크
		if (resultCode != 200) {
			log.debug("=-= CJ몰 인터페이스 접속중 HTTP[" + resultCode + "] 오류발생");
			throw new Exception("CJ몰 인터페이스 접속중 HTTP[" + resultCode + "] 오류발생");
		}

		return returnXML;
	}

	public static Object parsingXML(String xml, Object obj, String logDir) throws Exception {

		String packageName = obj.getClass().getName();
		String className = packageName.substring(packageName.lastIndexOf(".") + 1, packageName.length());

		recordsXML(className, xml, logDir);

		XStream xstream = new XStream(new DomDriver()) {
			protected MapperWrapper wrapMapper(MapperWrapper next) {
				return new MapperWrapper(next) {
					@SuppressWarnings("rawtypes")
					public boolean shouldSerializeMember(Class definedIn, String fieldName) {
						try {
							return definedIn != Object.class || realClass(fieldName) != null;
						} catch (CannotResolveClassException cnrce) {
							return false;
						}
					}
				};
			}
		};
		xstream.autodetectAnnotations(true);

		// XML전문의 각 그룹(별명)에 대한 클래스 지정
		if (className.equals("InterfacesCJRes0411")) {
			xstream.alias("ifResponse", InterfacesCJRes0411.class);
		} else if (className.equals("InterfacesCJRes0413")) {
			xstream.alias("ifResponse", InterfacesCJRes0413.class);
			xstream.alias("beNotYet", CJDelivery.class);
			xstream.alias("ifResult", CJResult.class);
		}

		return obj = xstream.fromXML(xml.replaceAll("ns1:", ""));
	}

}
