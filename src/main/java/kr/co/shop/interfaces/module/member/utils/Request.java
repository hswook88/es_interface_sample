package kr.co.shop.interfaces.module.member.utils;

import java.util.List;
import java.util.Map;

public interface Request {

	public final static String GET = "GET";

	public final static String POST = "POST";

	public static boolean SEND_REDIRECT = true;

	public Response execute();

	public void setPost();

	public void setGet();

	public void setMethod(String method);

	public void setParams(List<RequestParameter> params);

	public void setJsonParams(String jsonParams);

	public void setResponseCharset(String responseCharset);

	public void setHeaders(Map<String, String> headers);
}
