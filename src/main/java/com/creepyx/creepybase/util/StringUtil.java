package com.creepyx.creepybase.util;

import com.creepyx.creepybase.CreepyBase;
import lombok.experimental.UtilityClass;
import meteordevelopment.starscript.Script;
import meteordevelopment.starscript.compiler.Compiler;
import meteordevelopment.starscript.compiler.Parser;
import meteordevelopment.starscript.utils.Error;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.text.minimessage.tag.standard.StandardTags;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.*;

@UtilityClass
public class StringUtil {

	@Deprecated(forRemoval = true)
	@ApiStatus.ScheduledForRemoval(inVersion = "1.1.2")
	public Component asComponent(String text) {
		if (text == null) {
			return Component.empty();
		}

		if (text.contains("&") || text.contains("§")) {
			return LegacyComponentSerializer.legacyAmpersand().deserialize("§f" + text);
		}

		return MiniMessage.builder().tags(TagResolver.builder().resolver(StandardTags.color()).resolver(StandardTags.clickEvent()).resolver(StandardTags.rainbow()).resolver(StandardTags.decorations()).resolver(StandardTags.font()).resolver(StandardTags.newline()).resolver(StandardTags.translatable()).resolver(StandardTags.keybind()).resolver(StandardTags.reset()).resolver(StandardTags.insertion()).resolver(StandardTags.hoverEvent()).resolver(StandardTags.score()).resolver(StandardTags.selector()).resolver(StandardTags.translatableFallback()).resolver(StandardTags.transition()).resolver(StandardTags.decorations(TextDecoration.ITALIC.withState(true).decoration())).build()).build().deserialize(text);
	}

	@Deprecated(forRemoval = true)
	@ApiStatus.ScheduledForRemoval(inVersion = "1.1.2")
	public Component asComponent(@NotNull String text, @NotNull Map<String, String> placeholders) {
		Component component = asComponent(text);
		for (Map.Entry<String, String> entry : placeholders.entrySet()) {
			String key = "{%}" + entry.getKey() + "%";
			String value = entry.getValue();
			component = (TextComponent) component.replaceText(TextReplacementConfig.builder()
					.matchLiteral(key)
					.replacement(asComponent(value))
					.build());
		}
		return component;
	}

	@Deprecated(forRemoval = true)
	@ApiStatus.ScheduledForRemoval(inVersion = "1.1.2")
	public Component asComponent(@NotNull String text, Replacement... placeholders) {
		Component component = asComponent(text);
		for (Replacement replacement : placeholders) {
			String key = "%" + replacement.original() + "%";
			String value = replacement.replacement();
			component = (TextComponent) component.replaceText(TextReplacementConfig.builder()
					.matchLiteral(key)
					.replacement(asComponent(value))
					.build());
		}
		return component;
	}

	public Component asModernComponent(@NotNull String text, Replacement... placeholders) {
		Parser.Result result = Parser.parse(text);
		Script script = getScript(result);

		if (starscriptReplace(result, placeholders)) return Component.empty();

		return asComponent(CreepyBase.getInstance().getStarscript().run(script).toString());
	}

	public Component asModernComponent(@NotNull String text, Map<String, String> placeholders) {
		Parser.Result result = Parser.parse(text);
		Script script = getScript(result);

		if (starscriptReplace(result, placeholders)) return Component.empty();

		return asComponent(CreepyBase.getInstance().getStarscript().run(script).toString());
	}

	public Component asModernPrefixedComponent(@NotNull String text, Replacement... placeholders) {
		Parser.Result result = Parser.parse(text);
		Script script = getScript(result);

		if (starscriptReplace(result, placeholders)) return Component.empty();

		return asPrefixedComponent(CreepyBase.getInstance().getStarscript().run(script).toString());
	}

	public Component asModernPrefixedComponent(@NotNull String text, Map<String, String> placeholders) {
		var result = Parser.parse(text);
		var script = getScript(result);

		if (starscriptReplace(result, placeholders)) return Component.empty();

		return asPrefixedComponent(CreepyBase.getInstance().getStarscript().run(script).toString());
	}

	@Deprecated(forRemoval = true)
	@ApiStatus.ScheduledForRemoval(inVersion = "1.1.2")
	public Component asPrefixedComponent(@NotNull String text) {
		return asPrefixedComponent(text, new HashMap<>());
	}

	@Deprecated(forRemoval = true)
	@ApiStatus.ScheduledForRemoval(inVersion = "1.1.2")
	public Component asPrefixedComponent(@NotNull String text, Map<String, String> placeholders) {
		return asComponent(CreepyBase.getInstance().getPrefix() != null ? CreepyBase.getInstance().getPrefix() : ("[" + CreepyBase.getInstance().getName() + "]")).append(asComponent(text, placeholders));
	}

