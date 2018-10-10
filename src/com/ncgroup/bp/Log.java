package com.ncgroup.bp;

import org.apache.log4j.Logger;

/**
 * @description: 日志处理类
 * @author: Lin
 * @created: 2018年9月13日 下午4:46:56
 * @version: 1.0
 */
public class Log {

	private static final String PREFIX = ">> ";
	private static Logger _logger = Logger.getLogger(Log.class);

	/**
	 * @description: 调试日志
	 * @param message
	 */
	public static void debug(String message) {
		message = getStackTrace() + PREFIX + message;
		_logger.debug(message);
	}

	/**
	 * @description: 消息日志
	 * @param message
	 */
	public static void info(String message) {
		message = getStackTrace() + PREFIX + message;
		_logger.info(message);
	}

	/**
	 * @description: 警告日志
	 * @param message
	 */
	public static void warn(String message) {
		message = getStackTrace() + PREFIX + message;
		_logger.warn(message);
	}

	/**
	 * @description: 错误日志
	 * @param message
	 */
	public static void error(String message) {
		message = getStackTrace() + PREFIX + message;
		_logger.error(message);
	}

	/**
	 * @description: 错误日志
	 * @param message
	 * @param e
	 */
	public static void error(String message, Exception e) {
		message = getStackTrace() + PREFIX + message;
		_logger.error(message, e);
	}

	/**
	 * @description: 获取当期线程堆栈跟踪信息
	 * @return
	 */
	private static String getStackTrace() {
		String location = "";
		try {
			StackTraceElement stack = Thread.currentThread().getStackTrace()[3];
			location = "[" + " Thread: " + Thread.currentThread().getName() + " Class: " + stack.getClassName()
					+ " Method: " + stack.getMethodName() + "(Line: " + stack.getLineNumber() + ")" + "]" + "\n";
		} catch (Exception e) {

		}
		return location;
	}
}
