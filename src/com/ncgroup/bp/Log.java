package com.ncgroup.bp;

import org.apache.log4j.Logger;

public class Log {

	private static final String PREFIX = ">> ";
	private static Logger _logger = Logger.getLogger(Log.class);

	public static void debug(String message) {
		_logger.debug(PREFIX + message);
	}

	public static void info(String message) {
		_logger.info(PREFIX + message);
	}

	public static void warn(String message) {
		_logger.warn(PREFIX + message);
	}

	public static void error(String message) {
		_logger.error(PREFIX + message);
	}

	public static void error(String message, Exception e) {
		_logger.error(PREFIX + message, e);
	}
}
