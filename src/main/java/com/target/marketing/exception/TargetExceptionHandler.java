package com.target.marketing.exception;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.target.marketing.common.TargetConstant;

@RestControllerAdvice
public class TargetExceptionHandler {

	@ExceptionHandler(TargetServiceException.class)
	public Map<String, Object> handleTargetServiceException(TargetServiceException targetServiceException,
			HttpServletResponse httpServletResponse) {
		Map<String, Object> responseMap = new LinkedHashMap<>();
		int code = targetServiceException.getCode();

		if (code > 1000)
			httpServletResponse.setStatus(HttpStatus.BAD_REQUEST.value());
		else if (code == 500)
			httpServletResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());

		responseMap.put(TargetConstant.CODE, code);
		responseMap.put(TargetConstant.MESSAGE, targetServiceException.getMessage());

		return responseMap;
	}
}