	@Deprecated(forRemoval = true)
	@ApiStatus.ScheduledForRemoval(inVersion = "1.1.2")
	public Component asPrefixedComponent(@NotNull String text, Replacement... placeholders) {
		return asComponent(CreepyBase.getInstance().getPrefix() != null ? CreepyBase.getInstance().getPrefix() : ("[" + CreepyBase.getInstance().getName() + "]")).append(asComponent(text, placeholders));
	}

	public List<Component> asFormattedList(@NotNull List<String> list) {
		return asFormattedList(list, new HashMap<>());
	}

	public List<Component> asFormattedList(@NotNull List<String> list, @NotNull Map<String, String> placeholders) {
		List<Component> components = new ArrayList<>();

		for (String line : list) {
			components.add(asModernComponent(line, placeholders));
		}
		return components;
	}

	public List<Component> asFormattedList(@NotNull List<String> list, @NotNull Replacement... placeholders) {
		List<Component> components = new ArrayList<>();

		for (String line : list) {
			components.add(asModernComponent(line, placeholders));
		}
		return components;
	}

	public String asString(@NotNull Component component) {
		return LegacyComponentSerializer.legacyAmpersand().serialize(component);
	}

	public boolean isSimilarWord(String word, Set<String> wordList) {
		for (String s : wordList) {
			int levenshteinDistance = calculateLevenshteinDistance(word, s);
			int maxLength = Math.max(word.length(), s.length());

			// Calculate similarity percentage
			double similarity = 1 - (double) levenshteinDistance / maxLength;

			// Check if similarity is greater than or equal to 80%
			if (similarity >= 0.7) {
				return true;
			}
		}
		return false;
	}
	public boolean isSimilarWord(String word, Set<String> wordList, double percentage) {
		for (String s : wordList) {
			int levenshteinDistance = calculateLevenshteinDistance(word, s);
			int maxLength = Math.max(word.length(), s.length());

			// Calculate similarity percentage
			double similarity = 1 - (double) levenshteinDistance / maxLength;

			// Check if similarity is greater than or equal to 80%
			if (similarity >= percentage) {
				return true;
			}
		}
		return false;
	}

	public boolean isSimilarWord(String word, String anotherWord) {
		int levenshteinDistance = calculateLevenshteinDistance(word, anotherWord);
		int maxLength = Math.max(word.length(), anotherWord.length());

		// Calculate similarity percentage
		double similarity = 1 - (double) levenshteinDistance / maxLength;

		// Check if similarity is greater than or equal to 80%
		return similarity >= 0.7;
	}

	public boolean isSimilarWord(String word, String anotherWord, double percentage){
		int levenshteinDistance = calculateLevenshteinDistance(word, anotherWord);
		int maxLength = Math.max(word.length(), anotherWord.length());

		// Calculate similarity percentage
		double similarity = 1 - (double) levenshteinDistance / maxLength;

		// Check if similarity is greater than or equal to 80%
		return similarity >= percentage;
	}

	// Helper method to calculate Levenshtein Distance
	public int calculateLevenshteinDistance(String word1, String word2) {
		int[][] distances = new int[word1.length() + 1][word2.length() + 1];

		for (int previousLetter = 0; previousLetter <= word1.length(); previousLetter++) {
			for (int nextLetter = 0; nextLetter <= word2.length(); nextLetter++) {
				if (previousLetter == 0) {
					distances[previousLetter][nextLetter] = nextLetter;
				} else if (nextLetter == 0) {
					distances[previousLetter][nextLetter] = previousLetter;
				} else if (word1.charAt(previousLetter - 1) == word2.charAt(nextLetter - 1)) {
					distances[previousLetter][nextLetter] = distances[previousLetter - 1][nextLetter - 1];
				} else {
					distances[previousLetter][nextLetter] = 1 + Math.min(distances[previousLetter - 1][nextLetter - 1],
							Math.min(distances[previousLetter - 1][nextLetter], distances[previousLetter][nextLetter - 1]));
				}
			}
		}
		return distances[word1.length()][word2.length()];
	}

	private Script getScript(Parser.Result result) {
		return Compiler.compile(result);
	}


	private boolean starscriptReplace(Parser.Result result, Map<String, String> placeholders) {
		if (result.hasErrors()) {
			for (Error error : result.errors) Log.info(Log.LogType.ERROR, error.message);
			return true;
		}

		for (Map.Entry<String, String> entry : placeholders.entrySet()) {
			CreepyBase.getInstance().getStarscript().set(entry.getKey(), entry.getValue());
		}
		return false;
	}

	private boolean starscriptReplace(Parser.Result result, Replacement... placeholders) {
		if (result.hasErrors()) {
			for (Error error : result.errors) Log.info(Log.LogType.ERROR, error.message);
			return true;
		}

		for (Replacement replacement : placeholders) {
			CreepyBase.getInstance().getStarscript().set(replacement.original(), replacement.replacement());
		}
		return false;
	}

	public record Replacement(String original, String replacement) {}
}
