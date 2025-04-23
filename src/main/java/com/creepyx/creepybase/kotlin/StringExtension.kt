package com.creepyx.creepybase.kotlin

import com.creepyx.creepybase.CreepyBase
import com.creepyx.creepybase.util.LogType
import com.creepyx.creepybase.util.LogUtil
import com.creepyx.creepybase.util.MessageUtil
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextDecoration
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver
import net.kyori.adventure.text.minimessage.tag.standard.StandardTags
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import kotlin.math.max
import kotlin.math.min

fun String.c(placeholders: Map<String, String> = emptyMap(), withPrefix: Boolean = false): Component {
    if (this.isBlank()) {
        return Component.empty()
    }

    var message = this

    if (withPrefix) {
        message = CreepyBase.getInstance().prefix.plus(message)
    }

    if (placeholders.isNotEmpty()) {
        for ((key1, value) in placeholders) {
            val key = "%$key1%"
            message = this.replace(key, value)
        }
    }

    if (message.contains("&") || message.contains("§")) {
        return LegacyComponentSerializer.legacyAmpersand().deserialize(message)
    }

    return MiniMessage.builder().tags(
        TagResolver.builder().resolver(StandardTags.color()).resolver(StandardTags.defaults())
            .resolver(StandardTags.decorations(TextDecoration.ITALIC.withState(false).decoration()))
            .build()
    ).build().deserialize(message)
}

fun String.msg(
    placeholders: Map<String, String> = mapOf(),
    withPrefix: Boolean = false,
    fallback: String?
): Component {
    if (MessageUtil.getConfig() == null) {
        LogUtil.log(LogType.ERROR, "${MessageUtil.getConfig().name} is null or empty!")
        return Component.empty()
    }
    if (fallback != null) {
        return MessageUtil.getConfig().getString(this, fallback)!!.c(placeholders, withPrefix)
    }
    return MessageUtil.getConfig().getString(this, this)!!.c(placeholders, withPrefix)
}

fun String.log(logType: LogType) {
    LogUtil.log(logType, this)
}

fun List<String>.toComponents(placeholders: Map<String, String> = emptyMap()): List<Component> {
    var components = emptyList<Component>()

    for (string in this) {
        components = components.plus(string.c(placeholders))
    }

    return components
}

fun String.isSimilarWord( wordList: Set<String>): Boolean {
    for (s in wordList) {
        val levenshteinDistance = calculateLevenshteinDistance(this, s)
        val maxLength = max(this.length.toDouble(), s.length.toDouble()).toInt()

        // Calculate similarity percentage
        val similarity = 1 - levenshteinDistance.toDouble() / maxLength

        // Check if similarity is greater than or equal to 80%
        if (similarity >= 0.7) {
            return true
        }
    }
    return false
}

fun String.isSimilarWord(wordList: Set<String>, percentage: Double = 0.7): Boolean {
    for (s in wordList) {
        if (this.isSimilarWord(s, percentage))
            return true
    }
    return false
}

fun String.isSimilarWord( anotherWord: String, percentage: Double = 0.7): Boolean {
    val levenshteinDistance = calculateLevenshteinDistance(this, anotherWord)
    val maxLength = max(this.length.toDouble(), anotherWord.length.toDouble()).toInt()

    // Calculate similarity percentage
    val similarity = 1 - levenshteinDistance.toDouble() / maxLength

    // Check if similarity is greater than or equal to 80%
    return similarity >= percentage
}

// Helper method to calculate Levenshtein Distance
private fun calculateLevenshteinDistance(word1: String, word2: String): Int {
    val distances = Array(word1.length + 1) { IntArray(word2.length + 1) }

    for (previousLetter in 0..word1.length) {
        for (nextLetter in 0..word2.length) {
            if (previousLetter == 0) {
                distances[previousLetter][nextLetter] = nextLetter
            } else if (nextLetter == 0) {
                distances[previousLetter][nextLetter] = previousLetter
            } else if (word1[previousLetter - 1] == word2[nextLetter - 1]) {
                distances[previousLetter][nextLetter] = distances[previousLetter - 1][nextLetter - 1]
            } else {
                distances[previousLetter][nextLetter] =
                    (1 + min(
                        distances[previousLetter - 1][nextLetter - 1].toDouble(),
                        min(
                            distances[previousLetter - 1][nextLetter].toDouble(),
                            distances[previousLetter][nextLetter - 1].toDouble()
                        )
                    )).toInt()
            }
        }
    }
    return distances[word1.length][word2.length]
}