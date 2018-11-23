package com.app.util;

import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class LocaleMessageUtil {
	@Autowired
	private MessageSource messageSource;

	public String getMessage(String code, String language, Object... args) {
		if (StringUtils.equalsIgnoreCase("zh", language)) {
			return getMessage(code, args, "", Locale.CHINA);
		} else if (StringUtils.equalsIgnoreCase("en", language)) {
			return getMessage(code, args, "", Locale.US);
		} else {
			return getMessage(code, args, "", Locale.CHINA);
		}
	}

	public String getMessage(String code, Object[] args, String defaultMessage, Locale locale) {
		return messageSource.getMessage(code, args, defaultMessage, locale);
	}
}
