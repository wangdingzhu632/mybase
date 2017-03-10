package me.supercube.common.util;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

public class ValidUtil {

	/**
	 * 获取验证错误信息
	 *
	 * @param result 验证结果
	 *
	 * @return
	 * */
	public static String getErrorMessage(BindingResult result) {
		StringBuilder msg = new StringBuilder();
		for(FieldError error : result.getFieldErrors()) {
			msg.append(error.getField() + "" + error.getDefaultMessage());
		}
		return msg.toString();
	}

}
