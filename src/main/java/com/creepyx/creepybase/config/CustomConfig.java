package com.creepyx.creepybase.config;

import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public abstract class CustomConfig extends YamlConfiguration {

	public abstract CustomConfig get(File file);

	public abstract CustomConfig get(File directory, File file);

	public abstract CustomConfig get(@NotNull String file);

	public abstract CustomConfig get(String folder, String file);

	/**
	 * Save a Custom Config in a yaml Format
	 */
	public abstract void save();
}
