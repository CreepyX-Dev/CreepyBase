package com.creepyx.creepybase.util;

import lombok.Data;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Simple tuple for key-value pairs
 * this class is implementing Serializable
 *
 * @param <K>
 * @param <V>
 */
@Data
public final class Tuple<K, V> implements Serializable {

	/**
	 * The key
	 */
	private final K key;

	/**
	 * The value
	 */
	private final V value;


	/**
	 *
	 * @return this tuple in X - Y syntax
	 */
	public String toLine() {
		return this.key + " - " + this.value;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.toLine();
	}

	public static <K, V> Map<K, V> getMap(Tuple<K, V> tuple) {
		Map<K, V> map = new HashMap<>();
		map.put(tuple.key, tuple.value);
		return map;
	}

}
