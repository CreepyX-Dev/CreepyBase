package com.creepyx.creepybase;

import lombok.Getter;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.Objects;

/**
 * The {@code CreepyBase} class is the base class for all plugins
 * it extends the {@link JavaPlugin} class and implements the {@link Listener} interface
 * and also registers the plugin main Class as a listener
 */
@Getter
public abstract class CreepyBase extends JavaPlugin implements Listener {

	/**
	 * The instance of this plugin
	 */
	private static CreepyBase instance;


	/**
	 * The {@code logPrefix} is used for logging messages to the console<br> and
	 * must be set in the {@code onPluginEnable()} method
	 * otherwise the plugin name is used
	 * <p>
	 * {@code supports legacy ColorCodes}
	 */
	protected String logPrefix;

	/**
	 * The {@code prefix} is used for sending messages to Players<br> and
	 * must be set in the {@code onPluginEnable()} method
	 * otherwise the plugin name is used
	 * <br>
	 * <br>
	 * {@code supports only MiniMessage}
	 */
	protected String prefix;

	/**
	 * this method is called when the plugin is enabled<br>
	 * it is recommended to use the {@code onPluginEnable()} method instead
	 * of overriding this method
	 */
	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
		onPluginEnable();
	}

	/**
	 * this method is called when the plugin is disabled
	 * <br>it is recommended to use the {@code onPluginDisable()} method instead
	 * of overriding this method, because it is called automatically
	 * when the plugin is disabling
	 */
	@Override
	public void onDisable() {
		onPluginDisable();
	}

	/**
	 * can be used to execute code when the plugin is enabled
	 * <p>
	 * this method is automatically called, when the {@code onEnable()} method
	 * is called
	 */
	protected abstract void onPluginEnable();

	/**
	 * can be used to execute code when the plugin is disabled
	 * <p>
	 * this method is automatically called, when the {@code onDisable()} method
	 * is called
	 */
	protected abstract void onPluginDisable();

	/**
	 * Returns the instance of {@link CreepyBase}.
	 * <p>
	 * It is recommended to override this in your own {@link CreepyBase}
	 * implementation so you will get the instance of that, directly.
	 *
	 * @return this instance
	 */
	public static CreepyBase getInstance() {
		if (instance == null) {
			try {
				instance = JavaPlugin.getPlugin(CreepyBase.class);

			} catch (final IllegalStateException ex) {
				if (Bukkit.getPluginManager().getPlugin("PlugMan") != null)
					Bukkit.getLogger().severe("Failed to get instance of the plugin, if you reloaded using PlugMan you need to do a clean restart instead.");

				throw ex;
			}

			Objects.requireNonNull(instance, "Cannot get a new instance! Have you reloaded?");
		}

		return instance;
	}

	/**
	 * checks if a plugin is loaded and enabled
	 *
	 * @param pluginName name of the plugin
	 * @return {@code true}, if the plugin is loaded and enabled
	 */
	public boolean isPluginEnabled(String pluginName) {
		Plugin plugin = Bukkit.getPluginManager().getPlugin(pluginName);
		return plugin != null && plugin.isEnabled();
	}
}

