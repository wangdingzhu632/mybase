package me.supercube.common.util;

import org.springframework.web.context.request.WebRequest;

public class AjaxUtils {

	private AjaxUtils() {}

	public static boolean isAjaxRequest(WebRequest webRequest) {
		String requestedWith = webRequest.getHeader("X-Requested-With");
		return requestedWith != null && "XMLHttpRequest".equals(requestedWith);
	}

	public static boolean isAjaxUploadRequest(WebRequest webRequest) {
		return webRequest.getParameter("ajaxUpload") != null;
	}

}
