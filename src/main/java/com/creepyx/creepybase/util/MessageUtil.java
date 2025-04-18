package com.creepyx.creepybase.util;

import com.creepyx.creepybase.config.CustomConfig;
import net.kyori.adventure.text.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MessageUtil {

	public static CustomConfig getConfig() {
		return config;
	}

	public static void setConfig(CustomConfig config) {
		MessageUtil.config = config;
	}

	private static CustomConfig config;

	public static Component get(String messageKey) {
		String message = config.getString("messages." + messageKey);
		if (message == null || message.isBlank()) {
			return StringUtil.asComponent("key " + messageKey + " not found");
		}
		return StringUtil.asComponent(message);
	}

	public static Component get(String messageKey, String defaultMessage) {
		String message = config.getString("messages." + messageKey);

		if (message == null || message.isBlank()) {
			return StringUtil.asComponent(defaultMessage);
		}

		return StringUtil.asComponent(message);
	}

	public static Component get(String messageKey, String defaultMessage, Map<String, String> placeholders) {
		String message = config.getString("messages." + messageKey);

		if (message == null || message.isBlank()) {
			return StringUtil.asComponent(defaultMessage, placeholders);
		}

		return StringUtil.asComponent(message, placeholders);
	}

	public static Component get(String messageKey, Map<String, String> placeholders) {
		String message = config.getString("messages." + messageKey);
		if (message == null || message.isBlank()) {
			return StringUtil.asComponent("key " + messageKey + " not found");
		}
		return StringUtil.asComponent(message, placeholders);
	}

	public static Component getPrefixed(String messageKey) {
		String message = config.getString("messages." + messageKey);
		if (message == null || message.isBlank()) {
			return StringUtil.asPrefixedComponent("key " + messageKey + " not found");
		}
		return StringUtil.asPrefixedComponent(message, new HashMap<>());
	}

	public static Component getPrefixed(String messageKey, String defaultMessage) {
		String message = config.getString("messages." + messageKey);

		if (message == null || message.isBlank()) {
			return StringUtil.asPrefixedComponent(defaultMessage);
		}

		return StringUtil.asPrefixedComponent(message, new HashMap<>());
	}

	public static Component getPrefixed(String messageKey, String defaultMessage, Map<String, String> placeholders) {
		String message = config.getString("messages." + messageKey);

		if (message == null || message.isBlank()) {
			return StringUtil.asPrefixedComponent(defaultMessage, placeholders);
		}

		return StringUtil.asPrefixedComponent(message, placeholders);
	}

	public static Component getPrefixed(String messageKey, Map<String, String> placeholders) {
		return StringUtil.asPrefixedComponent(Objects.requireNonNull(config.getString("messages." + messageKey)), placeholders);
	}

	public static List<Component> getList(String messageKey) {
		return StringUtil.asFormattedList(config.getStringList("messages." + messageKey));
	}

	public static List<Component> getList(String messageKey, Map<String, String> placeholders) {
		return StringUtil.asFormattedList(config.getStringList("messages." + messageKey), placeholders);
	}

	public static String getLegacy(String messageKey) {
		return config.getString("messages." + messageKey);
	}

	public static List<String> getLegacyList(String messageKey) {
		return config.getStringList("messages." + messageKey);
	}
}
