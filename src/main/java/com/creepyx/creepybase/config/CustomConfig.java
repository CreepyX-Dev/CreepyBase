package com.creepyx.creepybase.config;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.InputStream;

public abstract class CustomConfig extends YamlConfiguration {

	public abstract CustomConfig createOrLoadDefault(File file, InputStream resource);
	public abstract CustomConfig createOrLoadDefault(String file, InputStream resource);

	public abstract CustomConfig getOrCreate(File file);
	public abstract CustomConfig getOrCreate(File directory, File file);
	public abstract CustomConfig getOrCreate(String file);
	public abstract CustomConfig getOrCreate(String folder, String file);

	/**
	 * Save a Custom Config in a yaml Format
	 */
	public abstract void save();
}
