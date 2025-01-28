package com.creepyx.creepybase.util;

import net.kyori.adventure.text.format.NamedTextColor;

public enum LogType {
	INFO("&r[INFO] ", NamedTextColor.GREEN),
	WARNING("&6[WARNING] ", NamedTextColor.GOLD),
	ERROR("&c[ERROR] ", NamedTextColor.RED),
	DEBUG("&e[DEBUG] ", NamedTextColor.YELLOW),;

	final String prefix;
	final NamedTextColor color;

	LogType(String prefix, NamedTextColor color) {
		this.prefix = prefix;
		this.color = color;
	}
}
