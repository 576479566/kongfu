package com.kongfu.util;

public class My_Exception extends RuntimeException{

	/**
	 * 异常信息自定义
	 */
	private static final long serialVersionUID = 1L;
	public My_Exception(String msg){
		super(msg);
	}
}
