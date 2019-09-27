package kr.co.shop.interfaces.module.member.utils;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.List;

import kr.co.shop.common.util.UtilsText;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class Url {

	@SuppressWarnings("unused")

	private final static char SEPERATOR = '/';

	public static int DEFAULT_BUFFER_SIZE = 4096;

	/**
	 * URL
	 */
	private URL url = null;

	/**
	 * 기본포트(80)
	 */
	public final static int DEFAULT_PORT = 80;

	/**
	 * 기본프로토콜(http)
	 */
	public final static String DEFAULT_PROTOCOL = "http";

	/**
	 * 포트
	 */
	private int port = 0;

	private String host;

	private String path;

	private String query;

	private String fileName;

	private String protocol;

	private String schema;

	public Url(String schema, String host, int port, String path, String query) {
		StringBuilder buffer = new StringBuilder(200);
		buffer.append(schema);
		buffer.append(host).append(':');
		buffer.append(port).append(path);
		if (UtilsText.isEmpty(query)) {
		} else {
			buffer.append('?').append(query);
		}

		generateUrlInstance(buffer.toString());
	}

	/**
	 * 생성자
	 * 
	 * @param urlString
	 * @throws HttpRequestException
	 */
	public Url(String urlString) {
		generateUrlInstance(urlString);
	}

	private void generateUrlInstance(String urlString) {
		try {
			this.url = new URL(urlString);
			setProperty();
		} catch (MalformedURLException e) {
			log.error("generateUrlInstance Error", e);
		}
	}

	public void setProperty() {
		int urlPort = url.getPort();
		this.port = urlPort == -1 ? DEFAULT_PORT : urlPort;
		this.host = url.getHost();
		this.protocol = url.getProtocol();
		this.schema = this.protocol + "://";
		String pp = this.url.getPath();
		DirectoryPath dp = new DirectoryPath(pp);
		dp.analysis();
		this.path = dp.getURI();
		this.fileName = dp.getFileName();
		this.query = url.getQuery();
	}

	/**
	 * 
	 * @param url
	 * @throws HttpRequestException
	 */
	public Url(URL url) {
		this.url = url;
		setProperty();
	}

	/**
	 * 
	 * @param url
	 * @throws HttpRequestException
	 */
	public Url(URI uri) {
		try {
			this.url = uri.toURL();
			setProperty();
		} catch (MalformedURLException e) {
			// throw new HttpRequestException(e);
			log.error("Uri Error", e);
		}
	}

	/**
	 * 생성자
	 * 
	 * @param urlString
	 * @param baseUrl
	 * @throws MalformedURLException
	 */
	public Url(String urlString, String baseUrl) throws MalformedURLException {
		this.url = new URL(baseUrl + urlString);
		setProperty();
	}

	/**
	 * <pre>
	 * 쿼리정보리턴
	 * </pre>
	 * 
	 * @return
	 */
	public String getQuery() {
		return this.query == null ? "" : this.query;
	}

	/**
	 * <pre>
	 * 패스+쿼리정보 리턴
	 * </pre>
	 * 
	 * @return
	 */
	public String getURI() {
		StringBuilder buffer = new StringBuilder(DEFAULT_BUFFER_SIZE);
		buffer.append(this.getPath());
		if (this.getQuery().equals("") == false) {
			buffer.append("?").append(this.getQuery());
		}

		return buffer.toString();
	}

	/**
	 * <pre>
	 * 패스+쿼리정보리턴
	 * 쿼리를 URI인코딩한다.
	 * </pre>
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getEncodingURI() throws Exception {
		StringBuilder buffer = new StringBuilder(DEFAULT_BUFFER_SIZE);
		if (isEncodingURI()) {
			return this.getURI();
		}

		DirectoryPath dp = new DirectoryPath(this.getPath());
		dp.analysis();

		List<String> directorys = dp.getDirectories();
		for (String directory : directorys) {
			buffer.append(SEPERATOR);
			buffer.append(Util.UrlEncoding(directory));
		}

		buffer.append(SEPERATOR);
		buffer.append(Util.UrlEncoding(dp.getFileName()));

		if (UtilsText.isNotEmpty(this.getQuery())) {
			String tmpQuery = this.getQuery();
			buffer.append("?");
			String word = "";
			for (int i = 0; i < tmpQuery.length(); i++) {
				char ch = tmpQuery.charAt(i);
				if (ch == '&' || ch == '=') {
					buffer.append(Util.UrlEncoding(word)).append(ch);
					word = "";
				} else {
					word += ch;
				}
			}
			if (UtilsText.isNotEmpty(word)) {
				buffer.append(Util.UrlEncoding(word));
			}
		}

		return buffer.toString();
	}

	/**
	 * <pre>
	 * 딱히 알방법이 없어서 '%'문자열이 섞여 있으면 인코딩된 유알엘로 생각한다.
	 * </pre>
	 * 
	 * @return
	 */
	private boolean isEncodingURI() {
		int index = UtilsText.indexOf(this.getURI(), '%');
		return index != UtilsText.INDEX_NOT_FOUND;
	}

	/**
	 * 새로작성한 URL을 리턴한다.
	 * 
	 * @return UrlString
	 */
	@Override
	public String toString() {
		StringBuilder buffer = new StringBuilder(DEFAULT_BUFFER_SIZE);
		buffer.append(this.getProtocol()).append("://");
		if (UtilsText.isEmpty(this.getHost())) {
			return buffer.toString();
		} else {
			buffer.append(this.getHost());
		}

		if (getPort() != DEFAULT_PORT) {
			buffer.append(":").append(this.getPort());
		}

		if (this.getPath().equals("")) {
			buffer.append(SEPERATOR);
		} else {
			buffer.append(this.getPath());
		}

		if (this.getQuery().equals("") == false) {
			buffer.append("?").append(this.getQuery());
		}

		return buffer.toString();
	}

	/**
	 * <pre>
	 * 프로토콜 + 호스트 + 포트
	 * </pre>
	 * 
	 * @return
	 */
	public String getHostNPort() {
		StringBuilder buffer = new StringBuilder(DEFAULT_BUFFER_SIZE);
		buffer.append(this.getProtocol()).append("://");
		buffer.append(this.getHost());
		if (getPort() != DEFAULT_PORT) {
			buffer.append(":").append(this.getPort());
		}
		return buffer.toString();
	}

	/**
	 * <pre>
	 * 프로토콜 + 호스트 + 포트 + 패스
	 * </pre>
	 * 
	 * @return
	 */
	public String getHostNpath() {
		StringBuilder buffer = new StringBuilder(DEFAULT_BUFFER_SIZE);
		buffer.append(this.getProtocol()).append("://");
		buffer.append(this.getHost());
		if (getPort() != DEFAULT_PORT) {
			buffer.append(":").append(this.getPort());
		}
		if (this.getPath().equals("")) {
			buffer.append(SEPERATOR);
		} else {
			buffer.append(this.getPath());
		}

		return buffer.toString();
	}

	/**
	 * <pre>
	 * 프로토콜 + 호스트
	 * </pre>
	 * 
	 * @return
	 */
	public String getProtocolHost() {
		StringBuilder buffer = new StringBuilder(DEFAULT_BUFFER_SIZE);
		buffer.append(this.getProtocol()).append("://");
		buffer.append(this.getHost());
		if (getPort() != DEFAULT_PORT) {
			buffer.append(":").append(this.getPort());
		}
		buffer.append(SEPERATOR);
		return buffer.toString();
	}

}
