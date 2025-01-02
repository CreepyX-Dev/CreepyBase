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
        return StringUtil.asComponent(config.getString("Messages." + messageKey));
    }

    public Component get(String messageKey, Map<String, String> placeholders) {
        return StringUtil.asComponent(Objects.requireNonNull(config.getString("Messages." + messageKey)), placeholders);
    }

    public Component getPrefixed(String messageKey) {
        return StringUtil.asPrefixedComponent(Objects.requireNonNull(config.getString("Messages." + messageKey)), new HashMap<>());
    }

    public Component getPrefixed(String messageKey, String defaultMessage) {
        String message = config.getString("Messages." + messageKey);

        if (message == null || message.isBlank()) {
            return StringUtil.asPrefixedComponent(defaultMessage);
        }

        return StringUtil.asPrefixedComponent(message, new HashMap<>());
    }

    public Component getPrefixed(String messageKey, String defaultMessage, Map<String, String> placeholders) {
        String message = config.getString("Messages." + messageKey);

        if (message == null || message.isBlank()) {
            return StringUtil.asPrefixedComponent(defaultMessage, placeholders);
        }

        return StringUtil.asPrefixedComponent(message, new HashMap<>());
    }

    public Component getPrefixed(String messageKey, Map<String, String> placeholders) {
        return StringUtil.asPrefixedComponent(Objects.requireNonNull(config.getString("Messages." + messageKey)), placeholders);
    }

    public Component getOrDefault(String messageKey, String defaultMessage) {
        String message = config.getString("Messages." + messageKey);

        if (message == null || message.isBlank()) {
            return StringUtil.asComponent(defaultMessage);
        }

        return StringUtil.asComponent(message);
    }

    public Component getOrDefault(String messageKey, String defaultMessage, Map<String, String> placeholders) {
        String message = config.getString("Messages." + messageKey);

        if (message == null || message.isBlank()) {
            return StringUtil.asComponent(defaultMessage, placeholders);
        }

        return StringUtil.asComponent(message, placeholders);
    }

    public List<Component> getList(String messageKey) {
        return StringUtil.asFormattedList(config.getStringList("Messages." + messageKey));
    }

    public List<Component> getList(String messageKey, Map<String, String> placeholders) {
        return StringUtil.asFormattedList(config.getStringList("Messages." + messageKey), placeholders);
    }

    public String getLegacy(String messageKey) {
        return config.getString("Messages." + messageKey);
    }

    public List<String> getLegacyList(String messageKey) {
        return config.getStringList("Messages." + messageKey);
    }
}
