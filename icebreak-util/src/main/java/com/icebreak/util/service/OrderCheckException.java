package com.icebreak.util.service;

import com.icebreak.util.lang.constants.SplitConstants;

import java.util.HashMap;
import java.util.Map;
public class OrderCheckException extends IllegalArgumentException {
	
	private static final long serialVersionUID = 1L;
	
	private Map<String, String> errorMap = new HashMap<>();
	
	private String msg;
	
	public OrderCheckException() {
		super();
	}
	
	public OrderCheckException(Throwable cause) {
		super(cause);
	}
	
	public Map<String, String> getErrorMap() {
		return errorMap;
	}
	
	/**
	 * 增加参数错误信息
	 * 
	 * @param parameter 校验失败参数
	 * @param msg 参数信息
	 */
	public void addError(String parameter, String msg) {
		this.errorMap.put(parameter, msg);
		this.msg = null;
	}
	
	@Override
	public String getMessage() {
		if (msg == null) {
			if (errorMap.isEmpty()) {
				msg = "";
			} else {
				StringBuilder sb = new StringBuilder();
				for (Map.Entry entry : errorMap.entrySet()) {
					sb.append(entry.getKey()).append(SplitConstants.SEPARATOR_CHAR_COLON)
						.append(entry.getValue()).append(SplitConstants.SEPARATOR_CHAR_COMMA);
				}
				msg = sb.toString();
			}
		}
		return msg;
	}
	
	@Override
	public synchronized Throwable fillInStackTrace() {
		return this;
	}
}
