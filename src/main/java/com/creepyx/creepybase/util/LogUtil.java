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
		Component finalMessage = StringUtil.asComponent(prefix).append(StringUtil.asComponent(logType.prefix).color(logType.color)).append(StringUtil.asComponent(message).color(logType.color));
		consoleSender.sendMessage(finalMessage);
	}

	private String getPrefix() {
		return CreepyBase.getInstance().getLogPrefix() != null ? CreepyBase.getInstance().getLogPrefix() : ("[" + CreepyBase.getInstance().getName() + "]");
	}
}