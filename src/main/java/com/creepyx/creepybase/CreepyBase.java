package com.creepyx.creepybase;

import com.creepyx.creepybase.config.Config;
import com.creepyx.creepybase.config.CustomConfig;
import com.sk89q.worldguard.WorldGuard;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.List;

@Getter
public abstract class CreepyBase extends JavaPlugin {

	private static CreepyBase instance;
	private WorldGuard worldGuard;

	protected String logPrefix = this.getName();
	protected String prefix = "&8[&6" + this.getName() + "&8] &7";
	protected CustomConfig defaultConfig = new Config("config.yml");
	protected CustomConfig messages = new Config("messages.yml");

	/**
	 * Diese Methode kann von Plugins verwendet werden, um eine Instanz der API zu erhalten.
	 *
	 * @return instance of the API
	 */
	public static CreepyBase getInstance() {
		if (instance == null) {
			throw new IllegalStateException("API is not initialized yet!");
		}
		return instance;
	}

	/**
	 * this method is called when the plugin is enabled
	 */
	@Override
	public void onEnable() {
		if (instance == null) {
			instance = this;
			registerListeners();
			registerCommands();
			onPluginEnable();
		} else {
			throw new IllegalStateException("API already initialized!");
		}
	}

	/**
	 * this method is called when the plugin is disabled
	 */
	@Override
	public void onDisable() {
		onPluginDisable();
		instance = null;
	}

	/**
	 * this method can be used to execute code when the plugin is enabled
	 */
	protected abstract void onPluginEnable();

	/**
	 * this method can be used to execute code when the plugin is disabled
	 */
	protected abstract void onPluginDisable();

	/**
	 * registers listeners
	 */
	protected void registerListeners() {
		// Beispiel: Listener registrieren
		// Bukkit.getPluginManager().registerEvents(new MyListener(), this);
	}

	/**
	 * registers commands
	 */
	protected void registerCommands() {
		// Beispiel: Command registrieren
		// getCommand("mycommand").setExecutor(new MyCommandExecutor());
	}

	/**
	 * checks if a plugin is enabled
	 *
	 * @param pluginName name of the plugin
	 * @return true, if the plugin is enabled
	 */
	public boolean isPluginEnabled(String pluginName) {
		Plugin plugin = Bukkit.getPluginManager().getPlugin(pluginName);
		return plugin != null && plugin.isEnabled();
	}
}

