package com.creepyx.creepybase.util;

import com.creepyx.creepybase.CreepyBase;
import lombok.experimental.UtilityClass;
import org.bukkit.command.ConsoleCommandSender;

@UtilityClass
public class Log {

	public void info(LogType logType, String message) {
		String type = switch (logType) {
			case INFO -> "&a[INFO] ";
			case WARNING -> "&e[WARNING] ";
			case ERROR -> "&c[ERROR] ";
		};
		ConsoleCommandSender consoleSender = CreepyBase.getInstance().getServer().getConsoleSender();
		consoleSender.sendMessage(type + CreepyBase.getInstance().getLogPrefix() + message);
	}

	public enum LogType {
		INFO,
		WARNING,
		ERROR
	}
}
