package io.vertx.main.util;

import org.apache.commons.lang3.StringUtils;

public class NxUtils {

	public static String defaultString(String orgStr, String changeStr) {
		if (StringUtils.trimToNull(orgStr) == null){
			return changeStr;
		}
		return orgStr;
	}

}
