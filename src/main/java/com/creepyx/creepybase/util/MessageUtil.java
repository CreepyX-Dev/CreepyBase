package com.creepyx.creepybase.util;

import com.creepyx.creepybase.config.CustomConfig;
import lombok.Setter;
import lombok.experimental.UtilityClass;
import net.kyori.adventure.text.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@UtilityClass
public class MessageUtil {

    @Setter
    private CustomConfig config;

    public Component get(String messageKey) {
        String message = config.getString("messages." + messageKey);
        if (message == null || message.isBlank()) {
            return StringUtil.asComponent("key " + messageKey + " not found");
        }
        return StringUtil.asComponent(message);
    }

    public Component get(String messageKey, Map<String, String> placeholders) {
        String message = config.getString("messages." + messageKey);
        if (message == null || message.isBlank()) {
            return StringUtil.asComponent("key " + messageKey + " not found");
        }
        return StringUtil.asComponent(message, placeholders);
    }

    public Component getPrefixed(String messageKey) {
        String message = config.getString("messages." + messageKey);
        if (message == null || message.isBlank()) {
            return StringUtil.asPrefixedComponent("key " + messageKey + " not found");
        }
        return StringUtil.asPrefixedComponent(message, new HashMap<>());
    }

    public Component getPrefixed(String messageKey, String defaultMessage) {
        String message = config.getString("messages." + messageKey);

        if (message == null || message.isBlank()) {
            return StringUtil.asPrefixedComponent(defaultMessage);
        }

        return StringUtil.asPrefixedComponent(message, new HashMap<>());
    }

    public Component getPrefixed(String messageKey, String defaultMessage, Map<String, String> placeholders) {
        String message = config.getString("messages." + messageKey);

        if (message == null || message.isBlank()) {
            return StringUtil.asPrefixedComponent(defaultMessage, placeholders);
        }

        return StringUtil.asPrefixedComponent(message, placeholders);
    }

    public Component getPrefixed(String messageKey, Map<String, String> placeholders) {
        return StringUtil.asPrefixedComponent(Objects.requireNonNull(config.getString("messages." + messageKey)), placeholders);
    }

    public Component getOrDefault(String messageKey, String defaultMessage) {
        String message = config.getString("messages." + messageKey);

        if (message == null || message.isBlank()) {
            return StringUtil.asComponent(defaultMessage);
        }

        return StringUtil.asComponent(message);
    }

    public Component getOrDefault(String messageKey, String defaultMessage, Map<String, String> placeholders) {
        String message = config.getString("messages." + messageKey);

        if (message == null || message.isBlank()) {
            return StringUtil.asComponent(defaultMessage, placeholders);
        }

        return StringUtil.asComponent(message, placeholders);
    }

    public List<Component> getList(String messageKey) {
        return StringUtil.asFormattedList(config.getStringList("messages." + messageKey));
    }

    public List<Component> getList(String messageKey, Map<String, String> placeholders) {
        return StringUtil.asFormattedList(config.getStringList("messages." + messageKey), placeholders);
    }

    public String getLegacy(String messageKey) {
        return config.getString("messages." + messageKey);
    }

    public List<String> getLegacyList(String messageKey) {
        return config.getStringList("messages." + messageKey);
    }
}
