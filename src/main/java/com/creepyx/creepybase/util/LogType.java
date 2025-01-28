package com.creepyx.creepybase.util;

public enum LogType {
	INFO("&r[INFO] "),
	WARNING("&6[WARNING] "),
	ERROR("&c[ERROR] "),
	DEBUG("&e[DEBUG] ");

	final String prefix;

	LogType(String prefix) {
		this.prefix = prefix;
	}
}
