package com.creepyx.creepybase.util;

import com.creepyx.creepybase.CreepyBase;
import com.creepyx.creepybase.config.Config;
import lombok.experimental.UtilityClass;
import net.kyori.adventure.text.Component;
import org.bukkit.command.ConsoleCommandSender;

@UtilityClass
public class LogUtil {

	private static String prefix;

	public void log(LogType logType, String message) {

		ConsoleCommandSender consoleSender = CreepyBase.getInstance().getServer().getConsoleSender();
		if (prefix == null) {
			prefix = getPrefix();
		}
		if (logType == LogType.DEBUG) {
			if (!new Config("config.yml").getBoolean("debug"))
				return;
		}
		Component finalMessage = StringUtil.asComponent(logType.prefix).append(Component.text(prefix).append(StringUtil.asComponent(message)));
		consoleSender.sendMessage(finalMessage);
	}

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

	private String getPrefix() {
		return CreepyBase.getInstance().getLogPrefix() != null ? CreepyBase.getInstance().getLogPrefix() : ("[" + CreepyBase.getInstance().getName() + "]");
	}
}
